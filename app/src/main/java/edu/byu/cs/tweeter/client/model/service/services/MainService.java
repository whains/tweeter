package edu.byu.cs.tweeter.client.model.service.services;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.FollowTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.IsFollowerTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.LogoutTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.PostStatusTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.UnfollowTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTasks.handlers.FollowHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTasks.handlers.GetFollowersCountHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTasks.handlers.GetFollowingCountHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTasks.handlers.IsFollowerHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTasks.handlers.LogoutHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTasks.handlers.PostStatusHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTasks.handlers.UnfollowHandler;
import edu.byu.cs.tweeter.client.model.service.observers.FollowObserver;
import edu.byu.cs.tweeter.client.model.service.observers.GetFollowCountObserver;
import edu.byu.cs.tweeter.client.model.service.observers.IsFollowerObserver;
import edu.byu.cs.tweeter.client.model.service.observers.LogoutObserver;
import edu.byu.cs.tweeter.client.model.service.observers.PostStatusObserver;
import edu.byu.cs.tweeter.client.model.service.observers.UnfollowObserver;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.GetFollowCountRequest;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.request.UnfollowRequest;

public class MainService extends SourceService {

    public void follow(IsFollowerRequest request, FollowObserver followObserver) {
        FollowTask followTask = new FollowTask(request, new FollowHandler(followObserver));

        executeSingleTask(followTask);
    }

    public void unfollow(IsFollowerRequest request, UnfollowObserver unfollowObserver) {
        UnfollowTask unfollowTask = new UnfollowTask(request, new UnfollowHandler(unfollowObserver));

        executeSingleTask(unfollowTask);
    }

    public void isFollower(IsFollowerRequest request, IsFollowerObserver mainObserver) {
        IsFollowerTask isFollowerTask = new IsFollowerTask(request, new IsFollowerHandler(mainObserver));

        executeSingleTask(isFollowerTask);
    }

    public void postStatus(PostStatusRequest request, PostStatusObserver postStatusObserver) {
        PostStatusTask statusTask = new PostStatusTask(request, new PostStatusHandler(postStatusObserver));

        executeSingleTask(statusTask);
    }

    public void getFollowCount(GetFollowCountRequest request, GetFollowCountObserver getFollowCountObserver) {
        GetFollowersCountTask followersCountTask = new GetFollowersCountTask(request, new GetFollowersCountHandler(getFollowCountObserver));

        GetFollowingCountTask followingCountTask = new GetFollowingCountTask(request, new GetFollowingCountHandler(getFollowCountObserver));

        executeMultiTask(followersCountTask, followingCountTask);
    }

    public void logout(LogoutRequest request, LogoutObserver mainObserver) {
        LogoutTask logoutTask = new LogoutTask(request, new LogoutHandler(mainObserver));

        executeSingleTask(logoutTask);
    }
}
