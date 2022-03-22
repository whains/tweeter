package edu.byu.cs.tweeter.client.model.service.backgroundTasks.handlers;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.observers.MsgNotificationServiceObserver;

public abstract class MsgNotificationHandler<T extends MsgNotificationServiceObserver> extends BackgroundTaskHandler<T> {
    public MsgNotificationHandler(T observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(T observer, Bundle data) { observer.handleSuccess(getMsg()); }

    protected abstract String getMsg();
}
