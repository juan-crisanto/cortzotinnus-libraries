package pe.cortzotinnus.security.autoconfigure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pe.cortzotinnus.security.autoconfigure.properties.SecurityAutoconfigureProperties;

@Import({
        CommonsSecurityBeansConfiguration.class,
        AuthenticationAutoconfiguration.class,
        BasicAuthAutoconfiguration.class,
        TokenAuthorizationAutoconfiguration.class
})
@EnableConfigurationProperties(SecurityAutoconfigureProperties.class)
@Configuration
public class AllAutoConfigurationSettings {

}
