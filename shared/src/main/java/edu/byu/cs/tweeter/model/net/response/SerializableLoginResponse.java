package edu.byu.cs.tweeter.model.net.response;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class SerializableLoginResponse {
    public User user;
    public AuthToken authToken;
    public boolean success;
    public boolean hasMorePages;
    public String errorMessage;
}
