package edu.byu.cs.tweeter.client.presenter;

import android.widget.EditText;
import android.widget.ImageView;

import edu.byu.cs.tweeter.client.model.service.services.UserService;
import edu.byu.cs.tweeter.client.model.service.observers.MakeUserObserver;
import edu.byu.cs.tweeter.client.presenter.view.RegisterView;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;

public class RegisterPresenter extends ogPresenter {
    public RegisterPresenter(RegisterView view) {
        super(view);
        this.view = view;
        userService = new UserService();
    }

    private UserService userService;
    private RegisterView view;

    public void register(EditText firstName, EditText lastName, EditText alias, EditText password, String imageBytesBase64) {

        RegisterRequest request = new RegisterRequest(firstName.getText().toString(), lastName.getText().toString(), imageBytesBase64, alias.getText().toString(), password.getText().toString());

        userService.register(request, new MakeUserObserver() {
            @Override
            public void handleSuccess(User user) {
                view.beginActivity(user);
            }

            @Override
            public void handleFailure(String message) {
                ogHandleFailure(message);
            }
        });
    }

    public void validateRegistration(EditText firstName, EditText lastName, EditText alias, EditText password, ImageView imageToUpload) {
        if (firstName.getText().length() == 0) {
            throw new IllegalArgumentException("First Name cannot be empty.");
        }
        if (lastName.getText().length() == 0) {
            throw new IllegalArgumentException("Last Name cannot be empty.");
        }
        if (alias.getText().length() == 0) {
            throw new IllegalArgumentException("Alias cannot be empty.");
        }
        if (imageToUpload.getDrawable() == null) {
            throw new IllegalArgumentException("Profile image must be uploaded.");
        }
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
