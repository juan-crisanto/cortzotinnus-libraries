package pe.cortzotinnus.security.web.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.util.matcher.RequestMatcher;
import pe.cortzotinnus.security.Token;
import pe.cortzotinnus.security.authentication.JWTAuthenticationToken;
import pe.cortzotinnus.security.web.util.TokenExtractor;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final TokenExtractor tokenExtractor;

    public JWTAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher, TokenExtractor tokenExtractor) {
        super(requiresAuthenticationRequestMatcher);
        this.tokenExtractor = tokenExtractor;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        final Token token = tokenExtractor.extractAccessToken(request);
        final JWTAuthenticationToken jwtToken = JWTAuthenticationToken.fromToken(token);
        jwtToken.setDetails(new WebAuthenticationDetails(request));
        return this.getAuthenticationManager().authenticate(jwtToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
    }
}
