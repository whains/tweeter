package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;

import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;

public interface StoryDAOInterface {
    PostStatusResponse postStatus(PostStatusRequest postStatusRequest);
    StoryResponse getStory(StoryRequest storyRequest);
    ArrayList<String> findUrls(String post);
    ArrayList<String> findMentions(String post);
}
