package edu.byu.cs.tweeter.client.model.service.backgroundTasks.handlers;

import edu.byu.cs.tweeter.client.model.service.observers.FollowObserver;

public class FollowHandler extends SimpleNotificationHandler {
    public FollowHandler(FollowObserver observer) {
        super(observer);
    }

    @Override
    protected String getFailedMessagePrefix() {
        return "Failed to follow";
    }
}
