package edu.byu.cs.tweeter.client.model.service.observers;

public interface MsgNotificationServiceObserver extends OriginalServiceObserver {
    void handleSuccess(String message);
}
