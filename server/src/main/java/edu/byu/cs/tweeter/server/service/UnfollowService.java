package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;
import edu.byu.cs.tweeter.model.net.response.UnfollowResponse;
import edu.byu.cs.tweeter.server.dao.FactoryDAO;

public class UnfollowService extends AuthenticationService {

    public UnfollowService(FactoryDAO factoryDAO) {
        super(factoryDAO);
    }

    public IsFollowerResponse unfollow(IsFollowerRequest unfollowRequest) {
        if (unfollowRequest == null) {
            throw new RuntimeException("[BadRequest400] 400");
        }

        checkAuthTokens();
        return factoryDAO.getFollowDAO().unfollow(unfollowRequest);
    }
}
