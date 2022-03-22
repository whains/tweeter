package edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks;

import android.os.Handler;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.PagedTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.util.Pair;

public class GetFeedTask extends PagedTask<Status> {
    public GetFeedTask(FeedRequest request, Handler messageHandler) {
        super(messageHandler, request.getLimit(), request.getLastStatus(), request.getAuthToken());
        this.request = request;
        this.targetUser = targetUser;
    }

    private static final String LOG_TAG = "GetFeedTask";
    static final String URL_PATH = "/getfeed";

    public static final String STATUSES_KEY = "statuses";
    public static final String MORE_PAGES_KEY = "more-pages";

    private FeedRequest request;
    private User targetUser;

    private List<Status> statuses;
    private boolean hasMorePages;

    @Override
    protected Pair<List<Status>, Boolean> getItems() throws IOException, TweeterRemoteException {
        FeedResponse response = getServerFacade().getFeed(request, URL_PATH);
        return new Pair<>(response.getFeed(), response.getHasMorePages());
    }

    @Override
    protected List<User> returnUsers(List<Status> items) {
        return items.stream().map(x -> x.user).collect(Collectors.toList());
    }
}
