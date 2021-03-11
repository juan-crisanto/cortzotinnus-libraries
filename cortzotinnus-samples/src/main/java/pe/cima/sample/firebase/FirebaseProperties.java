package pe.cima.sample.firebase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@RefreshScope
@Component
@Data
@ConfigurationProperties("pe.cima.security.web.firebase")
public class FirebaseProperties {

    private String firebaseKey;
    private String url;
    private Resource resources;
    private String databaseUrl;
}
