package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;
import edu.byu.cs.tweeter.server.dao.FactoryDAO;
import edu.byu.cs.tweeter.server.dao.FollowDAO;

public class FollowService extends AuthenticationService {

    public FollowService(FactoryDAO factoryDAO) {
        super(factoryDAO);
    }

    public IsFollowerResponse follow(IsFollowerRequest followRequest) {
        FollowDAO followDAO = factoryDAO.getFollowDAO();

        checkAuthTokens();
        return followDAO.follow(followRequest);
    }
}
