package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.net.request.GetFollowCountRequest;
import edu.byu.cs.tweeter.model.net.response.GetFollowersCountResponse;
import edu.byu.cs.tweeter.server.dao.FactoryDAO;

public class GetFollowersCountService extends AuthenticationService {

    public GetFollowersCountService(FactoryDAO factoryDAO) {
        super(factoryDAO);
    }

    public GetFollowersCountResponse getFollowersCount(GetFollowCountRequest getFollowCountRequest) {
        if (getFollowCountRequest == null) {
            throw new RuntimeException("[BadRequest400] 400");
        }

        checkAuthTokens();
        return factoryDAO.getFollowDAO().getFollowersCount(getFollowCountRequest);
    }
}
