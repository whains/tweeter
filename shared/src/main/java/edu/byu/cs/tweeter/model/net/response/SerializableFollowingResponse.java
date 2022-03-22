package edu.byu.cs.tweeter.model.net.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;

public class SerializableFollowingResponse {
    public List<User> followees;
    public boolean success;
    public boolean hasMorePages;
    public String errorMessage;
}
