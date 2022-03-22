package edu.byu.cs.tweeter.client.model.service.backgroundTasks.handlers;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.GetFollowingTask;
import edu.byu.cs.tweeter.client.model.service.observers.FollowingObserver;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowingHandler extends PagedNotificationHandler {
    public GetFollowingHandler(FollowingObserver observer) {
        super(observer);
    }

    @Override
    protected String getFailedMessagePrefix() {
        return "Failed to get following";
    }

    @Override
    protected List getItems(Bundle data) { return (List<User>) data.getSerializable(GetFollowingTask.ITEMS_KEY); }

    @Override
    protected boolean getPages(Bundle data) { return data.getBoolean(GetFollowingTask.MORE_PAGES_KEY); }
}
