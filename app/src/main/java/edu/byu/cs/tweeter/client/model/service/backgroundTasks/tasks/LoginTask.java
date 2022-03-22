package edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks;

import android.os.Handler;

import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.AuthenticationTask;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;

public class LoginTask extends AuthenticationTask {
    public LoginTask(LoginRequest request, Handler messageHandler) {
        super(messageHandler, request, request.getUsername(), request.getPassword());
    }

    private static final String LOG_TAG = "LoginTask";
}
