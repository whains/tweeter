package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;
import edu.byu.cs.tweeter.server.dao.FactoryDAO;
import edu.byu.cs.tweeter.server.dao.FollowDAO;

public class IsFollowerService extends AuthenticationService {

    public IsFollowerService(FactoryDAO factoryDAO) {
        super(factoryDAO);
    }

    public IsFollowerResponse isFollower(IsFollowerRequest isFollowerRequest) {
        if (isFollowerRequest == null) {
            throw new RuntimeException("[BadRequest400] 400");
        }

        FollowDAO followDAO = factoryDAO.getFollowDAO();

        checkAuthTokens();
        return followDAO.isFollower(isFollowerRequest);
    }
}
