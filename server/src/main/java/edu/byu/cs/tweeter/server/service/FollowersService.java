package edu.byu.cs.tweeter.server.service;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
import edu.byu.cs.tweeter.server.dao.FactoryDAO;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.server.util.FakeData;
import edu.byu.cs.tweeter.server.util.Pair;

public class FollowersService extends AuthenticationService {

    public FollowersService(FactoryDAO factoryDAO) {
        super(factoryDAO);
    }

    public Pair<List<User>, Boolean> getFollowers(FollowersRequest request) {
        if (request == null) {
            throw new RuntimeException("[BadRequest400] 400");
        } else if (request.getLimit() < 0) {
            throw new RuntimeException("[BadRequest500] 500");
        }

        FollowDAO followDAO = factoryDAO.getFollowDAO();
        FollowersResponse followersResponse = followDAO.getFollowers(request);
        Pair<List<User>, Boolean> result = new Pair<>(followersResponse.getFollowers(), followersResponse.getHasMorePages());

        checkAuthTokens();
        return result;
    }
}
