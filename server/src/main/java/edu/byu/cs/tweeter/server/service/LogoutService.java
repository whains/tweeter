package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.response.LogoutResponse;
import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.FactoryDAO;

public class LogoutService extends AuthenticationService {

    public LogoutService(FactoryDAO factoryDAO) {
        super(factoryDAO);
    }

    public LogoutResponse logout(LogoutRequest logoutRequest) {
        if (logoutRequest == null) {
            throw new RuntimeException("[BadRequest400] 400");
        }

        AuthTokenDAO authTokenDAO = factoryDAO.getAuthTokenDAO();
        authTokenDAO.deleteToken(logoutRequest.getAuthToken());

        checkAuthTokens();
        return new LogoutResponse();
    }
}
