package edu.byu.cs.tweeter.client.model.net;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.request.GetFollowCountRequest;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
import edu.byu.cs.tweeter.model.net.response.GetUserResponse;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.model.net.response.LogoutResponse;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;
import edu.byu.cs.tweeter.model.net.response.SerializableFeedResponse;
import edu.byu.cs.tweeter.model.net.response.SerializableFollowersResponse;
import edu.byu.cs.tweeter.model.net.response.SerializableFollowingResponse;
import edu.byu.cs.tweeter.model.net.response.SerializableGetFollowersCountResponse;
import edu.byu.cs.tweeter.model.net.response.SerializableGetFollowingCountResponse;
import edu.byu.cs.tweeter.model.net.response.SerializableGetUserResponse;
import edu.byu.cs.tweeter.model.net.response.SerializableIsFollowerResponse;
import edu.byu.cs.tweeter.model.net.response.SerializableLoginResponse;
import edu.byu.cs.tweeter.model.net.response.SerializableLogoutResponse;
import edu.byu.cs.tweeter.model.net.response.SerializablePostStatusResponse;
import edu.byu.cs.tweeter.model.net.response.SerializableRegisterResponse;
import edu.byu.cs.tweeter.model.net.response.SerializableStoryResponse;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.model.net.response.UnfollowResponse;

public class ServerFacade {

    private static final String SERVER_URL = "https://lad35qc4fe.execute-api.us-west-2.amazonaws.com/dev";

    public ClientCommunicator getClientCommunicator() {
        return clientCommunicator;
    }

    public void setClientCommunicator(ClientCommunicator clientCommunicator) {
        this.clientCommunicator = clientCommunicator;
    }

    private ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);

    public LoginResponse login(LoginRequest request, String urlPath) throws IOException, TweeterRemoteException {
        SerializableLoginResponse serializableResponse = clientCommunicator.doPost(urlPath, request, null, SerializableLoginResponse.class);

        if(serializableResponse.success) {
            LoginResponse response = new LoginResponse(serializableResponse.user, serializableResponse.authToken);
            return response;
        } else {
            throw new RuntimeException(serializableResponse.errorMessage);
        }
    }

    public RegisterResponse register(RegisterRequest request, String urlPath) throws IOException, TweeterRemoteException {

        SerializableRegisterResponse serializableResponse = clientCommunicator.doPost(urlPath, request, null, SerializableRegisterResponse.class);

        if(serializableResponse.success) {
            RegisterResponse response = new RegisterResponse();
            return response;
        } else {
            throw new RuntimeException(serializableResponse.errorMessage);
        }
    }

    public LogoutResponse logout(LogoutRequest request, String urlPath) throws IOException, TweeterRemoteException {
        SerializableLogoutResponse serializableResponse = clientCommunicator.doPost(urlPath, request, null, SerializableLogoutResponse.class);

        if(serializableResponse.success) {
            LogoutResponse response = new LogoutResponse();
            return response;
        } else {
            throw new RuntimeException(serializableResponse.errorMessage);
        }
    }

    public FollowingResponse getFollowees(FollowingRequest request, String urlPath)
            throws IOException, TweeterRemoteException {
        SerializableFollowingResponse serializableResponse = clientCommunicator.doPost(urlPath, request, null, SerializableFollowingResponse.class);

        if(serializableResponse.success) {
            FollowingResponse response = new FollowingResponse(serializableResponse.followees, serializableResponse.hasMorePages);
            return response;
        } else {
            throw new RuntimeException(serializableResponse.errorMessage);
        }
    }

    public FollowersResponse getFollowers(FollowersRequest request, String urlPath)
            throws IOException, TweeterRemoteException {

        SerializableFollowersResponse serializableResponse = clientCommunicator.doPost(urlPath, request, null, SerializableFollowersResponse.class);

        if(serializableResponse.success) {
            FollowersResponse response = new FollowersResponse(serializableResponse.followers, serializableResponse.hasMorePages);
            return response;
        } else {
            throw new RuntimeException(serializableResponse.errorMessage);
        }
    }

    public FeedResponse getFeed(FeedRequest request, String urlPath) throws IOException, TweeterRemoteException {
        SerializableFeedResponse serializableResponse = clientCommunicator.doPost(urlPath, request, null, SerializableFeedResponse.class);

        if(serializableResponse.success) {
            FeedResponse response = new FeedResponse(serializableResponse.feed, serializableResponse.hasMorePages);
            return response;
        } else {
            throw new RuntimeException(serializableResponse.errorMessage);
        }
    }

    public StoryResponse getStory(StoryRequest request, String urlPath) throws IOException, TweeterRemoteException {
        SerializableStoryResponse serializableResponse = clientCommunicator.doPost(urlPath, request, null, SerializableStoryResponse.class);

        if(serializableResponse.success) {
            StoryResponse response = new StoryResponse(serializableResponse.story, serializableResponse.hasMorePages);
            return response;
        } else {
            throw new RuntimeException(serializableResponse.errorMessage);
        }
    }

    public GetUserResponse getUser(GetUserRequest request, String urlPath) throws IOException, TweeterRemoteException {
        SerializableGetUserResponse serializableResponse = clientCommunicator.doPost(urlPath, request, null, SerializableGetUserResponse.class);

        if(serializableResponse.success) {
            GetUserResponse response = new GetUserResponse(serializableResponse.user);
            return response;
        } else {
            throw new RuntimeException(serializableResponse.errorMessage);
        }
    }

    public PostStatusResponse postStatus(PostStatusRequest request, String urlPath) throws IOException, TweeterRemoteException {
        SerializablePostStatusResponse serializableResponse = clientCommunicator.doPost(urlPath, request, null, SerializablePostStatusResponse.class);

        if(serializableResponse.success) {
            PostStatusResponse response = new PostStatusResponse();
            return response;
        } else {
            throw new RuntimeException(serializableResponse.errorMessage);
        }
    }

    public FollowResponse follow(IsFollowerRequest request, String urlPath) throws IOException, TweeterRemoteException {
        SerializableIsFollowerResponse serializableResponse = clientCommunicator.doPost(urlPath, request, null, SerializableIsFollowerResponse.class);

        if(serializableResponse.success) {
            FollowResponse response = new FollowResponse();
            return response;
        } else {
            throw new RuntimeException(serializableResponse.errorMessage);
        }
    }

    public UnfollowResponse unfollow(IsFollowerRequest request, String urlPath) throws IOException, TweeterRemoteException {
        SerializableIsFollowerResponse serializableResponse = clientCommunicator.doPost(urlPath, request, null, SerializableIsFollowerResponse.class);

        if(serializableResponse.success) {
            UnfollowResponse response = new UnfollowResponse();
            return response;
        } else {
            throw new RuntimeException(serializableResponse.errorMessage);
        }
    }

    public IsFollowerResponse isFollower(IsFollowerRequest request, String urlPath) throws IOException, TweeterRemoteException {
        SerializableIsFollowerResponse serializableResponse = clientCommunicator.doPost(urlPath, request, null, SerializableIsFollowerResponse.class);

        IsFollowerResponse response = new IsFollowerResponse(serializableResponse.success);
        return response;
    }

    public int getFollowersCount(GetFollowCountRequest request, String urlPath) throws IOException, TweeterRemoteException {
        SerializableGetFollowersCountResponse serializableResponse = clientCommunicator.doPost(urlPath, request, null, SerializableGetFollowersCountResponse.class);

        return serializableResponse.followersCount;
    }

    public int getFollowingCount(GetFollowCountRequest request, String urlPath) throws IOException, TweeterRemoteException {
        SerializableGetFollowingCountResponse serializableResponse = clientCommunicator.doPost(urlPath, request, null, SerializableGetFollowingCountResponse.class);

        return serializableResponse.followingCount;
    }
}