package edu.byu.cs.tweeter.client.model.service.backgroundTasks.handlers;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.GetFeedTask;
import edu.byu.cs.tweeter.client.model.service.observers.StoryObserver;
import edu.byu.cs.tweeter.model.domain.Status;

public class GetFeedHandler extends PagedNotificationHandler {
    public GetFeedHandler(StoryObserver observer) { super(observer); }

    @Override
    protected String getFailedMessagePrefix() {
        return "Failed to get feed";
    }

    @Override
    protected List getItems(Bundle data) { return (List<Status>) data.getSerializable(GetFeedTask.ITEMS_KEY); }

    @Override
    protected boolean getPages(Bundle data) {
        return data.getBoolean(GetFeedTask.MORE_PAGES_KEY);
    }
}
