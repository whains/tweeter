package edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.AuthorizedTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.response.LogoutResponse;

public class LogoutTask extends AuthorizedTask {
    public LogoutTask(LogoutRequest request, Handler messageHandler) {
        super(messageHandler, request.getAuthToken());
        this.request = request;
    }

    private LogoutRequest request;
    private static final String LOG_TAG = "LogoutTask";
    static final String URL_PATH = "/logout";

    @Override
    protected void runTask() throws IOException, TweeterRemoteException {
        LogoutResponse response = getServerFacade().logout(request, URL_PATH);
    }

    @Override
    protected void loadMessageBundle(Bundle msgBundle) { }
}
