package edu.byu.cs.tweeter.client.presenter.view;

import edu.byu.cs.tweeter.model.domain.User;

public interface LoginView extends ogView {
    void loginSuccess(User loggedInUser);
}
