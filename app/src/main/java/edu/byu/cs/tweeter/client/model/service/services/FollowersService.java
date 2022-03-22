package edu.byu.cs.tweeter.client.model.service.services;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.GetFollowersTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTasks.handlers.GetFollowersHandler;
import edu.byu.cs.tweeter.client.model.service.observers.FollowersObserver;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;

public class FollowersService extends SourceService {

    public void loadMoreItems(FollowersRequest request, FollowersObserver followersObserver) {
        GetFollowersTask getFollowersTask = new GetFollowersTask(request, new GetFollowersHandler(followersObserver));

        executeSingleTask(getFollowersTask);
    }

}
