package edu.byu.cs.tweeter.client.presenter.view;

import edu.byu.cs.tweeter.model.domain.User;

public interface RegisterView extends ogView {
    void beginActivity(User user);
}
