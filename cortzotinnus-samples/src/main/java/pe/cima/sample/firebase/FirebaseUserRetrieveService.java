package pe.cima.sample.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import pe.cortzotinnus.security.DefaultUser;
import pe.cortzotinnus.security.Token;
import pe.cortzotinnus.security.User;
import pe.cortzotinnus.security.retriever.UserRetrieverService;

import java.util.Collections;
import java.util.Map;

@Slf4j
//@Service
public class FirebaseUserRetrieveService implements UserRetrieverService {

    private final String UID = "user_id";
    private final String EMAIL = "email";
    private final String NAME = "name";
    private final String PICTURE = "picture";

    @Override
    public User retrieveUserFromToken(Token token) {
        if (token.getType() != Token.TokenType.ACCESS) {
            throw new BadCredentialsException("INVALID_TOKEN_TYPE");
        }
        try {
            FirebaseToken tokenFirebase = FirebaseAuth.getInstance().verifyIdToken(token.getRawToken());
            log.info("Successfully validated token for {}", tokenFirebase.getEmail());
            Map<String, Object> claims = tokenFirebase.getClaims();
            return DefaultUser.builder()
                    .userId((String) claims.get(UID))
                    .email((String) claims.get(EMAIL))
                    .name((String) claims.get(NAME))
                    //.picture((String) claims.get(PICTURE))
                    .authorities(Collections.emptyList())
                    .build();
        } catch (FirebaseAuthException e) {
            throw new BadCredentialsException(e.getMessage(), e);
        }
    }
}
