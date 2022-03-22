package edu.byu.cs.tweeter.client.presenter;

import java.net.MalformedURLException;
import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.observers.StoryObserver;
import edu.byu.cs.tweeter.client.model.service.services.MainService;
import edu.byu.cs.tweeter.client.model.service.services.StatusService;
import edu.byu.cs.tweeter.client.presenter.view.StoryView;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;

public class StoryPresenter extends PagedParentPresenter {
    public StoryPresenter(StoryView view) {
        super(view);
    }

    private StatusService statusService;

    public StatusService getStatusService() {
        if (statusService == null) {
            statusService = new StatusService();
        }
        return statusService;
    }

    private Status lastStatus;
    private boolean hasMorePages = true;

    public boolean isLoading() {
        return parentIsLoading();
    }

    public void loadMoreItems(User user) throws MalformedURLException {
        if (!parentIsLoading() && hasMorePages) {
            setup();

            StoryRequest request = new StoryRequest(user, lastStatus, Cache.getInstance().getCurrUserAuthToken(), getPageSize());

            //TODO TEST TO SEE IF SUCCESSFUL

            getStatusService().loadMoreItems(request, new StoryObserver() {
                @Override
                public void handleSuccess(List<Status> Statuses, boolean hasMorePages) {
                    StoryPresenter.this.lastStatus = (Status) lastItem(Statuses);
                    StoryPresenter.this.hasMorePages = hasMorePages;
                    pagedHandleSuccess(Statuses, hasMorePages);
                }

                @Override
                public void handleFailure(String message) {
                    pagedHandleFailure(message);
                }
            });
        }
    }
}

