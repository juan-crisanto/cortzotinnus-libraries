package pe.cortzotinnus.security.autoconfigure.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import pe.cortzotinnus.security.config.properties.*;

@ConfigurationProperties(prefix = "com.cortzotinnus.security.web")
@Getter
@Setter
public class SecurityAutoconfigureProperties {

    private AuthenticationSecurityConfigurationProperties authentication;
    private JWTAuthenticationSecurityConfigurationProperties tokenAuthorization;
    private BasicAuthenticationSecurityConfigurationProperties basic;
    private CorsConfigurationProperties cors;
    private WebSecurityConfigurationProperties config;

}
