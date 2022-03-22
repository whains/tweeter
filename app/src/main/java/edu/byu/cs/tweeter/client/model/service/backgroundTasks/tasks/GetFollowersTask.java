package edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks;

import android.os.Handler;

import java.io.IOException;
import java.util.List;

import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.PagedTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.util.Pair;

public class GetFollowersTask extends PagedTask<User> {
    public GetFollowersTask(FollowersRequest request, Handler messageHandler) {
        super(messageHandler, request.getLimit(), request.getLastFollower(), request.getAuthToken());
        this.request = request;
        this.targetUser = targetUser;
    }

    private static final String LOG_TAG = "GetFollowersTask";
    static final String URL_PATH = "/getfollowers";

    private FollowersRequest request;
    private User targetUser;

    private List<User> followers;
    private boolean hasMorePages;

    @Override
    protected Pair<List<User>, Boolean> getItems() throws IOException, TweeterRemoteException {
        FollowersResponse response = getServerFacade().getFollowers(request, URL_PATH);
        return new Pair<>(response.getFollowers(), response.getHasMorePages());
    }

    @Override
    protected List<User> returnUsers(List<User> items) {
        return items;
    }

}
