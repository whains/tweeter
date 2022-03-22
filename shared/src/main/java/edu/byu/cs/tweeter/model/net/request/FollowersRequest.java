package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowersRequest {

    private User followee;
    private User lastFollower;
    private AuthToken authToken;
    private int limit;

    private FollowersRequest() {}

    public FollowersRequest(User followee, User lastFollower, AuthToken authToken, int limit) {
        this.followee = followee;
        this.lastFollower = lastFollower;
        this.authToken = authToken;
        this.limit = limit;
    }

    public User getFollowee() {
        return followee;
    }
    public void setFollowee(User followee) {
        this.followee = followee;
    }

    public User getLastFollower() {
        return lastFollower;
    }
    public void setLastFollower(User lastFollower) {
        this.lastFollower = lastFollower;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }
    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public String getFolloweeAlias() {
        return followee.getAlias();
    }

    public int getLimit() {
        return limit;
    }
    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getLastFollowerAlias() {
        return lastFollower.getAlias();
    }

}
