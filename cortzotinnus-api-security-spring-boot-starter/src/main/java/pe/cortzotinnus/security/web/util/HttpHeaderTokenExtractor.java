package pe.cortzotinnus.security.web.util;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import pe.cortzotinnus.security.JsonWebToken;
import pe.cortzotinnus.security.Token;
import pe.cortzotinnus.security.config.properties.WebSecurityConfigurationProperties;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RequiredArgsConstructor
public class HttpHeaderTokenExtractor implements TokenExtractor {

    private final WebSecurityConfigurationProperties properties;

    private static final String DEFAULT_AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String DEFAULT_TOKEN_PREFIX = "Bearer ";

    @Override
    public Token extractAccessToken(HttpServletRequest request) {
        return Optional
                .ofNullable(request.getHeader(authorizationHeaderName()))
                .map(this::createToken)
                .orElseThrow(() -> new BadCredentialsException("No se encontró token en la cabecera"));
    }

    private Token createToken(String rawHeaderValue) {
        return JsonWebToken.createAccessToken(retrieveTokenFromRawHeaderValue(rawHeaderValue));
    }

    private String retrieveTokenFromRawHeaderValue(String rawHeaderValue) {
        return StringUtils.replace(rawHeaderValue, tokenPrefix(), StringUtils.EMPTY).trim();
    }

    private String tokenPrefix() {
        return Optional.ofNullable(properties.getHeaderAuthorizationPrefix()).orElse(DEFAULT_TOKEN_PREFIX);
    }

    private String authorizationHeaderName() {
        return Optional.ofNullable(properties.getHeaderAuthorizationName()).orElse(DEFAULT_AUTHORIZATION_HEADER_NAME);
    }
}
