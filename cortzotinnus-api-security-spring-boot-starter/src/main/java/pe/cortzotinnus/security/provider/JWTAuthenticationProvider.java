package pe.cortzotinnus.security.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import pe.cortzotinnus.security.DefaultUser;
import pe.cortzotinnus.security.DefaultUserDetails;
import pe.cortzotinnus.security.Token;
import pe.cortzotinnus.security.User;
import pe.cortzotinnus.security.authentication.JWTAuthenticationToken;
import pe.cortzotinnus.security.retriever.UserRetrieverService;

import java.util.ArrayList;

@RequiredArgsConstructor
public class JWTAuthenticationProvider implements AuthenticationProvider {

    private final UserRetrieverService userRetrieverService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final JWTAuthenticationToken authenticationToken = (JWTAuthenticationToken) authentication;
        final Token token = (Token) authenticationToken.getCredentials();
        User user = userRetrieverService.retrieveUserFromToken(token);
        final DefaultUserDetails defaultUserDetails = new DefaultUserDetails((DefaultUser) user);
        return JWTAuthenticationToken.fromUser(defaultUserDetails);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JWTAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
