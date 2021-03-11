package pe.cortzotinnus.security.config.properties;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BasicAuthenticationSecurityConfigurationProperties {

    private String urlEntryPointPattern;
    private String username;
    private String password;
    private List<String> roles;

}
