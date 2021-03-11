package pe.cortzotinnus.security.authentication;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import pe.cortzotinnus.security.PairOfTokens;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UsernamePasswordAuthenticationToken implements Authentication {

    private final PairOfTokens authorizationTokens;
    private final boolean authenticated;
    private final List<GrantedAuthority> authorities;
    @Getter
    private final String username;
    @Getter
    private final String password;
    @Setter
    private Object details;

    private UsernamePasswordAuthenticationToken(final String username, final String password) {
        this.username = username;
        this.password = password;
        this.authenticated = false;
        this.authorizationTokens = null;
        this.authorities = Collections.emptyList();
    }

    private UsernamePasswordAuthenticationToken(final PairOfTokens authorizationTokens, final List<GrantedAuthority> authorities) {
        this.authorizationTokens = authorizationTokens;
        this.authorities = authorities;
        this.authenticated = true;
        this.username = StringUtils.EMPTY;
        this.password = StringUtils.EMPTY;
    }

    public static UsernamePasswordAuthenticationToken fromCredentials(final String firebaseUsername, final String firebasePassword) {
        return new UsernamePasswordAuthenticationToken(firebaseUsername, firebasePassword);
    }

    public static UsernamePasswordAuthenticationToken fromAuthorizationTokens(final PairOfTokens authorizationTokens, final List<GrantedAuthority> authorities) {
        return new UsernamePasswordAuthenticationToken(authorizationTokens, authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Object getCredentials() {
        return Optional
                .ofNullable(this.password)
                .orElseThrow(() -> new IllegalStateException("Credentials are empty!!!"));
    }

    @Override
    public Object getDetails() {
        return this.details;
    }

    @Override
    public Object getPrincipal() {
        return Optional
                .ofNullable(this.authorizationTokens)
                .orElseThrow(() -> new IllegalStateException("Unauthenticated token"));
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        throw new IllegalArgumentException("Authentication stated can't be set");
    }

    @Override
    public String getName() {
        return Optional
                .ofNullable(this.username)
                .orElseThrow(() -> new IllegalStateException("Username empty!!!"));
    }
}
