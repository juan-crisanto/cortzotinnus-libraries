package pe.cortzotinnus.security.web.authentication.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@NoArgsConstructor
@Setter
@Getter
public class SuccessAuthenticationResponseDto implements Serializable {

    private String accessToken;
    private String refreshToken;
    private Integer expirationTimeInMinutes;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime expireAt;

    @Builder
    public SuccessAuthenticationResponseDto(String accessToken, String refreshToken, Integer expirationTimeInMinutes, LocalDateTime expireAt) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expirationTimeInMinutes = expirationTimeInMinutes;
        this.expireAt = expireAt;
    }
}
