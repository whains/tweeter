package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.server.dao.FactoryDAO;
import edu.byu.cs.tweeter.server.service.FollowersService;
import edu.byu.cs.tweeter.server.util.Pair;

public class GetFollowersHandler implements RequestHandler<FollowersRequest, FollowersResponse> {

    @Override
    public FollowersResponse handleRequest(FollowersRequest request, Context context) {
        FollowersService service = new FollowersService(new FactoryDAO());
        Pair<List<User>, Boolean> pair = service.getFollowers(request);

        FollowersResponse response = new FollowersResponse(pair.getFirst(), pair.getSecond());

        return response;
    }
}
