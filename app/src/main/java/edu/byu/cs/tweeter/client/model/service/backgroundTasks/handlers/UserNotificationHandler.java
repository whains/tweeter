package edu.byu.cs.tweeter.client.model.service.backgroundTasks.handlers;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.backgroundTasks.handlers.BackgroundTaskHandler;
import edu.byu.cs.tweeter.client.model.service.observers.UserNotificationServiceObserver;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class UserNotificationHandler<T extends UserNotificationServiceObserver> extends BackgroundTaskHandler<T> {
    public UserNotificationHandler(T observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(T observer, Bundle data) { observer.handleSuccess(getCurrUser(data)); }

    protected abstract User getCurrUser(Bundle data);
}
