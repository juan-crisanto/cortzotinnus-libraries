package pe.cortzotinnus.security.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pe.cortzotinnus.security.Token;

import java.util.*;

public class JWTAuthenticationToken implements Authentication {
    private final Token token;
    private final boolean authenticated;
    private final List<GrantedAuthority> authorities;
    private final UserDetails authenticatedUser;
    private Object details;


    private JWTAuthenticationToken(UserDetails userDetails) {
        this.authorities = new ArrayList<>(userDetails.getAuthorities());
        this.authenticated = true;
        this.authenticatedUser = userDetails;
        this.token = null;
    }

    private JWTAuthenticationToken(Token token) {
        this.token = token;
        this.authorities = Collections.emptyList();
        this.authenticated = false;
        this.authenticatedUser = null;
    }

    public static JWTAuthenticationToken fromToken(final Token token) {
        return new JWTAuthenticationToken(token);
    }

    public static JWTAuthenticationToken fromUser(final UserDetails userDetails) {
        return new JWTAuthenticationToken(userDetails);
    }

    public void setDetails(final Object details) {
        this.details = details;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Object getCredentials() {
        return Optional.ofNullable(this.token).orElseThrow(() -> new IllegalStateException("El token no cuenta con credenciales"));
    }

    @Override
    public Object getDetails() {
        return this.details;
    }

    @Override
    public Object getPrincipal() {
        return Optional.ofNullable(this.authenticatedUser).orElseThrow(() -> new IllegalStateException("El token no se encuentra autenticado"));
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw new IllegalArgumentException("Este token debe ser autenticado");
    }

    @Override
    public String getName() {
        return Optional.ofNullable(this.authenticatedUser).orElseThrow(() -> new IllegalStateException("El token no se encuentra autenticado")).getUsername();
    }
}
