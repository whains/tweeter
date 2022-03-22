package edu.byu.cs.tweeter.client.model.service.backgroundTasks.handlers;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.backgroundTasks.handlers.BackgroundTaskHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.model.service.observers.CountNotificationServiceObserver;

public abstract class CountNotificationHandler<T extends CountNotificationServiceObserver> extends BackgroundTaskHandler<T> {
    public CountNotificationHandler(T observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(T observer, Bundle data) {
        observer.handleSuccessFollowee(data.getInt(GetFollowingCountTask.FOLLOWING_COUNT_KEY));
        observer.handleSuccessFollower(data.getInt(GetFollowersCountTask.FOLLOWERS_COUNT_KEY));
    }
}
