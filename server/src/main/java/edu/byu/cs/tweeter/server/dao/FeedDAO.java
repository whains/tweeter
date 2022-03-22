package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;

public class FeedDAO implements FeedDAOInterface {

    private static final String FollowerUsername = "FollowerUsername";
    private static final String FolloweeUsername = "FolloweeUsername";

    private static final String Username = "Username";
    private static final String FirstName = "FirstName";
    private static final String LastName = "LastName";
    private static final String ImageURL = "ImageURL";
    private static final String Post = "Post";
    private static final String TimeOfCreation = "TimeOfCreation";
    private static final String Date = "Date";

    AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withRegion("us-west-2")
            .build();

    DynamoDB dynamoDB = new DynamoDB(client);

    Table feedTable = dynamoDB.getTable("Feed");
    Table storyTable = dynamoDB.getTable("Story");
    Table followTable = dynamoDB.getTable("Follows");


    @Override
    public FeedResponse getFeed(FeedRequest feedRequest) {
        clearFeedTable();
        createFeedTable(feedRequest);

        boolean hasMorePages;
        QueryRequest queryRequest;
        QueryResult queryResult;

        List<Status> feed = new ArrayList<>();

        Map<String, String> names = new HashMap<>();
        Map<String, AttributeValue> values = new HashMap<>();

        names.put("#username", FollowerUsername);
        values.put(":" + FollowerUsername, new AttributeValue().withS(feedRequest.getUser().getAlias()));

        queryRequest = new QueryRequest()
                .withTableName("Feed")
                .withKeyConditionExpression("#username = :" + FollowerUsername)
                .withExpressionAttributeNames(names)
                .withExpressionAttributeValues(values)
                .withScanIndexForward(false)
                .withLimit(feedRequest.getLimit());

        if (feedRequest.getLastStatus() != null) {
            Map<String, AttributeValue> startKey = Map.of(
                    FollowerUsername, new AttributeValue().withS(feedRequest.getUser().getAlias()),
                    TimeOfCreation, new AttributeValue().withS(feedRequest.getLastStatus().getSecondstime())
            );

            queryRequest = queryRequest.withExclusiveStartKey(startKey);
        }

        queryResult = client.query(queryRequest);
        List<Map<String, AttributeValue>> items = queryResult.getItems();

        if (items != null) {
            for (Map<String, AttributeValue> item : items) {
                String username = item.get(FolloweeUsername).getS();
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
                feed.add(new Status(post, user, date, timestamp, urls, mentions));
            }
        }

        Map<String, AttributeValue> lastKey = queryResult.getLastEvaluatedKey();
        if (lastKey != null) {
            hasMorePages = true;
        }
        else {
            hasMorePages = false;
        }

        return new FeedResponse(feed, hasMorePages);
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


    @Override
    public void clearFeedTable() {
        ItemCollection<ScanOutcome> items = feedTable.scan();

        Iterator<Item> iterator = items.iterator();
        Item item;

        while (iterator.hasNext()) {
            item = iterator.next();
            feedTable.deleteItem(FollowerUsername, item.get(FollowerUsername).toString(), TimeOfCreation, item.get(TimeOfCreation).toString());
        }
    }


    @Override
    public void createFeedTable(FeedRequest feedRequest) {
        QuerySpec spec = new QuerySpec()
                .withKeyConditionExpression("FollowerUsername = :user")
                .withValueMap(new ValueMap()
                        .withString(":user", feedRequest.getUser().getAlias()));

        ItemCollection<QueryOutcome> items = followTable.query(spec);

        Iterator<Item> iterator = items.iterator();
        Item item;

        while (iterator.hasNext()) {
            item = iterator.next();
            addToFeed(item);
        }
    }


    @Override
    public void addToFeed(Item i) {
        QuerySpec spec = new QuerySpec()
                .withKeyConditionExpression("Username = :user")
                .withValueMap(new ValueMap()
                        .withString(":user", i.get(FolloweeUsername).toString()));

        ItemCollection<QueryOutcome> items = storyTable.query(spec);

        Iterator<Item> iterator = items.iterator();
        Item item;
        Item newItem;

        while (iterator.hasNext()) {
            item = iterator.next();
            String username = item.get(Username).toString();
            String firstName = item.get(FirstName).toString();
            String lastName = item.get(LastName).toString();
            String imageURL = item.get(ImageURL).toString();
            String post = item.get(Post).toString();
            String timestamp = item.get(TimeOfCreation).toString();
            String date = item.get(Date).toString();
            newItem = new Item()
                    .withPrimaryKey(FollowerUsername, i.get(FollowerUsername).toString(), TimeOfCreation, timestamp)
                    .withString(Date, date)
                    .withString(FolloweeUsername, username)
                    .withString(FirstName, firstName)
                    .withString(LastName, lastName)
                    .withString(ImageURL, imageURL)
                    .withString(Post, post);
            feedTable.putItem(newItem);
        }
    }
}
