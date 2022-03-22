package edu.byu.cs.tweeter.client.model.service.backgroundTasks.tasks;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.util.Pair;

public abstract class PagedTask<T> extends AuthorizedTask {
    public PagedTask(Handler messageHandler, int limit, T lastItem, AuthToken authToken) {
        super(messageHandler, authToken);
        this.limit = limit;
        this.lastItem = lastItem;
    }

    public static final String ITEMS_KEY = "items";
    public static final String MORE_PAGES_KEY = "more-pages";

    private List<T> items;
    private boolean hasMorePages;
    private T lastItem;
    public T getLastItem() {
        return lastItem;
    }
    private int limit;
    public int getLimit() {
        return limit;
    }

    @Override
    protected void runTask() throws IOException, TweeterRemoteException {
        Pair<List<T>, Boolean> pageOfItems = getItems();

        items = pageOfItems.getFirst();
        hasMorePages = pageOfItems.getSecond();

        for (User u : returnUsers(items)) {
            BackgroundTaskUtils.loadImage(u);
        }
    }

    @Override
    protected void loadMessageBundle(Bundle msgBundle) {
        msgBundle.putSerializable(ITEMS_KEY, (Serializable) items);
        msgBundle.putBoolean(MORE_PAGES_KEY, hasMorePages);
    }

    protected void setHasMorePages(boolean hasMorePages) {
        this.hasMorePages = hasMorePages;
    }

    protected abstract Pair<List<T>, Boolean> getItems() throws IOException, TweeterRemoteException;

    protected abstract List<User> returnUsers(List<T> items);
}
