package edu.byu.cs.tweeter.client.model.net;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.request.GetFollowCountRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.model.net.response.GetFollowersCountResponse;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;
import edu.byu.cs.tweeter.model.net.response.SerializableRegisterResponse;

public class ServerFacadeUnitTest {

    private String URL_PATH;
    private RegisterRequest registerRequest;
    private FollowersRequest followersRequest;
    private GetFollowCountRequest followCountRequest;
    private ServerFacade serverFacadeSpy;

    @BeforeEach
    public void setup() {
        serverFacadeSpy = Mockito.spy(new ServerFacade());
    }

    @Test
    public void testRegister_success() throws IOException, TweeterRemoteException {

        URL_PATH = "/register";
        registerRequest = new RegisterRequest("Will", "Hainsworth", "image", "@whains", "password");

        RegisterResponse response = serverFacadeSpy.register(registerRequest, URL_PATH);

        assertEquals(true, response.isSuccess());
        assertEquals(null, response.getMessage());
    }

    @Test
    public void testRegister_failure() throws IOException, TweeterRemoteException {

        URL_PATH = "/register";
        registerRequest = null;

        try {
            RegisterResponse response = serverFacadeSpy.register(registerRequest, URL_PATH);
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "[BadRequest400] 400");
        }
    }

    @Test
    public void testGetFollowers_success() throws IOException, TweeterRemoteException {

        URL_PATH = "/getfollowers";
        User user = new User("test", "test", "test", "test");
        AuthToken authToken = new AuthToken("test", "test");
        followersRequest = new FollowersRequest(user, user, authToken, 10);

        FollowersResponse response = serverFacadeSpy.getFollowers(followersRequest, URL_PATH);

        assertEquals(true, response.isSuccess());
        assertEquals(null, response.getMessage());
    }

    @Test
    public void testGetFollowersCount_success() throws IOException, TweeterRemoteException {

        URL_PATH = "/getfollowerscount";
        User user = new User("test", "test", "test", "test");
        AuthToken authToken = new AuthToken("test", "test");
        followCountRequest = new GetFollowCountRequest(user, authToken);

        //GetFollowersCountResponse response = serverFacadeSpy.getFollowersCount(followCountRequest, URL_PATH);

    }

}
