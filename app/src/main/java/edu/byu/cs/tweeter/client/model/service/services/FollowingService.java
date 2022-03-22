package edu.byu.cs.tweeter.client.model.service.services;

import android.os.Looper;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.GetFollowingTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTasks.handlers.GetFollowingHandler;
import edu.byu.cs.tweeter.client.model.service.observers.FollowingObserver;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;

public class FollowingService extends SourceService {

    public void loadMoreItems(FollowingRequest request, FollowingObserver followingObserver) {
        GetFollowingTask getFollowingTask = new GetFollowingTask(request, new GetFollowingHandler(followingObserver));

        executeSingleTask(getFollowingTask);
    }

}
