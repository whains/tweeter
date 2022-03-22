package edu.byu.cs.tweeter.client.presenter;

import java.net.MalformedURLException;
import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.services.UserService;
import edu.byu.cs.tweeter.client.model.service.observers.GetUserObserver;
import edu.byu.cs.tweeter.client.presenter.view.pagedView;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;

public abstract class PagedParentPresenter<T> extends ogPresenter {
    public PagedParentPresenter(pagedView view) {
        super(view);
        this.view = view;
        userService = new UserService();
    }

    private pagedView view;
    private UserService userService;

    private static final int PAGE_SIZE = 10;

    private boolean hasMorePages = true;
    private boolean isLoading = false;

    public void getUser(String alias) {
        view.displayInfoMsg("Getting user's profile...");

        GetUserRequest request = new GetUserRequest(alias, Cache.getInstance().getCurrUserAuthToken());

        userService.getUser(request, new GetUserObserver() {

            @Override
            public void handleSuccess(User user) {
                view.clearInfoMsg();
                view.showUser(user);
            }

            @Override
            public void handleFailure(String msg) {
                view.clearInfoMsg();
                view.displayErrorMessage(msg);
            }
        });
    }

    protected boolean parentIsLoading() {
        return isLoading;
    }

    protected boolean isHasMorePages() { return hasMorePages; }

    protected static int getPageSize() { return PAGE_SIZE; }

    protected void setup() throws MalformedURLException {
        isLoading = true;
        view.addLoadingFooter();
    }

    protected T lastItem(List<T> items) {
        return (items.size() > 0) ? items.get(items.size() - 1) : null;
    }

    protected void pagedHandleSuccess(List<T> items, boolean hasMorePages) {
        isLoading = false;

        view.removeLoadingFooter();
        view.displayMoreItems(items, hasMorePages);
    }

    protected void pagedHandleFailure(String msg) {
        isLoading = false;

        view.removeLoadingFooter();
        view.displayErrorMessage(msg);
    }
}
