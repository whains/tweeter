package edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks;

import android.os.Bundle;
import android.os.Handler;

import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.AuthorizedTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class CountTask extends AuthorizedTask {
    private User targetUser;

    public CountTask(Handler messageHandler, User targetUser, AuthToken authToken) {
        super(messageHandler, authToken);
        this.targetUser = targetUser;
    }
}
