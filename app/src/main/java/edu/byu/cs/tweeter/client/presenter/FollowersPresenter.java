package edu.byu.cs.tweeter.client.presenter;

import java.net.MalformedURLException;
import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.services.FollowersService;
import edu.byu.cs.tweeter.client.model.service.observers.FollowersObserver;
import edu.byu.cs.tweeter.client.presenter.view.FollowersView;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;

public class FollowersPresenter extends PagedParentPresenter {
    public FollowersPresenter(FollowersView view) {
        super(view);
        followersService = new FollowersService();
    }

    private FollowersService followersService;
    private User lastFollower;
    private boolean hasMorePages = true;

    public boolean isLoading() {
        return parentIsLoading();
    }

    public void loadMoreItems(User user) throws MalformedURLException {
        if (!parentIsLoading() && hasMorePages) {   // This guard is important for avoiding a race condition in the scrolling code.
            setup();

            FollowersRequest request = new FollowersRequest(user, lastFollower, Cache.getInstance().getCurrUserAuthToken(), getPageSize());

            followersService.loadMoreItems(request, new FollowersObserver() {
                @Override
                public void handleSuccess(List<User> followers, boolean hasMorePages) {
                    FollowersPresenter.this.lastFollower = (User) lastItem(followers);
                    FollowersPresenter.this.hasMorePages = hasMorePages;
                    pagedHandleSuccess(followers, hasMorePages);
                }

                @Override
                public void handleFailure(String message) {
                    pagedHandleFailure(message);
                }
            });
        }
    }
}
