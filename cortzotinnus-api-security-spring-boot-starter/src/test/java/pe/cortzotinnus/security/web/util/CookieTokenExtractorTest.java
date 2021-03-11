package pe.cortzotinnus.security.web.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.authentication.BadCredentialsException;
import pe.cortzotinnus.security.Token;
import pe.cortzotinnus.security.config.properties.WebSecurityConfigurationProperties;

import javax.servlet.http.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CookieTokenExtractorTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private WebSecurityConfigurationProperties properties;

    @InjectMocks
    private CookieTokenExtractor cookieTokenExtractor;

    @Test
    void Given_CookieValidHttpServletRequest_whenValidTokenExistInCookies_thenTokenFound() {
        Mockito.when(properties.getAuthorizationCookieName()).thenReturn("X-Authorization-Token");
        final Cookie[] cookies = {new Cookie("X-Authorization-Token", "eyasdasd21342cwfd")};
        Mockito.when(request.getCookies()).thenReturn(cookies);
        final Token token = cookieTokenExtractor.extractAccessToken(request);
        Assertions.assertNotNull(token);
    }

    @Test
    void Given_CookieValidHttpServletRequest_whenTokenNotExistInCookies_thenBadCredentialsException() {
        Mockito.when(properties.getAuthorizationCookieName()).thenReturn("X-Authorization-Token");
        final Cookie[] cookies = {new Cookie("cookie", "eyasdasd21342cwfd")};
        Mockito.when(request.getCookies()).thenReturn(cookies);
        Assertions.assertThrows(BadCredentialsException.class, () -> cookieTokenExtractor.extractAccessToken(request));
    }
}