package pe.cortzotinnus.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import pe.cortzotinnus.security.config.properties.BasicAuthenticationSecurityConfigurationProperties;

@RequiredArgsConstructor
@Order(value = SecurityConfigurationConstant.BASIC_AUTHENTICATION_ORDER)
public class BasicAuthConfigurerAdapter extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final BasicAuthenticationSecurityConfigurationProperties properties;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser(properties.getUsername())
                .password(
                        this.passwordEncoder.encode(properties.getPassword())
                )
                .authorities(properties.getRoles().toArray(new String[0]));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        BaseHttpStatelessSecurityConfig
                .forUrlEntryPoint(properties.getUrlEntryPointPattern())
                .accept(http);
        http
                .authorizeRequests()
                .antMatchers(properties.getUrlEntryPointPattern())
                .authenticated()
                .and()
                .httpBasic();
    }
}
