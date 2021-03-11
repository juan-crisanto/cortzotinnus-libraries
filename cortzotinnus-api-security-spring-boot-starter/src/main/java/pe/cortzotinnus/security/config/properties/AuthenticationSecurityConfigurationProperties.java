package pe.cortzotinnus.security.config.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationSecurityConfigurationProperties {
    private String loginProcessingUrl;
    private String logoutUrl;
    private String urlEntryPointPattern;
}
