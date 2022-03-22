package edu.byu.cs.tweeter.model.net.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;

public class SerializableStoryResponse {
    public List<Status> story;
    public boolean success;
    public boolean hasMorePages;
    public String errorMessage;
}
