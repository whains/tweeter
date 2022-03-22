package edu.byu.cs.tweeter.client.presenter;

import android.content.Context;
import android.widget.EditText;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.observers.PostStatusObserver;
import edu.byu.cs.tweeter.client.model.service.observers.StoryObserver;
import edu.byu.cs.tweeter.client.model.service.services.MainService;
import edu.byu.cs.tweeter.client.model.service.services.StatusService;
import edu.byu.cs.tweeter.client.model.service.services.UserService;
import edu.byu.cs.tweeter.client.presenter.view.MainView;
import edu.byu.cs.tweeter.client.presenter.view.StoryView;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;

public class StoryPresenterUnitTest {

    private StoryView mockView;
    private StatusService mockStatusService;
    private Cache mockCache;

    private StoryPresenter storyPresenterSpy;

    private User currUser;

    List<Status> Statuses = new ArrayList<>();

    public String getFormattedDateTime() throws ParseException {
        SimpleDateFormat userFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat statusFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");

        return statusFormat.format(userFormat.parse(LocalDate.now().toString() + " " + LocalTime.now().toString().substring(0, 8)));
    }

    @BeforeEach
    public void setup() throws ParseException {
        mockView = Mockito.mock(StoryView.class);
        mockStatusService = Mockito.mock(StatusService.class);
        mockCache = Mockito.mock(Cache.class);

        storyPresenterSpy = Mockito.spy(new StoryPresenter(mockView));
        Mockito.doReturn(mockStatusService).when(storyPresenterSpy).getStatusService();

        Cache.setInstance(mockCache);

        currUser = mockCache.getCurrUser();

        List<String> containedMentions = new ArrayList<>();
        List<String> containedUrls = new ArrayList<>();

        Status status1 = new Status("testing", currUser, getFormattedDateTime(),new ArrayList<String>(), new ArrayList<String>());
        Status status2 = new Status("testing", currUser, getFormattedDateTime(),new ArrayList<String>(), new ArrayList<String>());
        Status status3 = new Status("testing", currUser, getFormattedDateTime(),new ArrayList<String>(), new ArrayList<String>());
        Status status4 = new Status("testing", currUser, getFormattedDateTime(),new ArrayList<String>(), new ArrayList<String>());

        Statuses.add(status1);
        Statuses.add(status2);
        Statuses.add(status3);
        Statuses.add(status3);
    }

    @Test
    public void testStoryPageSuccess() throws MalformedURLException {
        Answer<Void> successAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                StoryObserver observer = invocation.getArgumentAt(1, StoryObserver.class);
                observer.handleSuccess(Statuses, true);
                return null;
            }
        };

        Mockito.doAnswer(successAnswer).when(mockStatusService).loadMoreItems(Mockito.any(), Mockito.any());

        storyPresenterSpy.loadMoreItems(currUser);

        Mockito.verify(mockView).removeLoadingFooter();
        Mockito.verify(mockView).displayMoreItems(Statuses, true);
    }
}
