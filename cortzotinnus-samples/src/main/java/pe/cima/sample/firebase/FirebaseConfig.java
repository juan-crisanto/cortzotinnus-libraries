package pe.cima.sample.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.BadCredentialsException;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.Optional;
import com.google.firebase.auth.FirebaseAuth;

@Slf4j
@Order(SecurityProperties.IGNORED_ORDER)
@RequiredArgsConstructor
//@Configuration
@EnableConfigurationProperties(FirebaseProperties.class)
public class FirebaseConfig {

    private final FirebaseProperties firebaseProperties;

    @PostConstruct
    public void init() throws Exception {
        try (InputStream inputStream = firebaseProperties.getResources().getInputStream()) {
            FirebaseOptions.Builder builder = new FirebaseOptions
                    .Builder()
                    .setCredentials(GoogleCredentials.fromStream(inputStream));
            builder.setDatabaseUrl(Optional.ofNullable(firebaseProperties.getDatabaseUrl()).orElseThrow(() -> new BadCredentialsException("No se encontro url de firebase")));
            FirebaseApp.initializeApp(builder.build());
            log.info("Firebase connected by {}", firebaseProperties.getResources().getFilename());
        }
    }
}
