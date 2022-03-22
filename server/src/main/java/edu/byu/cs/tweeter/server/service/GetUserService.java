package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.response.GetUserResponse;
import edu.byu.cs.tweeter.server.dao.FactoryDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.server.util.FakeData;

public class GetUserService extends AuthenticationService {

    public GetUserService(FactoryDAO factoryDAO) {
        super(factoryDAO);
    }

    public User getUser(GetUserRequest request) {
        if (request == null) {
            throw new RuntimeException("[BadRequest400] 400");
        }

        UserDAO userDAO = factoryDAO.getUserDAO();
        GetUserResponse getUserResponse = userDAO.getUser(request);

        if (getUserResponse.isSuccess()) {

            checkAuthTokens();
            return getUserResponse.getUser();
        }
        else {
            throw new RuntimeException(getUserResponse.getMessage());
        }
    }
}
