package edu.byu.cs.tweeter.client.presenter;

import java.net.MalformedURLException;
import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.services.StatusService;
import edu.byu.cs.tweeter.client.model.service.observers.StoryObserver;
import edu.byu.cs.tweeter.client.presenter.view.FeedView;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FeedRequest;

public class FeedPresenter extends PagedParentPresenter {
    public FeedPresenter(FeedView view) {
        super(view);
        statusService = new StatusService();
    }

    private StatusService statusService;
    private Status lastStatus;
    private boolean hasMorePages = true;

    public boolean isLoading() {
        return parentIsLoading();
    }

    public void loadMoreItems(User user) throws MalformedURLException {
        if (!parentIsLoading() && hasMorePages) {   // This guard is important for avoiding a race condition in the scrolling code.
            setup();

            FeedRequest request = new FeedRequest(user, lastStatus, Cache.getInstance().getCurrUserAuthToken(), getPageSize());

            statusService.loadFeedItems(request, new StoryObserver() {
                @Override
                public void handleSuccess(List<Status> statuses, boolean hasMorePages) {
                    FeedPresenter.this.lastStatus = (Status) lastItem(statuses);
                    FeedPresenter.this.hasMorePages = hasMorePages;
                    pagedHandleSuccess(statuses, hasMorePages);
                }

                @Override
                public void handleFailure(String message) {
                    pagedHandleFailure(message);
                }
            });
        }
    }
    
}
