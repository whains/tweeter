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
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.Select;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.net.response.GetFollowersCountResponse;

public class AuthTokenDAO implements AuthTokenDAOInterface {

    private static final String Token = "Token";
    private static final String TimeOfCreation = "TimeOfCreation";

    AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withRegion("us-west-2")
            .build();

    DynamoDB dynamoDB = new DynamoDB(client);
    Table table = dynamoDB.getTable("AuthToken");


    @Override
    public void addToken(AuthToken authToken) {
        Item newAuthToken = new Item()
                .withPrimaryKey(Token, authToken.getToken())
                .withString(TimeOfCreation, authToken.getDatetime());
        table.putItem(newAuthToken);
    }


    @Override
    public void deleteToken(AuthToken authToken) {
        table.deleteItem(Token, authToken.getToken());
    }


    @Override
    public void checkAuthTokens() {
        ItemCollection<ScanOutcome> items = table.scan();

        Iterator<Item> iterator = items.iterator();
        Item item;

        while (iterator.hasNext()) {
            item = iterator.next();

            if (new Timestamp(System.currentTimeMillis()).getTime() - Long.parseLong(item.get(TimeOfCreation).toString()) > 86400000) {
                table.deleteItem(Token, item.get(Token).toString());
            }
        }
    }
}
