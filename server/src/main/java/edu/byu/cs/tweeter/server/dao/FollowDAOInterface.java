package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.request.GetFollowCountRequest;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
import edu.byu.cs.tweeter.model.net.response.GetFollowersCountResponse;
import edu.byu.cs.tweeter.model.net.response.GetFollowingCountResponse;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;

public interface FollowDAOInterface {
    FollowingResponse getFollowing(FollowingRequest followingRequest);
    FollowersResponse getFollowers(FollowersRequest followersRequest);
    IsFollowerResponse follow(IsFollowerRequest followRequest);
    IsFollowerResponse unfollow(IsFollowerRequest unfollowRequest);
    IsFollowerResponse isFollower(IsFollowerRequest isFollowerRequest);
    GetFollowingCountResponse getFollowingCount(GetFollowCountRequest getFollowCountRequest);
    GetFollowersCountResponse getFollowersCount(GetFollowCountRequest getFollowCountRequest);
}
