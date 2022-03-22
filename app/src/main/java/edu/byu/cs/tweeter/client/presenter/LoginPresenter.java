package edu.byu.cs.tweeter.client.presenter;

import android.util.Log;
import android.widget.EditText;

import edu.byu.cs.tweeter.client.model.service.services.UserService;
import edu.byu.cs.tweeter.client.model.service.observers.GetUserObserver;
import edu.byu.cs.tweeter.client.presenter.view.LoginView;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;

public class LoginPresenter extends ogPresenter {
    public LoginPresenter(LoginView view) {
        super(view);
        this.loginView = view;
        userService = new UserService();
    }

    private LoginView loginView;
    private UserService userService;

    public void login(String alias, String password) {

        LoginRequest request = new LoginRequest(alias, password);

        userService.login(request, new GetUserObserver() {
            @Override
            public void handleSuccess(User loggedInUser) {
                System.out.println("Successful login");
                loginView.loginSuccess(loggedInUser);
            }

            @Override
            public void handleFailure(String msg) {
                ogHandleFailure(msg);
            }
        });
    }

    public void authenticateLogin(EditText alias, EditText password) {
        if (alias.getText().charAt(0) != '@') {
            throw new IllegalArgumentException("Alias must begin with @.");
        }
        if (alias.getText().length() < 2) {
            throw new IllegalArgumentException("Alias must contain 1 or more characters after the @.");
        }
        if (password.getText().length() == 0) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
    }
}
