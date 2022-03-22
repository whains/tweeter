package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;

import java.sql.Timestamp;
import java.util.UUID;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.GetUserResponse;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;

public class UserDAO implements UserDAOInterface {

    private static final String Username = "Username";
    private static final String FirstName = "FirstName";
    private static final String LastName = "LastName";
    private static final String ImageURL = "ImageURL";
    private static final String Password = "Password";

    AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withRegion("us-west-2")
            .build();

    DynamoDB dynamoDB = new DynamoDB(client);
    Table table = dynamoDB.getTable("Users");


    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {

        if (table.getItem(Username, registerRequest.getUsername()) != null) {
            return new RegisterResponse("A user with that username already exists.");
        }
        else {
            Item newUser = new Item()
                    .withPrimaryKey(Username, registerRequest.getUsername())
                    .withString(FirstName, registerRequest.getFirstName())
                    .withString(LastName, registerRequest.getLastName())
                    .withString(ImageURL, registerRequest.getImage())
                    .withString(Password, registerRequest.getPassword());
            table.putItem(newUser);
        }

        return new RegisterResponse();
    }


    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Item userItem = table.getItem(Username, loginRequest.getUsername());

        if (userItem == null) {
            return new LoginResponse("User not found.");
        }
        else if (!userItem.getString(Password).equals(loginRequest.getPassword())) {
            return new LoginResponse("Incorrect password.");
        }
        else {
            User user = new User(userItem.getString(FirstName), userItem.getString(LastName), userItem.getString(Username), userItem.getString(ImageURL));
            return new LoginResponse(user, new AuthToken());
        }
    }


    @Override
    public GetUserResponse getUser(GetUserRequest getUserRequest) {
        Item userItem = table.getItem(Username, getUserRequest.getAlias());

        if (userItem == null) {
            return new GetUserResponse("User does not exist.");
        }
        else {
            return new GetUserResponse(new User(userItem.getString(FirstName), userItem.getString(LastName), userItem.getString(Username), userItem.getString(ImageURL)));
        }
    }
}
