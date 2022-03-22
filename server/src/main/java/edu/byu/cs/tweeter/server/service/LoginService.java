package edu.byu.cs.tweeter.server.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.model.net.response.LogoutResponse;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;
import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.FactoryDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.server.util.FakeData;

public class LoginService extends AuthenticationService {

    public LoginService(FactoryDAO factoryDAO) {
        super(factoryDAO);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        if (loginRequest == null) {
            throw new RuntimeException("[BadRequest400] 400");
        }

        UserDAO userDAO = factoryDAO.getUserDAO();

        loginRequest.setPassword(hashPassword(loginRequest.getPassword()));
        LoginResponse loginResponse = userDAO.login(loginRequest);

        if (loginResponse.isSuccess()) {

            AuthTokenDAO authTokenDAO = factoryDAO.getAuthTokenDAO();
            AuthToken authToken = generateNewToken();
            authTokenDAO.addToken(authToken);

            loginResponse.setAuthToken(authToken);

            checkAuthTokens();
            return loginResponse;
        }
        else {
            throw new RuntimeException(loginResponse.getMessage());
        }
    }

    FakeData getFakeData() {
        return new FakeData();
    }
}
