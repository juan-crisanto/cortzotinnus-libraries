package pe.cima.sample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pe.cortzotinnus.security.DefaultUser;
import pe.cortzotinnus.security.JsonWebToken;
import pe.cortzotinnus.security.PairOfTokens;
import pe.cortzotinnus.security.retriever.TokenRetriever;
import pe.cortzotinnus.security.retriever.UserRetrieverService;

import java.util.Collections;

@Configuration
public class MockRetrieverConfig {

    @Bean
    public UserRetrieverService mockUserRetriever() {
        return (token -> DefaultUser
                .builder()
                .userId("idUser")
                .name("username")
                .email("mock@mail.com")
                .authorities(Collections.emptyList())
                .build()
        );
    }

    @Bean
    public TokenRetriever tokenRetriever() {
        return ((username, password) ->
                new PairOfTokens(
                        JsonWebToken.createAccessToken("accessToken-firebase"),
                        JsonWebToken.createRefreshToken("refreshToken-firebase")
                )
        );
    }

}
