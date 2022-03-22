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
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.util.Pair;

public class GetStoryTask extends PagedTask<Status> {
    public GetStoryTask(StoryRequest request, Handler messageHandler) {
        super(messageHandler, request.getLimit(), request.getLastStatus(), request.getAuthToken());
        this.request = request;
        this.targetUser = targetUser;
    }

    private static final String LOG_TAG = "GetStoryTask";
    static final String URL_PATH = "/getstory";

    public static final String STATUSES_KEY = "statuses";
    public static final String MORE_PAGES_KEY = "more-pages";

    private StoryRequest request;
    private User targetUser;

    @Override
    protected Pair<List<Status>, Boolean> getItems() throws IOException, TweeterRemoteException {
        StoryResponse response = getServerFacade().getStory(request, URL_PATH);
        return new Pair<>(response.getStory(), response.getHasMorePages());
    }

    @Override
    protected List<User> returnUsers(List<Status> items) {
        return items.stream().map(x -> x.user).collect(Collectors.toList());
    }
}
