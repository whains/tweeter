package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class UnfollowRequest {
    private AuthToken authToken;
    private User selectedUser;
    private User currentUser;

    private UnfollowRequest() {}

    public UnfollowRequest(User selectedUser, User currentUser, AuthToken authToken) {
        this.selectedUser = selectedUser;
        this.currentUser = currentUser;
        this.authToken = authToken;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public User getUser() {
        return selectedUser;
    }

    public void setUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public User getCurrUser() { return currentUser; }
}
