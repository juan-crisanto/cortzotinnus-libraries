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
import pe.cortzotinnus.security.config.JwtConfigurerAdapter;
import pe.cortzotinnus.security.provider.JWTAuthenticationProvider;
import pe.cortzotinnus.security.retriever.UserRetrieverService;
import pe.cortzotinnus.security.web.authentication.AuthorizationTokensAuthenticationHandler;
import pe.cortzotinnus.security.web.util.AuthorizationCookieGenerator;
import pe.cortzotinnus.security.web.util.HttpHeaderTokenExtractor;
import pe.cortzotinnus.security.web.util.TokenExtractor;

@Slf4j
@EnableConfigurationProperties(SecurityAutoconfigureProperties.class)
@ConditionalOnProperty(
        prefix = "com.cortzotinnus.security.web.tokenAuthorization",
        name = "enabled", havingValue = "true"
)
@Configuration
public class TokenAuthorizationAutoconfiguration {

    private final SecurityAutoconfigureProperties securityAutoconfigureProperties;

    public TokenAuthorizationAutoconfiguration(SecurityAutoconfigureProperties securityAutoconfigureProperties) {
        this.securityAutoconfigureProperties = securityAutoconfigureProperties;
    }

    @ConditionalOnMissingBean(AuthorizationCookieGenerator.class)
    @Bean
    public AuthorizationCookieGenerator authorizationCookieGenerator() {
        return new AuthorizationCookieGenerator(this.securityAutoconfigureProperties.getConfig());
    }

    @ConditionalOnMissingBean(TokenExtractor.class)
    @Bean
    public TokenExtractor httpHeaderTokenExtractor() {
        return new HttpHeaderTokenExtractor(this.securityAutoconfigureProperties.getConfig());
    }

    @Bean
    public JWTAuthenticationProvider jwtAuthenticationProvider(final UserRetrieverService userRetrieverService) {
        return new JWTAuthenticationProvider(userRetrieverService);
    }

    @Bean
    @ConditionalOnMissingBean(AuthorizationTokensAuthenticationHandler.class)
    public AuthorizationTokensAuthenticationHandler authorizationTokensAuthenticationHandler(final ObjectMapper objectMapper, final AuthorizationCookieGenerator authorizationCookieGenerator) {
        return new AuthorizationTokensAuthenticationHandler(objectMapper, authorizationCookieGenerator);
    }

    @Bean
    public JwtConfigurerAdapter jwtConfigurerAdapter(
            final AuthorizationTokensAuthenticationHandler authorizationTokensAuthenticationHandler,
            final JWTAuthenticationProvider jwtAuthenticationProvider,
            final TokenExtractor tokenExtractor) {
        return new JwtConfigurerAdapter(authorizationTokensAuthenticationHandler, jwtAuthenticationProvider, tokenExtractor, this.securityAutoconfigureProperties.getTokenAuthorization());
    }

    @ConditionalOnMissingBean(CorsConfigurationSource.class)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return CorsConfigurationSourceFactory.create(this.securityAutoconfigureProperties.getCors());
    }
}
