package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.server.dao.FactoryDAO;
import edu.byu.cs.tweeter.server.util.FakeData;

public class FeedService extends AuthenticationService {

    public FeedService(FactoryDAO factoryDAO) {
        super(factoryDAO);
    }

    public FeedResponse getFeed(FeedRequest request) {
        if (request == null) {
            throw new RuntimeException("[BadRequest400] 400");
        } else if (request.getLimit() < 0) {
            throw new RuntimeException("[BadRequest500] 500");
        }

        checkAuthTokens();
        return factoryDAO.getFeedDAO().getFeed(request);
    }
}
