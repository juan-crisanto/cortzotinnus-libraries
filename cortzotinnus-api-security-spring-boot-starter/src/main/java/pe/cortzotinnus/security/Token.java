package pe.cortzotinnus.security;

public interface Token {

    enum TokenType{
        ACCESS, REFRESH
    }

    String getRawToken();

    TokenType getType();

}
