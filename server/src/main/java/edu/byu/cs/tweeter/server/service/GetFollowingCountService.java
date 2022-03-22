package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.net.request.GetFollowCountRequest;
import edu.byu.cs.tweeter.model.net.response.GetFollowingCountResponse;
import edu.byu.cs.tweeter.server.dao.FactoryDAO;

public class GetFollowingCountService extends AuthenticationService {

    public GetFollowingCountService(FactoryDAO factoryDAO) {
        super(factoryDAO);
    }

    public GetFollowingCountResponse getFollowingCount(GetFollowCountRequest getFollowCountRequest) {
        if (getFollowCountRequest == null) {
            throw new RuntimeException("[BadRequest400] 400");
        }

        checkAuthTokens();
        return factoryDAO.getFollowDAO().getFollowingCount(getFollowCountRequest);
    }
}
