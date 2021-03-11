package pe.cima.sample.firebase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.cortzotinnus.security.JsonWebToken;
import pe.cortzotinnus.security.PairOfTokens;
import pe.cortzotinnus.security.Token;
import pe.cortzotinnus.security.retriever.TokenRetriever;

@Slf4j
//@Service
@RequiredArgsConstructor
public class FirebaseTokenRetriever implements TokenRetriever {

    private final FirebaseExternalService firebaseExternalService;

    private final FirebaseProperties firebaseProperties;

    @Override
    public PairOfTokens retrieveToken(String username, String password) {
        try {
            final FirebaseExternalService.FirebaseRetrieveRequest request = new FirebaseExternalService.FirebaseRetrieveRequest(username, password, true);
            final FirebaseExternalService.FirebaseRetrieveResponse response = firebaseExternalService.retrieveToken(request, firebaseProperties.getFirebaseKey());
            final Token accessToken = JsonWebToken.createAccessToken(response.getIdToken());
            final Token refreshToken = JsonWebToken.createRefreshToken(response.getRefreshToken());
            return new PairOfTokens(accessToken, refreshToken);
        } catch (Exception e) {
            log.error("Error firebase connection: {}", e.getLocalizedMessage());
            throw e;
        }
    }

}
