package pe.cortzotinnus.security.web.util;

import pe.cortzotinnus.security.Token;

import javax.servlet.http.HttpServletRequest;

public interface TokenExtractor {

    Token extractAccessToken(HttpServletRequest request);
}
