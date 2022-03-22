package edu.byu.cs.tweeter.client.model.service.observers;

import edu.byu.cs.tweeter.model.domain.User;

public interface UserNotificationServiceObserver extends OriginalServiceObserver {
    void handleSuccess(User user);
}
