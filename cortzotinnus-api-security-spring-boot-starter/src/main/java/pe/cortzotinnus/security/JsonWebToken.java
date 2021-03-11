package pe.cortzotinnus.security;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class JsonWebToken implements Token {

    private final TokenType type;
    private final String rawToken;

    public static Token createAccessToken(final String rawToken) {
        return new JsonWebToken(TokenType.ACCESS, rawToken);
    }

    public static Token createRefreshToken(final String rawToken) {
        return new JsonWebToken(TokenType.REFRESH, rawToken);
    }

    @Override
    public String getRawToken() {
        return rawToken;
    }

    @Override
    public TokenType getType() {
        return type;
    }
}
