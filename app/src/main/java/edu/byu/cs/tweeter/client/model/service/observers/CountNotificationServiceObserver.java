package edu.byu.cs.tweeter.client.model.service.observers;

public interface CountNotificationServiceObserver extends OriginalServiceObserver {
    void handleSuccessFollower(int count);
    void handleSuccessFollowee(int count);
}
