package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
import edu.byu.cs.tweeter.server.dao.FactoryDAO;
import edu.byu.cs.tweeter.server.service.FollowingService;
import edu.byu.cs.tweeter.server.util.Pair;

public class GetFollowingHandler implements RequestHandler<FollowingRequest, FollowingResponse> {

    @Override
    public FollowingResponse handleRequest(FollowingRequest request, Context context) {
        FollowingService service = new FollowingService(new FactoryDAO());
        Pair<List<User>, Boolean> pair = service.getFollowees(request);

        FollowingResponse response = new FollowingResponse(pair.getFirst(), pair.getSecond());

        return response;
    }
}