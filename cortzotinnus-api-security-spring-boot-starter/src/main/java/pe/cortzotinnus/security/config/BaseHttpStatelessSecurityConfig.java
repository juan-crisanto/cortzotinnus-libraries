package pe.cortzotinnus.security.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

import java.util.function.Consumer;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseHttpStatelessSecurityConfig implements Consumer<HttpSecurity> {

    private final String urlEntryPointPattern;

    public static BaseHttpStatelessSecurityConfig forUrlEntryPoint(final String urlEntryPointPattern) {
        return new BaseHttpStatelessSecurityConfig(urlEntryPointPattern);
    }

    @SneakyThrows
    @Override
    public void accept(HttpSecurity httpSecurity) {
        httpSecurity.antMatcher(this.urlEntryPointPattern)
                .csrf()
                .disable()
                .cors()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new Http403ForbiddenEntryPoint())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
