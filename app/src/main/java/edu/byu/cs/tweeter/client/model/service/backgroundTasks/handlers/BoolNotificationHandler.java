package edu.byu.cs.tweeter.client.model.service.backgroundTasks.handlers;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.observers.BoolNotificationServiceObserver;

public abstract class BoolNotificationHandler<T extends BoolNotificationServiceObserver> extends BackgroundTaskHandler<T> {
    public BoolNotificationHandler(T observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(T observer, Bundle data) {
        observer.handleSuccess(getResult(data));
    }

    protected abstract boolean getResult(Bundle data);
}
