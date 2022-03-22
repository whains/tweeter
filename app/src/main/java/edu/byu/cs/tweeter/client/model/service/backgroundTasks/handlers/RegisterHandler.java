package edu.byu.cs.tweeter.client.model.service.backgroundTasks.handlers;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.RegisterTask;
import edu.byu.cs.tweeter.client.model.service.observers.MakeUserObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterHandler extends UserNotificationHandler {
    public RegisterHandler(MakeUserObserver observer) {
        super(observer);
    }

    @Override
    protected String getFailedMessagePrefix() {
        return "Failed to register";
    }

    @Override
    protected User getCurrUser(Bundle data) {
        User user = (User) data.getSerializable(RegisterTask.USER_KEY);
        AuthToken authToken = (AuthToken) data.getSerializable(RegisterTask.AUTH_TOKEN_KEY);

        Cache.getInstance().setCurrUser(user);
        Cache.getInstance().setCurrUserAuthToken(authToken);

        return user;
    }
}
