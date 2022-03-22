package edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.AuthorizedTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;

public class PostStatusTask extends AuthorizedTask {
    public PostStatusTask(PostStatusRequest request, Handler messageHandler) {
        super(messageHandler, request.getAuthToken());
        this.status = request.getStatus();
        this.request = request;
    }

    private static final String LOG_TAG = "PostStatusTask";
    static final String URL_PATH = "/poststatus";

    private PostStatusRequest request;
    private Status status;

    @Override
    protected void runTask() throws IOException, TweeterRemoteException {
        PostStatusResponse response = getServerFacade().postStatus(request, URL_PATH);
    }

    @Override
    protected void loadMessageBundle(Bundle msgBundle) { }
}
