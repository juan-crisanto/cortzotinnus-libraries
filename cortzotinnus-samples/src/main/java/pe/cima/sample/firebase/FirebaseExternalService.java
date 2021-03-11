package pe.cima.sample.firebase;

import feign.FeignException;
import lombok.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;

@FeignClient(name = "firebaseExternalService", url = "${com.cortzotinnus.sample.googleapis.base-url}") // firebase.api.url
public interface FirebaseExternalService {

    @PostMapping("/identitytoolkit/v3/relyingparty/verifyPassword")
    FirebaseRetrieveResponse retrieveToken(@RequestBody FirebaseRetrieveRequest request, @RequestParam("key") String key) throws FeignException;

    @Getter
    @Setter
    @AllArgsConstructor
    class FirebaseRetrieveRequest implements Serializable {
        private String email;
        private String password;
        private boolean returnSecureToken;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    class FirebaseRetrieveResponse implements Serializable {
        private String kind;
        private String localId;
        private String email;
        private String displayName;
        private String idToken;
        private boolean registered;
        private String refreshToken;
        private String expiresIn;
    }
}
