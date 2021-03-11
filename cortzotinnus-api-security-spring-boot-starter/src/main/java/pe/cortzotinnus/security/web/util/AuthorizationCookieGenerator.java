package pe.cortzotinnus.security.web.util;

import org.springframework.web.util.CookieGenerator;
import pe.cortzotinnus.security.config.properties.WebSecurityConfigurationProperties;

import java.util.concurrent.TimeUnit;

public class AuthorizationCookieGenerator extends CookieGenerator {

    public AuthorizationCookieGenerator(WebSecurityConfigurationProperties properties) {
        this.setCookieHttpOnly(properties.isCookieHttpOnly());
        this.setCookieName(properties.getAuthorizationCookieName());
        this.setCookieMaxAge((int) TimeUnit.MINUTES.toSeconds(properties.getTokenDurationInMinutes()));
    }
}
