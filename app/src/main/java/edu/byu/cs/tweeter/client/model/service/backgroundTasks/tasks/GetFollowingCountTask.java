package edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.CountTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.GetFollowCountRequest;
import edu.byu.cs.tweeter.model.net.response.GetFollowingCountResponse;

public class GetFollowingCountTask extends CountTask {
    public GetFollowingCountTask(GetFollowCountRequest request, Handler messageHandler) {
        super(messageHandler, request.getUser(), request.getAuthToken());
        this.request = request;
    }

    public static final String FOLLOWING_COUNT_KEY = "following-count";
    static final String URL_PATH = "/getfollowingcount";
    private static final String LOG_TAG = "LogoutTask";
    private GetFollowCountRequest request;
    public int followingCount;

    @Override
    protected void runTask() throws IOException, TweeterRemoteException {
        followingCount = getServerFacade().getFollowingCount(request, URL_PATH);
    }

    @Override
    protected void loadMessageBundle(Bundle msgBundle) {
        msgBundle.putInt(FOLLOWING_COUNT_KEY, this.followingCount);
    }

}
