package pe.cortzotinnus.security.provider;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.Authentication;
import pe.cortzotinnus.security.JsonWebToken;
import pe.cortzotinnus.security.DefaultUser;
import pe.cortzotinnus.security.Token;
import pe.cortzotinnus.security.authentication.JWTAuthenticationToken;
import pe.cortzotinnus.security.retriever.UserRetrieverService;

import java.util.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class JWTAuthenticationProviderTest {

    @Mock
    private UserRetrieverService userRetrieverService;

    @InjectMocks
    private JWTAuthenticationProvider jwtAuthenticationProvider;

    @Test
    void Given_authenticationValid_whenAuthenticationFromToken_thenAuthenticationFromUserDetails() {
        final Authentication authToken = JWTAuthenticationToken.fromToken(JsonWebToken.createAccessToken("eayawraf3452"));
        Mockito.when(userRetrieverService.retrieveUserFromToken(Mockito.any(Token.class))).thenReturn(DefaultUser.builder()
                .userId("123")
                .email("abcd@correo.com")
                .name("user")
                .authorities(Collections.emptyList())
                .build());
        final Authentication auth = jwtAuthenticationProvider.authenticate(authToken);
        Assertions.assertNotNull(auth);
    }


}