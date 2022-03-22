package edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;

public class RegisterTask extends AuthenticationTask {

    public RegisterTask(RegisterRequest request, Handler messageHandler) {
        super(messageHandler, new LoginRequest(request.getUsername(), request.getPassword()), request.getUsername(), request.getPassword());
        this.request = request;
        this.first = request.getFirstName();
        this.last = request.getLastName();
        this.image = request.getImage();
    }

    private RegisterRequest request;
    private String first;
    private String last;
    private String image;

    static final String URL_PATH = "/register";
    private static final String LOG_TAG = "RegisterTask";

    @Override
    protected void runTask() throws IOException, TweeterRemoteException {
        RegisterResponse response = getServerFacade().register(request, URL_PATH);
        super.runTask();
    }
}
