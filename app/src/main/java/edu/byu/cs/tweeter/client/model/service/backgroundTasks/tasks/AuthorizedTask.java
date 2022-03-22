package edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks;

import android.os.Handler;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public abstract class AuthorizedTask extends BackgroundTask {
    protected AuthToken authToken;

    public AuthorizedTask(Handler messageHandler, AuthToken authToken) {
        super(messageHandler);
        this.authToken = authToken;
    }
}
