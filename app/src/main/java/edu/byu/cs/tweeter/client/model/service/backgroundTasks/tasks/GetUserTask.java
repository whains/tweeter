package edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.AuthorizedTask;
import edu.byu.cs.tweeter.client.util.ByteArrayUtils;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.response.GetUserResponse;

public class GetUserTask extends AuthorizedTask {
    public GetUserTask(GetUserRequest request, Handler messageHandler) {
        super(messageHandler, request.getAuthToken());
        this.request = request;
        this.alias = request.getAlias();
    }

    private static final String LOG_TAG = "GetUserTask";
    public static final String USER_KEY = "user";
    static final String URL_PATH = "/getuser";

    private GetUserRequest request;
    private String alias;

    @Override
    protected void runTask() {

    }

    private User getUser() throws IOException, TweeterRemoteException {
        GetUserResponse response = getServerFacade().getUser(request, URL_PATH);
        response.getUser().setImageBytes(ByteArrayUtils.bytesFromUrl(response.getUser().getImageUrl()));

        return response.getUser();
    }

    @Override
    protected void loadMessageBundle(Bundle msgBundle) throws IOException, TweeterRemoteException {
        msgBundle.putSerializable(USER_KEY, getUser());
    }
}
