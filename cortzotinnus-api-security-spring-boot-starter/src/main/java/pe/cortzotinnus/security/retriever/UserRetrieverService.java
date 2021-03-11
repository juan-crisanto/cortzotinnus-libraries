package pe.cortzotinnus.security.retriever;

import pe.cortzotinnus.security.Token;
import pe.cortzotinnus.security.User;

@FunctionalInterface
public interface UserRetrieverService {

    User retrieveUserFromToken(Token token);

}
