package pe.cortzotinnus.security.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfigurationSource;
import pe.cortzotinnus.security.autoconfigure.factory.CorsConfigurationSourceFactory;
import pe.cortzotinnus.security.autoconfigure.properties.SecurityAutoconfigureProperties;
import pe.cortzotinnus.security.config.AuthenticationConfigurerAdapter;
import pe.cortzotinnus.security.provider.TokenRetrieverAuthenticationProvider;
import pe.cortzotinnus.security.retriever.TokenRetriever;
import pe.cortzotinnus.security.web.authentication.AuthorizationTokensAuthenticationHandler;
import pe.cortzotinnus.security.web.util.AuthorizationCookieGenerator;

@Slf4j
@EnableConfigurationProperties(SecurityAutoconfigureProperties.class)
@ConditionalOnProperty(
        prefix = "com.cortzotinnus.security.web.authentication",
        name = "enabled", havingValue = "true"
)
@Configuration
public class AuthenticationAutoconfiguration {

    private final SecurityAutoconfigureProperties securityAutoconfigureProperties;

    public AuthenticationAutoconfiguration(SecurityAutoconfigureProperties securityAutoconfigureProperties) {
        this.securityAutoconfigureProperties = securityAutoconfigureProperties;
    }

    @Bean
    public TokenRetrieverAuthenticationProvider tokenRetrieveAuthenticationProvider(final TokenRetriever tokenRetriever) {
        return new TokenRetrieverAuthenticationProvider(tokenRetriever);
    }

    @ConditionalOnMissingBean(AuthorizationCookieGenerator.class)
    @Bean
    public AuthorizationCookieGenerator authorizationCookieGenerator() {
        return new AuthorizationCookieGenerator(this.securityAutoconfigureProperties.getConfig());
    }

    @Bean
    @ConditionalOnMissingBean(AuthorizationTokensAuthenticationHandler.class)
    public AuthorizationTokensAuthenticationHandler authorizationTokensAuthenticationHandler(final ObjectMapper objectMapper, final AuthorizationCookieGenerator authorizationCookieGenerator) {
        return new AuthorizationTokensAuthenticationHandler(objectMapper, authorizationCookieGenerator);
    }

    @Bean
    public AuthenticationConfigurerAdapter authenticationConfigurerAdapter(
            final ObjectMapper objectMapper,
            final AuthorizationTokensAuthenticationHandler authorizationTokensAuthenticationHandler,
            final TokenRetrieverAuthenticationProvider tokenRetrieverAuthenticationProvider
    ) {
        return new AuthenticationConfigurerAdapter(objectMapper, authorizationTokensAuthenticationHandler, tokenRetrieverAuthenticationProvider, this.securityAutoconfigureProperties.getAuthentication());
    }

    @ConditionalOnMissingBean(CorsConfigurationSource.class)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return CorsConfigurationSourceFactory.create(this.securityAutoconfigureProperties.getCors());
    }
}
