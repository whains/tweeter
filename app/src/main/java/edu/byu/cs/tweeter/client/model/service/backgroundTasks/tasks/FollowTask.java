package edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.AuthorizedTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;

public class FollowTask extends AuthorizedTask {
    public FollowTask(IsFollowerRequest request, Handler messageHandler) {
        super(messageHandler, request.getAuthToken());
        this.request = request;
        this.followee = request.getUser();
    }

    private static final String LOG_TAG = "FollowTask";
    private final String URL_PATH = "/follow";

    private IsFollowerRequest request;
    private User followee;

    @Override
    protected void runTask() throws IOException, TweeterRemoteException {
        FollowResponse response = getServerFacade().follow(request, URL_PATH);
    }

    @Override
    protected void loadMessageBundle(Bundle msgBundle) { }
}
