package pe.cortzotinnus.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import pe.cortzotinnus.security.config.properties.JWTAuthenticationSecurityConfigurationProperties;
import pe.cortzotinnus.security.provider.JWTAuthenticationProvider;
import pe.cortzotinnus.security.web.authentication.AuthorizationTokensAuthenticationHandler;
import pe.cortzotinnus.security.web.filter.JWTAuthenticationFilter;
import pe.cortzotinnus.security.web.util.SkipPathsExceptOneRequestMatcher;
import pe.cortzotinnus.security.web.util.TokenExtractor;

import java.util.List;

@Order(value = SecurityConfigurationConstant.JWT_AUTHORIZATION_ORDER)
@RequiredArgsConstructor
public class JwtConfigurerAdapter extends WebSecurityConfigurerAdapter {

    private final AuthorizationTokensAuthenticationHandler authorizationTokensAuthenticationHandler;
    private final JWTAuthenticationProvider jwtAuthenticationProvider;
    private final TokenExtractor tokenExtractor;
    private final JWTAuthenticationSecurityConfigurationProperties jwtProperties;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    protected AuthenticationProvider authenticationProvider() {
        return this.jwtAuthenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        BaseHttpStatelessSecurityConfig
                .forUrlEntryPoint(jwtProperties.getUrlTokenProtectedPattern())
                .accept(http);
        http
                .authorizeRequests()
                .antMatchers(jwtProperties.getExcludedUrls().stream().toArray(String[]::new))
                .permitAll()
                .antMatchers(jwtProperties.getUrlTokenProtectedPattern())
                .authenticated()
                .and()
                .addFilterAfter(createAuthenticationFilter(this.authorizationTokensAuthenticationHandler, this.authenticationManager()), UsernamePasswordAuthenticationFilter.class);
    }

    private JWTAuthenticationFilter createAuthenticationFilter(AuthorizationTokensAuthenticationHandler authorizationTokensAuthenticationHandler, final AuthenticationManager authenticationManager) {
        final RequestMatcher matcher = getSkipPathsExceptOneRequestMatcher();
        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter(matcher, this.tokenExtractor);
        jwtAuthenticationFilter.setAuthenticationFailureHandler(authorizationTokensAuthenticationHandler);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        return jwtAuthenticationFilter;
    }

    private RequestMatcher getSkipPathsExceptOneRequestMatcher() {
        List<String> pathToSkip = jwtProperties.getExcludedUrls();
        return SkipPathsExceptOneRequestMatcher.create(pathToSkip, jwtProperties.getUrlTokenProtectedPattern());
    }
}
