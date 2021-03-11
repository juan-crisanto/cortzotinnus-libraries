package pe.cortzotinnus.security.config.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebSecurityConfigurationProperties {

    private int tokenDurationInMinutes;
    private String authorizationCookieName;
    private boolean cookieHttpOnly;
    private boolean useCookie;

    private String headerAuthorizationName;
    private String headerAuthorizationPrefix;
}
