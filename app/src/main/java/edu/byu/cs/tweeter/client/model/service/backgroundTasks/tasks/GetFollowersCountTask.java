package edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.GetFollowCountRequest;
import edu.byu.cs.tweeter.model.net.response.GetFollowersCountResponse;

public class GetFollowersCountTask extends CountTask {
    public GetFollowersCountTask(GetFollowCountRequest request, Handler messageHandler) {
        super(messageHandler, request.getUser(), request.getAuthToken());
        this.request = request;
    }

    public static final String FOLLOWERS_COUNT_KEY = "followers-count";
    static final String URL_PATH = "/getfollowerscount";
    private static final String LOG_TAG = "LogoutTask";
    private GetFollowCountRequest request;
    public int followerCount;

    @Override
    protected void runTask() throws IOException, TweeterRemoteException {
        followerCount = getServerFacade().getFollowersCount(request, URL_PATH);
    }

    @Override
    protected void loadMessageBundle(Bundle msgBundle) {
        msgBundle.putInt(FOLLOWERS_COUNT_KEY, this.followerCount);
    }
}
