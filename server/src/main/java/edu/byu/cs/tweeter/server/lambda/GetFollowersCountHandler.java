package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.GetFollowCountRequest;
import edu.byu.cs.tweeter.model.net.response.GetFollowersCountResponse;
import edu.byu.cs.tweeter.server.dao.FactoryDAO;
import edu.byu.cs.tweeter.server.service.GetFollowersCountService;

public class GetFollowersCountHandler implements RequestHandler<GetFollowCountRequest, GetFollowersCountResponse> {
    @Override
    public GetFollowersCountResponse handleRequest(GetFollowCountRequest getFollowCountRequest, Context context) {
        GetFollowersCountService getFollowersCountService = new GetFollowersCountService(new FactoryDAO());
        return getFollowersCountService.getFollowersCount(getFollowCountRequest);
    }
}
