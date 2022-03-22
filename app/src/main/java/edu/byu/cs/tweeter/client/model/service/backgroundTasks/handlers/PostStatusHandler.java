package edu.byu.cs.tweeter.client.model.service.backgroundTasks.handlers;

import edu.byu.cs.tweeter.client.model.service.observers.PostStatusObserver;

public class PostStatusHandler extends MsgNotificationHandler {
    public PostStatusHandler(PostStatusObserver observer) {
        super(observer);
    }

    @Override
    protected String getFailedMessagePrefix() {
        return "Failed to post status";
    }

    @Override
    protected String getMsg() {
        return "Successfully Posted!";
    }
}
