package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;

public class StoryDAO implements StoryDAOInterface {

    private static final String Username = "Username";
    private static final String TimeOfCreation = "TimeOfCreation";
    private static final String Date = "Date";
    private static final String Post = "Post";
    private static final String FirstName = "FirstName";
    private static final String LastName = "LastName";
    private static final String ImageURL = "ImageURL";

    AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withRegion("us-west-2")
            .build();

    DynamoDB dynamoDB = new DynamoDB(client);
    Table table = dynamoDB.getTable("Story");


    @Override
    public PostStatusResponse postStatus(PostStatusRequest postStatusRequest) {
        Item item = new Item()
                .withPrimaryKey(Username, postStatusRequest.getStatus().getUser().getAlias(), TimeOfCreation, postStatusRequest.getStatus().getSecondstime())
                .withString(Date, postStatusRequest.getStatus().getDate())
                .withString(Post, postStatusRequest.getStatus().getPost())
                .withString(FirstName, postStatusRequest.getStatus().getUser().getFirstName())
                .withString(LastName, postStatusRequest.getStatus().getUser().getLastName())
                .withString(ImageURL, postStatusRequest.getStatus().getUser().getImageUrl());
        table.putItem(item);

        return new PostStatusResponse();
    }


    @Override
    public StoryResponse getStory(StoryRequest storyRequest) {

        boolean hasMorePages;
        QueryRequest queryRequest;
        QueryResult queryResult;

        List<Status> story = new ArrayList<>();

        Map<String, String> names = new HashMap<>();
        Map<String, AttributeValue> values = new HashMap<>();

        names.put("#username", Username);
        values.put(":" + Username, new AttributeValue().withS(storyRequest.getUser().getAlias()));

        queryRequest = new QueryRequest()
                .withTableName("Story")
                .withKeyConditionExpression("#username = :" + Username)
                .withExpressionAttributeNames(names)
                .withExpressionAttributeValues(values)
                .withScanIndexForward(false)
                .withLimit(storyRequest.getLimit());

        if (storyRequest.getLastStatus() != null) {
            Map<String, AttributeValue> startKey = Map.of(
                    Username, new AttributeValue().withS(storyRequest.getUser().getAlias()),
                    TimeOfCreation, new AttributeValue().withS(storyRequest.getLastStatus().getSecondstime())
            );

            queryRequest = queryRequest.withExclusiveStartKey(startKey);
        }

        queryResult = client.query(queryRequest);
        List<Map<String, AttributeValue>> items = queryResult.getItems();

        if (items != null) {
            for (Map<String, AttributeValue> item : items) {
                String username = item.get(Username).getS();
                String firstName = item.get(FirstName).getS();
                String lastName = item.get(LastName).getS();
                String imageURL = item.get(ImageURL).getS();
                User user = new User(firstName, lastName, username, imageURL);

                String post = item.get(Post).getS();

                ArrayList<String> mentions;
                mentions = findMentions(post);

                ArrayList<String> urls;
                urls = findUrls(post);

                String date = item.get(Date).getS();
                String timestamp = item.get(TimeOfCreation).getS();
                story.add(new Status(post, user, date, timestamp, urls, mentions));
            }
        }

        Map<String, AttributeValue> lastKey = queryResult.getLastEvaluatedKey();
        if (lastKey != null) {
            hasMorePages = true;
        }
        else {
            hasMorePages = false;
        }

        return new StoryResponse(story, hasMorePages);
    }


    @Override
    public ArrayList<String> findUrls(String post) {
        String[] words = post.split(" ");
        ArrayList<String> urls = new ArrayList<>();
        for (String word : words) {
            if (word.startsWith("http")) {
                urls.add(word);
            }
        }
        return urls;
    }


    @Override
    public ArrayList<String> findMentions(String post) {
        String[] words = post.split(" ");
        ArrayList<String> mentions = new ArrayList<>();
        for (String word : words) {
            if (word.charAt(0) == '@') {
                mentions.add(word);
            }
        }
        return mentions;
    }
}
