package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.Select;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.request.GetFollowCountRequest;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
import edu.byu.cs.tweeter.model.net.response.GetFollowersCountResponse;
import edu.byu.cs.tweeter.model.net.response.GetFollowingCountResponse;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;

public class FollowDAO implements FollowDAOInterface{

    private static final String FollowerUsername = "FollowerUsername";
    private static final String FollowerImageURL = "FollowerImageURL";
    private static final String FollowerFirstName = "FollowerFirstName";
    private static final String FollowerLastName = "FollowerLastName";

    private static final String FolloweeUsername = "FolloweeUsername";
    private static final String FolloweeImageURL = "FolloweeImageURL";
    private static final String FolloweeFirstName = "FolloweeFirstName";
    private static final String FolloweeLastName = "FolloweeLastName";

    AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withRegion("us-west-2")
            .build();

    DynamoDB dynamoDB = new DynamoDB(client);

    Table table = dynamoDB.getTable("Follows");


    @Override
    public FollowingResponse getFollowing(FollowingRequest followingRequest) {

        boolean hasMorePages;
        QueryRequest queryRequest;
        QueryResult queryResult;

        List<User> following = new ArrayList<>();

        Map<String, String> names = new HashMap<>();
        Map<String, AttributeValue> values = new HashMap<>();

        names.put("#follower", FollowerUsername);
        values.put(":" + FollowerUsername, new AttributeValue().withS(followingRequest.getFollower().getAlias()));

        queryRequest = new QueryRequest()
                .withTableName("Follows")
                .withKeyConditionExpression("#follower = :" + FollowerUsername)
                .withExpressionAttributeNames(names)
                .withExpressionAttributeValues(values)
                .withLimit(followingRequest.getLimit());

        if (followingRequest.getLastFollowee() != null) {
            Map<String, AttributeValue> startKey = Map.of(
                FollowerUsername, new AttributeValue().withS(followingRequest.getFollower().getAlias()),
                FolloweeUsername, new AttributeValue().withS(followingRequest.getLastFollowee().getAlias())
            );

            queryRequest = queryRequest.withExclusiveStartKey(startKey);
        }

        queryResult = client.query(queryRequest);
        List<Map<String, AttributeValue>> users = queryResult.getItems();

        if (users != null) {
            for (Map<String, AttributeValue> user : users) {
                String username = user.get(FolloweeUsername).getS();
                String firstName = user.get(FolloweeFirstName).getS();
                String lastName = user.get(FolloweeLastName).getS();
                String imageURL = user.get(FolloweeImageURL).getS();

                following.add(new User(firstName, lastName, username, imageURL));
            }
        }

        Map<String, AttributeValue> lastKey = queryResult.getLastEvaluatedKey();
        hasMorePages = lastKey != null;

        return new FollowingResponse(following, hasMorePages);
    }


    @Override
    public FollowersResponse getFollowers(FollowersRequest followersRequest) {

        boolean hasMorePages;
        QueryRequest queryRequest;
        QueryResult queryResult;

        List<User> followers = new ArrayList<>();

        Map<String, String> names = new HashMap<>();
        Map<String, AttributeValue> values = new HashMap<>();

        names.put("#followee", FolloweeUsername);
        values.put(":" + FolloweeUsername, new AttributeValue().withS(followersRequest.getFollowee().getAlias()));

        queryRequest = new QueryRequest()
                .withTableName("Follows")
                .withIndexName("Follows-Index")
                .withKeyConditionExpression("#followee = :" + FolloweeUsername)
                .withExpressionAttributeNames(names)
                .withExpressionAttributeValues(values)
                .withLimit(followersRequest.getLimit());

        if (followersRequest.getLastFollower() != null) {
            Map<String, AttributeValue> startKey = Map.of(
                    FolloweeUsername, new AttributeValue().withS(followersRequest.getFollowee().getAlias()),
                    FollowerUsername, new AttributeValue().withS(followersRequest.getLastFollower().getAlias())
            );

            queryRequest = queryRequest.withExclusiveStartKey(startKey);
        }

        queryResult = client.query(queryRequest);
        List<Map<String, AttributeValue>> users = queryResult.getItems();

        if (users != null) {
            for (Map<String, AttributeValue> user : users) {
                String username = user.get(FollowerUsername).getS();
                String firstName = user.get(FollowerFirstName).getS();
                String lastName = user.get(FollowerLastName).getS();
                String imageURL = user.get(FollowerImageURL).getS();

                followers.add(new User(firstName, lastName, username, imageURL));
            }
        }

        Map<String, AttributeValue> lastKey = queryResult.getLastEvaluatedKey();
        if (lastKey != null) {
            hasMorePages = true;
        }
        else {
            hasMorePages = false;
        }

        return new FollowersResponse(followers, hasMorePages);
    }


    @Override
    public IsFollowerResponse follow(IsFollowerRequest followRequest) {
        Item item = new Item()
                .withPrimaryKey(FollowerUsername, followRequest.getCurrUser().getAlias(), FolloweeUsername, followRequest.getUser().getAlias())
                .withString(FollowerFirstName, followRequest.getCurrUser().getFirstName())
                .withString(FollowerLastName, followRequest.getCurrUser().getLastName())
                .withString(FollowerImageURL, followRequest.getCurrUser().getImageUrl())
                .withString(FolloweeFirstName, followRequest.getUser().getFirstName())
                .withString(FolloweeLastName, followRequest.getUser().getLastName())
                .withString(FolloweeImageURL, followRequest.getUser().getImageUrl());
        table.putItem(item);

        return new IsFollowerResponse(true);
    }


    @Override
    public IsFollowerResponse unfollow(IsFollowerRequest unfollowRequest) {
        table.deleteItem(FollowerUsername, unfollowRequest.getCurrUser().getAlias(), FolloweeUsername, unfollowRequest.getUser().getAlias());
        return new IsFollowerResponse(true);
    }


    @Override
    public IsFollowerResponse isFollower(IsFollowerRequest isFollowerRequest) {
        if (table.getItem(FollowerUsername, isFollowerRequest.getCurrUser().getAlias(), FolloweeUsername, isFollowerRequest.getUser().getAlias()) != null) {
            return new IsFollowerResponse(true);
        }
        else {
            return new IsFollowerResponse(false);
        }
    }


    @Override
    public GetFollowingCountResponse getFollowingCount(GetFollowCountRequest getFollowCountRequest) {

        Condition weekCondition = new Condition()
                .withComparisonOperator(ComparisonOperator.EQ)
                .withAttributeValueList(new AttributeValue().withS(getFollowCountRequest.getUser().getAlias()));

        Map<String, Condition> keyConditions = new HashMap<>();
        keyConditions.put(FollowerUsername, weekCondition);

        QueryRequest request = new QueryRequest("Follows");
        request.setSelect(Select.COUNT);
        request.setKeyConditions(keyConditions);

        QueryResult result = client.query(request);
        Integer count = result.getCount();

        return new GetFollowingCountResponse(count);
    }


    @Override
    public GetFollowersCountResponse getFollowersCount(GetFollowCountRequest getFollowCountRequest) {

        Condition weekCondition = new Condition()
                .withComparisonOperator(ComparisonOperator.EQ)
                .withAttributeValueList(new AttributeValue().withS(getFollowCountRequest.getUser().getAlias()));

        Map<String, Condition> keyConditions = new HashMap<>();
        keyConditions.put(FolloweeUsername, weekCondition);

        QueryRequest request = new QueryRequest("Follows");
        request.setIndexName("Follows-Index");
        request.setSelect(Select.COUNT);
        request.setKeyConditions(keyConditions);

        QueryResult result = client.query(request);
        Integer count = result.getCount();

        return new GetFollowersCountResponse(count);
    }
}
