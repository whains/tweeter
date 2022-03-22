package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public interface AuthTokenDAOInterface {
    void addToken(AuthToken authToken);
    void deleteToken(AuthToken authToken);
    void checkAuthTokens();
}
