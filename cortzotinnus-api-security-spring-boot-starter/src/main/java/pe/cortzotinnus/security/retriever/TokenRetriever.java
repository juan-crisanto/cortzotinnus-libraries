package pe.cortzotinnus.security.retriever;

import pe.cortzotinnus.security.PairOfTokens;

@FunctionalInterface
public interface TokenRetriever {

    PairOfTokens retrieveToken(String username, String password);

}
