package edu.byu.cs.tweeter.client.model.service.backgroundTasks.handlers;

import edu.byu.cs.tweeter.client.model.service.observers.UnfollowObserver;

public class UnfollowHandler extends SimpleNotificationHandler {
    public UnfollowHandler(UnfollowObserver observer) {
        super(observer);
    }

    @Override
    protected String getFailedMessagePrefix() {
        return "Failed to unfollow";
    }
}
