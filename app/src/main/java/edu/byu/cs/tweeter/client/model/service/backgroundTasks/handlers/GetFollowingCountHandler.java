package edu.byu.cs.tweeter.client.model.service.backgroundTasks.handlers;

import android.os.Bundle;

import java.net.MalformedURLException;

import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.model.service.observers.CountNotificationServiceObserver;
import edu.byu.cs.tweeter.client.model.service.observers.GetFollowCountObserver;

public class GetFollowingCountHandler <T extends CountNotificationServiceObserver> extends BackgroundTaskHandler<T> {
    public GetFollowingCountHandler(GetFollowCountObserver observer) {
        super((T) observer);
    }


    @Override
    protected void handleSuccessMessage(T observer, Bundle data) throws MalformedURLException {
        observer.handleSuccessFollowee(data.getInt(GetFollowingCountTask.FOLLOWING_COUNT_KEY));
    }

    @Override
    protected String getFailedMessagePrefix() {
        return "Failed to get following count";
    }
}
