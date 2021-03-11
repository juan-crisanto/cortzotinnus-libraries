package pe.cortzotinnus.security.autoconfigure.factory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pe.cortzotinnus.security.config.properties.CorsConfigurationProperties;

import java.util.Collections;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CorsConfigurationSourceFactory {

    public static UrlBasedCorsConfigurationSource create(final CorsConfigurationProperties corsConfigurationProperties) {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList(corsConfigurationProperties.getAllowedOrigins()));
        configuration.setAllowedMethods(corsConfigurationProperties.getAllowedMethods());
        configuration.setAllowedHeaders(corsConfigurationProperties.getAllowedHeaders());
        configuration.setAllowCredentials(corsConfigurationProperties.isAllowCredentials());
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(corsConfigurationProperties.getRegisterCorsConfigurationPath(), configuration);
        log.info("CORS origin has enabled and configured...");
        return source;
    }

}
