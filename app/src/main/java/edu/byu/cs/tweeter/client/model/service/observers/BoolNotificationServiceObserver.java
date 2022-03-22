package edu.byu.cs.tweeter.client.model.service.observers;

public interface BoolNotificationServiceObserver extends OriginalServiceObserver {
    void handleSuccess(boolean bool);
}
