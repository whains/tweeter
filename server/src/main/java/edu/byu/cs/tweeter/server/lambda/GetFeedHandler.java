package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.server.dao.FactoryDAO;
import edu.byu.cs.tweeter.server.service.FeedService;
import edu.byu.cs.tweeter.server.util.Pair;

public class GetFeedHandler implements RequestHandler<FeedRequest, FeedResponse> {

    @Override
    public FeedResponse handleRequest(FeedRequest request, Context context) {
        FeedService service = new FeedService(new FactoryDAO());
        //Pair<List<Status>, Boolean> pair = null;
        //pair = service.getFeed(request);

        //FeedResponse response = new FeedResponse(pair.getFirst(), pair.getSecond());

        return service.getFeed(request);
    }
}
