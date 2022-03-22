package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.server.dao.FactoryDAO;

public class PostStatusService extends AuthenticationService {

    public PostStatusService(FactoryDAO factoryDAO) {
        super(factoryDAO);
    }

    public PostStatusResponse postStatus(PostStatusRequest postStatusRequest) {
        if (postStatusRequest == null) {
            throw new RuntimeException("[BadRequest400] 400");
        }

        checkAuthTokens();
        return factoryDAO.getStoryDAO().postStatus(postStatusRequest);
    }
}
