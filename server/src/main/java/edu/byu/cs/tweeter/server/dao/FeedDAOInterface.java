package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.Item;

import java.util.ArrayList;

import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;

public interface FeedDAOInterface {
    FeedResponse getFeed(FeedRequest feedRequest);
    ArrayList<String> findUrls(String post);
    ArrayList<String> findMentions(String post);
    void clearFeedTable();
    void createFeedTable(FeedRequest feedRequest);
    void addToFeed(Item item);
}
