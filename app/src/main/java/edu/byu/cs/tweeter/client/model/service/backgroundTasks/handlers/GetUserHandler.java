package edu.byu.cs.tweeter.client.model.service.backgroundTasks.handlers;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.observers.GetUserObserver;
import edu.byu.cs.tweeter.model.domain.User;

public class GetUserHandler extends UserNotificationHandler {
    public GetUserHandler(GetUserObserver observer) {
        super(observer);
    }

    @Override
    protected String getFailedMessagePrefix() {
        return "Failed to get following";
    }

    @Override
    protected User getCurrUser(Bundle data) { return (User) data.getSerializable(GetUserTask.USER_KEY); }
}
