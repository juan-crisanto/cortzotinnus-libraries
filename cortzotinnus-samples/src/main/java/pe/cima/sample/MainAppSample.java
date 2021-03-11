package pe.cima.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
        MockRetrieverConfig.class
})
@EnableFeignClients
public class MainAppSample {

    public static void main(String[] args) {
        SpringApplication.run(MainAppSample.class, args);
    }

}
