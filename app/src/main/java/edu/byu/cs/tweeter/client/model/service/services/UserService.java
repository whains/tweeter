package edu.byu.cs.tweeter.client.model.service.services;

import android.widget.EditText;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.LoginTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks.RegisterTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTasks.handlers.GetUserHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTasks.handlers.LoginHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTasks.handlers.RegisterHandler;
import edu.byu.cs.tweeter.client.model.service.observers.GetUserObserver;
import edu.byu.cs.tweeter.client.model.service.observers.MakeUserObserver;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;

public class UserService extends SourceService {

    public void login(LoginRequest request, GetUserObserver loginObserver) {
        LoginTask loginTask = new LoginTask(request, new LoginHandler(loginObserver));

        executeSingleTask(loginTask);
    }

    public void register(RegisterRequest request, MakeUserObserver registerObserver) {
        RegisterTask registerTask = new RegisterTask(request, new RegisterHandler(registerObserver));

        executeSingleTask(registerTask);
    }

    public void getUser(GetUserRequest request, GetUserObserver getUserObserver) {
        GetUserTask getUserTask = new GetUserTask(request, new GetUserHandler(getUserObserver));

        executeSingleTask(getUserTask);
    }

}
