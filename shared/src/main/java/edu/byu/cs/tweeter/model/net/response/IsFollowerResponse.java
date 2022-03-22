package edu.byu.cs.tweeter.model.net.response;

public class IsFollowerResponse extends Response {

    public IsFollowerResponse(String message) {
        super(false, message);
    }

    public IsFollowerResponse(boolean isFollower) {
        super(isFollower, null);
    }
}
