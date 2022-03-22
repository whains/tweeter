package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.GetFollowCountRequest;
import edu.byu.cs.tweeter.model.net.response.GetFollowingCountResponse;
import edu.byu.cs.tweeter.server.dao.FactoryDAO;
import edu.byu.cs.tweeter.server.service.GetFollowingCountService;

public class GetFollowingCountHandler implements RequestHandler<GetFollowCountRequest, GetFollowingCountResponse> {
    @Override
    public GetFollowingCountResponse handleRequest(GetFollowCountRequest getFollowCountRequest, Context context) {
        GetFollowingCountService getFollowingCountService = new GetFollowingCountService(new FactoryDAO());
        return getFollowingCountService.getFollowingCount(getFollowCountRequest);
    }
}
