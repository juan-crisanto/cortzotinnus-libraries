package pe.cortzotinnus.security.web.authentication.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@NoArgsConstructor
@Setter
@Getter
public class FailureAuthenticationResponseDto implements Serializable {

    private String errorCode;
    private String errorMessage;

    @Builder
    public FailureAuthenticationResponseDto(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
