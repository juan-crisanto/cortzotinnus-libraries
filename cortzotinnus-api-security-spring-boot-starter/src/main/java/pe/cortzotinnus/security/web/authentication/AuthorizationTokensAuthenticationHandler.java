package pe.cortzotinnus.security.web.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import pe.cortzotinnus.security.PairOfTokens;
import pe.cortzotinnus.security.config.SecurityConfigurationConstant;
import pe.cortzotinnus.security.web.authentication.dto.FailureAuthenticationResponseDto;
import pe.cortzotinnus.security.web.authentication.dto.SuccessAuthenticationResponseDto;
import pe.cortzotinnus.security.web.util.AuthorizationCookieGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
public class AuthorizationTokensAuthenticationHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;
    private final AuthorizationCookieGenerator authorizationCookieGenerator;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        log.info("Authentication Success");
        final PairOfTokens pairOfTokens = (PairOfTokens) authentication.getPrincipal();
        writeResponseBodyWithCode(httpServletResponse, createSuccessResponseDto(pairOfTokens), HttpServletResponse.SC_OK);
        addTokensToCookie(httpServletRequest, httpServletResponse, pairOfTokens);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        log.error("Authentication Failure with error {}", e);
        removeTokensFromCookie(httpServletRequest, httpServletResponse);
        writeResponseBodyWithCode(httpServletResponse, createFailureResponseDto(e), HttpServletResponse.SC_UNAUTHORIZED);
    }

    private void removeTokensFromCookie(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.authorizationCookieGenerator.setCookieSecure(httpServletRequest.isSecure());
        this.authorizationCookieGenerator.removeCookie(httpServletResponse);
    }

    private void addTokensToCookie(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, PairOfTokens pairOfTokens) {
        removeTokensFromCookie(httpServletRequest, httpServletResponse);
        this.authorizationCookieGenerator.addCookie(httpServletResponse, pairOfTokens.getAccessToken().getRawToken());
    }

    private FailureAuthenticationResponseDto createFailureResponseDto(final AuthenticationException e) {
        FailureAuthenticationResponseDto.FailureAuthenticationResponseDtoBuilder responseDtoBuilder = FailureAuthenticationResponseDto
                .builder()
                .errorCode(SecurityConfigurationConstant.AUTHENTICATION_ERROR_CODE)
                .errorMessage(SecurityConfigurationConstant.UNSUCCESSFUL_AUTHENTICATION_DEFAULT_MESSAGE);
        if (e instanceof BadCredentialsException) {
            responseDtoBuilder.errorMessage(e.getMessage());
        }
        return responseDtoBuilder.build();
    }

    private SuccessAuthenticationResponseDto createSuccessResponseDto(PairOfTokens pairOfTokens) {
        return SuccessAuthenticationResponseDto
                .builder()
                .accessToken(pairOfTokens.getAccessToken().getRawToken())
                .refreshToken(pairOfTokens.getRefreshToken().getRawToken())
                .expirationTimeInMinutes(
                        getCookieExpirationTimeInMinutes()
                )
                .expireAt(
                        getExpirationLocalDatetime()
                )
                .build();
    }

    private LocalDateTime getExpirationLocalDatetime() {
        return LocalDateTime.now().plus(this.authorizationCookieGenerator.getCookieMaxAge(), ChronoUnit.SECONDS);
    }

    private int getCookieExpirationTimeInMinutes() {
        return Math.toIntExact(TimeUnit.SECONDS.toMinutes(this.authorizationCookieGenerator.getCookieMaxAge()));
    }

    private void writeResponseBodyWithCode(HttpServletResponse httpServletResponse, Object responseDto, int httpStatusCode) throws IOException {
        final String jsonResponse = this.objectMapper.writeValueAsString(responseDto);
        httpServletResponse.getWriter().write(jsonResponse);
        httpServletResponse.setStatus(httpStatusCode);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }
}
