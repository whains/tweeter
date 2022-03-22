package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowingRequest {

    private User follower;
    private User lastFollowee;
    private AuthToken authToken;
    private int limit;

    private FollowingRequest() {}

    public FollowingRequest(User follower, User lastFollowee, AuthToken authToken, int limit) {
        this.follower = follower;
        this.lastFollowee = lastFollowee;
        this.authToken = authToken;
        this.limit = limit;
    }

    public User getFollower() {
        return follower;
    }
    public void setFollower(User follower) {
        this.follower = follower;
    }

    public User getLastFollowee() {
        return lastFollowee;
    }
    public void setLastFollowee(User lastFollowee) {
        this.lastFollowee = lastFollowee;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }
    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public String getFollowerAlias() {
        return follower.getAlias();
    }

    public int getLimit() {
        return limit;
    }
    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getLastFolloweeAlias() {
        return lastFollowee.getAlias();
    }
}
