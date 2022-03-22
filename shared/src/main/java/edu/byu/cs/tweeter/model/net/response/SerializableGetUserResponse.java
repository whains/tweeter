package edu.byu.cs.tweeter.model.net.response;

import edu.byu.cs.tweeter.model.domain.User;

public class SerializableGetUserResponse {
    public User user;
    public boolean success;
    public String errorMessage;
}
