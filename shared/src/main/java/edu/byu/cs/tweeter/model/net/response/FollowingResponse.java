package edu.byu.cs.tweeter.model.net.response;

import java.util.List;
import java.util.Objects;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;

public class FollowingResponse extends PagedResponse {

    private List<User> followees;

    public FollowingResponse(String message) {
        super(false, message, false);
    }

    public FollowingResponse(List<User> followees, boolean hasMorePages) {
        super(true, hasMorePages);
        this.followees = followees;
    }

    public List<User> getFollowees() {
        return followees;
    }

    public void setFollowees(List<User> followees) {
        this.followees = followees;
    }

    @Override
    public boolean equals(Object param) {
        if (this == param) {
            return true;
        }

        if (param == null || getClass() != param.getClass()) {
            return false;
        }

        FollowingResponse that = (FollowingResponse) param;

        return (Objects.equals(followees, that.followees) &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                this.isSuccess() == that.isSuccess());
    }

    @Override
    public int hashCode() {
        return Objects.hash(followees);
    }
}
