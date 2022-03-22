package edu.byu.cs.tweeter.client.model.service.backgroundTasks.handlers;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.IsFollowerTask;
import edu.byu.cs.tweeter.client.model.service.observers.IsFollowerObserver;

public class IsFollowerHandler extends BoolNotificationHandler {
    public IsFollowerHandler(IsFollowerObserver observer) {
        super(observer);
    }

    @Override
    protected String getFailedMessagePrefix() { return "Failed to determine following relationship"; }

    @Override
    protected boolean getResult(Bundle data) { return data.getBoolean(IsFollowerTask.IS_FOLLOWER_KEY); }
}
