package pe.cortzotinnus.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PairOfTokens {
    private final Token accessToken;
    private final Token refreshToken;
}
