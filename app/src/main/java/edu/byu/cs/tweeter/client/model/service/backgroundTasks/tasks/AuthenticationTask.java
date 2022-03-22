package edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.util.Pair;

public abstract class AuthenticationTask extends BackgroundTask {

    public AuthenticationTask(Handler messageHandler, LoginRequest request, String username, String password) {
        super(messageHandler);
        this.request = request;
        this.username = username;
        this.password = password;
    }

    public static final String USER_KEY = "user";
    public static final String AUTH_TOKEN_KEY = "auth-token";
    static final String URL_PATH = "/login";

    private String username;
    private String password;

    private LoginRequest request;

    User user;
    AuthToken authToken;

    @Override
    protected void runTask() throws IOException, TweeterRemoteException {
        Pair<User, AuthToken> result = authenticate();

        user = result.getFirst();
        authToken = result.getSecond();

        BackgroundTaskUtils.loadImage(user);
    }

    @Override
    protected void loadMessageBundle(Bundle msgBundle) {
        msgBundle.putSerializable(USER_KEY, user);
        msgBundle.putSerializable(AUTH_TOKEN_KEY, authToken);
    }

    protected Pair<User, AuthToken> authenticate() throws IOException, TweeterRemoteException {
        LoginResponse response = getServerFacade().login(request, URL_PATH);

        return new Pair<>(response.getUser(), response.getAuthToken());
    }
}
