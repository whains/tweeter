package edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks;

import android.os.Handler;

import java.io.IOException;
import java.util.List;

import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.PagedTask;
import edu.byu.cs.tweeter.client.model.service.services.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
import edu.byu.cs.tweeter.util.Pair;

public class GetFollowingTask extends PagedTask<User> {
    public GetFollowingTask(FollowingRequest request, Handler messageHandler) {
        super(messageHandler, request.getLimit(), request.getLastFollowee(), request.getAuthToken());
        this.request = request;
        this.targetUser = targetUser;
    }

    private static final String LOG_TAG = "GetFollowingTask";
    static final String URL_PATH = "/getfollowing";

    private FollowingRequest request;
    private User targetUser;

    protected Pair<List<User>, Boolean> getItems() throws IOException, TweeterRemoteException {
        FollowingResponse response = getServerFacade().getFollowees(request, URL_PATH);
        return new Pair<>(response.getFollowees(), response.getHasMorePages());
    }

    @Override
    protected List<User> returnUsers(List<User> items) {
        return items;
    }

}
