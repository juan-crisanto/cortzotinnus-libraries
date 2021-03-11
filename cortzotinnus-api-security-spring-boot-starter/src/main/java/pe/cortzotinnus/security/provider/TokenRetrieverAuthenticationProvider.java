package pe.cortzotinnus.security.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import pe.cortzotinnus.security.PairOfTokens;
import pe.cortzotinnus.security.authentication.UsernamePasswordAuthenticationToken;
import pe.cortzotinnus.security.retriever.TokenRetriever;

import java.util.Collections;

@RequiredArgsConstructor
public class TokenRetrieverAuthenticationProvider implements AuthenticationProvider {

    private final TokenRetriever tokenRetriever;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final PairOfTokens authorizationTokens = retrievePairOfTokens((UsernamePasswordAuthenticationToken) authentication);
        return UsernamePasswordAuthenticationToken.fromAuthorizationTokens(authorizationTokens, Collections.emptyList());
    }

    private PairOfTokens retrievePairOfTokens(UsernamePasswordAuthenticationToken authentication) {
        return this.tokenRetriever.retrieveToken(authentication.getUsername(), authentication.getPassword());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
