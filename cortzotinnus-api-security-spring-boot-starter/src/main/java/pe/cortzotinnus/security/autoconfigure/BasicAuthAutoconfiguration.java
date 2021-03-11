package pe.cortzotinnus.security.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfigurationSource;
import pe.cortzotinnus.security.autoconfigure.factory.CorsConfigurationSourceFactory;
import pe.cortzotinnus.security.autoconfigure.properties.SecurityAutoconfigureProperties;
import pe.cortzotinnus.security.config.BasicAuthConfigurerAdapter;

@Slf4j
@EnableConfigurationProperties(SecurityAutoconfigureProperties.class)
@ConditionalOnProperty(
        prefix = "com.cortzotinnus.security.web.basic",
        name = "enabled", havingValue = "true"
)
@Configuration
public class BasicAuthAutoconfiguration {

    private final SecurityAutoconfigureProperties securityAutoconfigureProperties;

    public BasicAuthAutoconfiguration(SecurityAutoconfigureProperties securityAutoconfigureProperties) {
        this.securityAutoconfigureProperties = securityAutoconfigureProperties;
    }

    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public BasicAuthConfigurerAdapter basicAuthConfigurerAdapter(final PasswordEncoder passwordEncoder) {
        return new BasicAuthConfigurerAdapter(passwordEncoder, this.securityAutoconfigureProperties.getBasic());
    }

    @ConditionalOnMissingBean(CorsConfigurationSource.class)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return CorsConfigurationSourceFactory.create(this.securityAutoconfigureProperties.getCors());
    }
}
