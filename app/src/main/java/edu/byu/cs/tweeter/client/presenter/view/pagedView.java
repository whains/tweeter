package edu.byu.cs.tweeter.client.presenter.view;

import java.net.MalformedURLException;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;

public interface pagedView<T> extends ogView {
    void displayInfoMsg(String s);
    void displayMoreItems(List<T> items, boolean hasMorePages);
    void showUser(User user);
    void clearInfoMsg();
    void addLoadingFooter() throws MalformedURLException;
    void removeLoadingFooter();
}
