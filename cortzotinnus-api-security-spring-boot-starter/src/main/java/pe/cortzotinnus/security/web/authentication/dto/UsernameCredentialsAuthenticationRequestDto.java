package pe.cortzotinnus.security.web.authentication.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsernameCredentialsAuthenticationRequestDto {

    private final String username;
    private final String password;

    public UsernameCredentialsAuthenticationRequestDto(@JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }
}
