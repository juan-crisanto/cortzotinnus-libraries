package pe.cortzotinnus.security.web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import pe.cortzotinnus.security.authentication.UsernamePasswordAuthenticationToken;
import pe.cortzotinnus.security.web.authentication.dto.UsernameCredentialsAuthenticationRequestDto;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class UsernamePasswordAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;

    public UsernamePasswordAuthenticationProcessingFilter(String defaultFilterProcessesUrl, ObjectMapper objectMapper) {
        super(defaultFilterProcessesUrl);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        isValidHttpMethod(request);
        UsernameCredentialsAuthenticationRequestDto authenticationRequest = retrieveAuthenticationRequest(request);
        Authentication authenticationToken = UsernamePasswordAuthenticationToken.fromCredentials(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        this.getFailureHandler().onAuthenticationFailure(request, response, failed);
    }

    private UsernameCredentialsAuthenticationRequestDto retrieveAuthenticationRequest(HttpServletRequest request) throws IOException {
        return objectMapper.readValue(request.getInputStream(), UsernameCredentialsAuthenticationRequestDto.class);
    }

    private void isValidHttpMethod(HttpServletRequest request) {
        if (!HttpMethod.POST.matches(request.getMethod())) {
            throw new UnsupportedOperationException("Authentication method not supported");
        }
    }
}
