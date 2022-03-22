package edu.byu.cs.tweeter.client.model.service.services;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.GetFeedTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.GetStoryTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTasks.handlers.GetFeedHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTasks.handlers.GetStoryHandler;
import edu.byu.cs.tweeter.client.model.service.observers.StoryObserver;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;

public class StatusService extends SourceService {

    public void loadFeedItems(FeedRequest request, StoryObserver storyObserver) {
        GetFeedTask getFeedTask = new GetFeedTask(request, new GetFeedHandler(storyObserver));

        executeSingleTask(getFeedTask);
    }

    public void loadMoreItems(StoryRequest request, StoryObserver storyObserver) {
        GetStoryTask getStoryTask = new GetStoryTask(request, new GetStoryHandler(storyObserver));

        //TODO TEST TO SEE IF STORY OBSERVER HANDLES SUCCESSFULLY
        executeSingleTask(getStoryTask);
    }

}
