package edu.byu.cs.tweeter.model.net.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;

public class SerializableFollowersResponse {
    public List<User> followers;
    public boolean success;
    public boolean hasMorePages;
    public String errorMessage;
}
