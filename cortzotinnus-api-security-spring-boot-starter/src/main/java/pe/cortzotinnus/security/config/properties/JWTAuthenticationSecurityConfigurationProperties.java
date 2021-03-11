package pe.cortzotinnus.security.config.properties;

import lombok.*;

import java.util.List;

@Getter
@Setter
public class JWTAuthenticationSecurityConfigurationProperties {
    private String urlTokenProtectedPattern;
    @Singular
    private List<String> excludedUrls;
}
