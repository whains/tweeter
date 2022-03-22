package edu.byu.cs.tweeter.client.model.service.backgroundTasks.handlers;

import edu.byu.cs.tweeter.client.model.service.observers.LogoutObserver;

public class LogoutHandler extends SimpleNotificationHandler {
    public LogoutHandler(LogoutObserver observer) {
        super(observer);
    }

    @Override
    protected String getFailedMessagePrefix() {
        return "Failed to logout";
    }
}
