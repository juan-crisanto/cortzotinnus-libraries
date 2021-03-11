package pe.cima.sample.firebase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pe.cortzotinnus.security.DefaultUser;
import pe.cortzotinnus.security.DefaultUserDetails;
import pe.cortzotinnus.security.authentication.JWTAuthenticationToken;

@Slf4j
@RequiredArgsConstructor
public class SecurityUtils {

    public static DefaultUser currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof JWTAuthenticationToken) {
            JWTAuthenticationToken jwtAuthenticationToken = (JWTAuthenticationToken) auth;
            return ((DefaultUserDetails) jwtAuthenticationToken.getPrincipal()).getUser();
        }
        return DefaultUser.builder().build();
    }

}
