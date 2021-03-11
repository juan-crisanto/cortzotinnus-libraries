package pe.cortzotinnus.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pe.cortzotinnus.security.config.properties.AuthenticationSecurityConfigurationProperties;
import pe.cortzotinnus.security.provider.TokenRetrieverAuthenticationProvider;
import pe.cortzotinnus.security.web.authentication.AuthorizationTokensAuthenticationHandler;
import pe.cortzotinnus.security.web.filter.UsernamePasswordAuthenticationProcessingFilter;

@Order(value = SecurityConfigurationConstant.AUTHENTICATION_CONFIG_ORDER)
@RequiredArgsConstructor
public class AuthenticationConfigurerAdapter extends WebSecurityConfigurerAdapter {

    private final ObjectMapper objectMapper;
    private final AuthorizationTokensAuthenticationHandler authorizationTokensAuthenticationHandler;
    private final TokenRetrieverAuthenticationProvider tokenRetrieverAuthenticationProvider;
    private final AuthenticationSecurityConfigurationProperties authenticationProperties;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    protected AuthenticationProvider authenticationProvider() {
        return this.tokenRetrieverAuthenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        BaseHttpStatelessSecurityConfig
                .forUrlEntryPoint(authenticationProperties.getUrlEntryPointPattern())
                .accept(http);
        http
                .authorizeRequests()
                .antMatchers(authenticationProperties.getLoginProcessingUrl(), authenticationProperties.getLogoutUrl())
                .permitAll()
                .and()
                .addFilterAfter(createAuthenticationFilter(this.authorizationTokensAuthenticationHandler, this.authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl(authenticationProperties.getLogoutUrl());

    }

    private UsernamePasswordAuthenticationProcessingFilter createAuthenticationFilter(final AuthorizationTokensAuthenticationHandler authorizationTokensAuthenticationHandler, final AuthenticationManager authenticationManager) {
        UsernamePasswordAuthenticationProcessingFilter authFilter = new UsernamePasswordAuthenticationProcessingFilter(authenticationProperties.getLoginProcessingUrl(), this.objectMapper);
        authFilter.setAuthenticationFailureHandler(authorizationTokensAuthenticationHandler);
        authFilter.setAuthenticationSuccessHandler(authorizationTokensAuthenticationHandler);
        authFilter.setAuthenticationManager(authenticationManager);
        return authFilter;
    }
}
