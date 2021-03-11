package pe.cortzotinnus.security.config.properties;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CorsConfigurationProperties {
    private String allowedOrigins;
    private List<String> allowedMethods;
    private List<String> allowedHeaders;
    private boolean allowCredentials;
    private String registerCorsConfigurationPath;
}
