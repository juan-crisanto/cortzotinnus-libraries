package pe.cortzotinnus.security.web.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.util.WebUtils;
import pe.cortzotinnus.security.JsonWebToken;
import pe.cortzotinnus.security.Token;
import pe.cortzotinnus.security.config.properties.WebSecurityConfigurationProperties;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RequiredArgsConstructor
public class CookieTokenExtractor implements TokenExtractor {

    private final WebSecurityConfigurationProperties properties;

    @Override
    public Token extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(WebUtils.getCookie(request, properties.getAuthorizationCookieName())).map(s ->
                JsonWebToken.createAccessToken(s.getValue())).orElseThrow(() -> new BadCredentialsException("No se encontro token en la cookie"));
    }
}
