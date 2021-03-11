package pe.cortzotinnus.security.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

@Import({
        AllAutoConfigurationSettings.class
})
@Configuration
@Slf4j
public class CortzoTinnusSecurityAutoConfiguration {

    @PostConstruct
    public void onLoad() {
        log.info("Cima Security {} auto configuration", CortzoTinnusSecurityAutoConfiguration.class);
    }

}
