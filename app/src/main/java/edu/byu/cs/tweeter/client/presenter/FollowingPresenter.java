package edu.byu.cs.tweeter.client.presenter;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Objects;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.services.FollowingService;
import edu.byu.cs.tweeter.client.model.service.observers.FollowingObserver;
import edu.byu.cs.tweeter.client.presenter.view.FollowingView;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;

public class FollowingPresenter extends PagedParentPresenter {
    public FollowingPresenter(FollowingView view) {
        super(view);
        followingService = new FollowingService();
    }

    private FollowingService followingService;
    private User lastFollowee;
    private boolean hasMorePages = true;

    public boolean isLoading() {
        return parentIsLoading();
    }

    public void loadMoreItems(User user) throws MalformedURLException {
        if (!parentIsLoading() && hasMorePages) {   // This guard is important for avoiding a race condition in the scrolling code.
            setup();

            FollowingRequest request = new FollowingRequest(user, lastFollowee, Cache.getInstance().getCurrUserAuthToken(), getPageSize());

            followingService.loadMoreItems(request, new FollowingObserver() {
                @Override
                public void handleSuccess(List<User> followees, boolean hasMorePages) {
                    FollowingPresenter.this.lastFollowee = (User) lastItem(followees);
                    FollowingPresenter.this.hasMorePages = hasMorePages;
                    pagedHandleSuccess(followees, hasMorePages);
                }

                @Override
                public void handleFailure(String message) {
                    pagedHandleFailure(message);
                }
            });
        }
    }
}
