package pe.cortzotinnus.security.web.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.Answer;
import org.springframework.security.authentication.BadCredentialsException;
import pe.cortzotinnus.security.Token;
import pe.cortzotinnus.security.config.properties.WebSecurityConfigurationProperties;

import javax.servlet.http.HttpServletRequest;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class HttpHeaderTokenExtractorTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private WebSecurityConfigurationProperties properties;

    @InjectMocks
    private HttpHeaderTokenExtractor httpHeaderTokenExtractor;

    @Test
    void Given_HeaderValidHttpServletRequest_whenValidTokenExistInHeaders_thenTokenFound() {
        Mockito.when(properties.getHeaderAuthorizationName()).thenReturn("Authorization");
        Mockito.when(properties.getHeaderAuthorizationPrefix()).thenReturn("Bearer ");
        final Map<String, String> headersWithToken = this.buildHeadersWithToken();
        Mockito.when(request.getHeaderNames()).thenReturn(this.buildHeadersName(headersWithToken));
        Mockito.doAnswer(this.buildAnswerHeader(headersWithToken)).when(request).getHeader("Authorization");
        final Token token = httpHeaderTokenExtractor.extractAccessToken(request);
        Assertions.assertNotNull(token);
    }

    @Test
    void Given_HeaderValidHttpServletRequest_whenValidTokenNotExistInHeaders_thenBadCredentialsException() {
        Mockito.when(properties.getHeaderAuthorizationName()).thenReturn("Authorization");
        Mockito.when(properties.getHeaderAuthorizationPrefix()).thenReturn("Bearer ");
        final Map<String, String> headersWithToken = this.buildHeadersNoToken();
        Mockito.when(request.getHeaderNames()).thenReturn(this.buildHeadersName(headersWithToken));
        Mockito.doAnswer(this.buildAnswerHeader(headersWithToken)).when(request).getHeader("Authorization");
        Assertions.assertThrows(BadCredentialsException.class, () -> {
            httpHeaderTokenExtractor.extractAccessToken(request);
        });
    }

    private Map<String, String> buildHeadersWithToken() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer eyadyasd2asd");
        return headers;
    }

    private Map<String, String> buildHeadersNoToken() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return headers;
    }

    private Enumeration<String> buildHeadersName(Map<String, String> headers) {
        final Iterator<String> iterator = headers.keySet().iterator();
        final Enumeration<String> headerNames = new Enumeration<String>() {
            @Override
            public boolean hasMoreElements() {
                return iterator.hasNext();
            }

            @Override
            public String nextElement() {
                return iterator.next();
            }
        };
        return headerNames;
    }

    private Answer<String> buildAnswerHeader(Map<String, String> headers) {
        return new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return headers.get((String) args[0]);
            }
        };
    }

}