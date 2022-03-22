package edu.byu.cs.tweeter.client.model.service.backgroundTasks.handlers;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.GetFollowersTask;
import edu.byu.cs.tweeter.client.model.service.observers.FollowersObserver;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowersHandler extends PagedNotificationHandler {
    public GetFollowersHandler(FollowersObserver observer) {
        super(observer);
    }

    @Override
    protected String getFailedMessagePrefix() {
        return "Failed to get followers";
    }

    @Override
    protected List getItems(Bundle data) { return (List<User>) data.getSerializable(GetFollowersTask.ITEMS_KEY); }

    @Override
    protected boolean getPages(Bundle data) { return data.getBoolean(GetFollowersTask.MORE_PAGES_KEY); }
}
