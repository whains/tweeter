package edu.byu.cs.tweeter.client.model.service.backgroundTasks.handlers;

import android.os.Bundle;

import java.net.MalformedURLException;

import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.model.service.observers.CountNotificationServiceObserver;
import edu.byu.cs.tweeter.client.model.service.observers.GetFollowCountObserver;
import edu.byu.cs.tweeter.client.model.service.observers.OriginalServiceObserver;

public class GetFollowersCountHandler<T extends CountNotificationServiceObserver> extends BackgroundTaskHandler<T> {
    public GetFollowersCountHandler(GetFollowCountObserver observer) {
        super((T) observer);
    }

    @Override
    protected void handleSuccessMessage(T observer, Bundle data) throws MalformedURLException {
        observer.handleSuccessFollower(data.getInt(GetFollowersCountTask.FOLLOWERS_COUNT_KEY));
    }

    @Override
    protected String getFailedMessagePrefix() {
        return "Failed to get followers count";
    }
}
