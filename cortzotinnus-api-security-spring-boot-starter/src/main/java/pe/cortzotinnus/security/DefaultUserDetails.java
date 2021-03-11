package pe.cortzotinnus.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class DefaultUserDetails implements UserDetails {

    private final boolean enabled = true;
    private final boolean credentialsNonExpired = true;
    private final boolean accountNonLocked = true;
    private final boolean accountNonExpired = true;
    private final String password = null;

    private final DefaultUser user;
    private final Collection<? extends GrantedAuthority> authorities;

    public DefaultUserDetails(final DefaultUser user) {
        this.user = user;
        this.authorities = user.getAuthorities().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

}