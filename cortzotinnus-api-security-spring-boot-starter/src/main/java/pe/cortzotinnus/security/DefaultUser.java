package pe.cortzotinnus.security;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Collection;

@Getter
@Builder
@ToString
public class DefaultUser implements User {

    private String userId;
    private String email;
    private String name;
    private Collection<String> authorities;

    @Override
    public String getUsername() {
        return this.getEmail();
    }
}