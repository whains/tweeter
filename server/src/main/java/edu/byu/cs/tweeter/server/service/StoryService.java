package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.server.dao.FactoryDAO;
import edu.byu.cs.tweeter.server.util.FakeData;

public class StoryService extends AuthenticationService {

    public StoryService(FactoryDAO factoryDAO) {
        super(factoryDAO);
    }

    public StoryResponse getStory(StoryRequest request) {
        if (request == null) {
            throw new RuntimeException("[BadRequest400] 400");
        } else if (request.getLimit() < 0) {
            throw new RuntimeException("[BadRequest500] 500");
        }

        checkAuthTokens();
        return factoryDAO.getStoryDAO().getStory(request);
    }
}
