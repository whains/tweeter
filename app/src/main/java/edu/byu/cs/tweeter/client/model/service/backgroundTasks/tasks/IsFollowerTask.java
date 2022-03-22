package edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;
import java.util.Random;

import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.AuthorizedTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;

public class IsFollowerTask extends AuthorizedTask {
    public IsFollowerTask(IsFollowerRequest request, Handler messageHandler) {
        super(messageHandler, request.getAuthToken());
        this.follower = request.getCurrUser();
        this.followee = request.getUser();
        this.request = request;
    }

    private static final String LOG_TAG = "IsFollowerTask";
    public static final String IS_FOLLOWER_KEY = "is-follower";

    static final String URL_PATH = "/isfollower";
    private User follower;
    private User followee;
    private IsFollowerRequest request;
    private IsFollowerResponse response;

    @Override
    protected void runTask() throws IOException, TweeterRemoteException {
        this.response = getServerFacade().isFollower(request, URL_PATH);
    }

    @Override
    protected void loadMessageBundle(Bundle msgBundle) {
        msgBundle.putBoolean(IS_FOLLOWER_KEY, response.isSuccess());
    }
}
