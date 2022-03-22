package edu.byu.cs.tweeter.client.presenter;

import android.widget.EditText;

import edu.byu.cs.tweeter.client.presenter.view.ogView;

public abstract class ogPresenter {
    public ogPresenter(ogView view) {
        this.view = view;
    }

    private ogView view;

    protected void ogHandleFailure(String msg) {
        view.displayErrorMessage(msg);
    }

}
