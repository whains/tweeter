package edu.byu.cs.tweeter.client.presenter;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.services.MainService;
import edu.byu.cs.tweeter.client.model.service.observers.PostStatusObserver;
import edu.byu.cs.tweeter.client.presenter.view.MainView;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainPresenterUnitTest {

    private MainView mockView;
    private MainService mockMainService;
    private Cache mockCache;

    private MainPresenter mainPresenterSpy;

    User currUser;
    Status newStatus;

    public String getFormattedDateTime() throws ParseException {
        SimpleDateFormat userFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat statusFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");

        return statusFormat.format(userFormat.parse(LocalDate.now().toString() + " " + LocalTime.now().toString().substring(0, 8)));
    }

    @BeforeEach
    public void setup() throws ParseException {
        mockView = Mockito.mock(MainView.class);
        mockMainService = Mockito.mock(MainService.class);
        mockCache = Mockito.mock(Cache.class);

        mainPresenterSpy = Mockito.spy(new MainPresenter(mockView));
        Mockito.doReturn(mockMainService).when(mainPresenterSpy).getMainService();

        Cache.setInstance(mockCache);

        currUser = mockCache.getCurrUser();
        String message = "testing post";
        List<String> containedMentions = new ArrayList<>();
        List<String> containedUrls = new ArrayList<>();

        newStatus = new Status(message, currUser, getFormattedDateTime(),
                mainPresenterSpy.parseURLs(message, containedUrls),
                mainPresenterSpy.parseMentions(message, containedMentions));
    }

    @Test
    public void testPostStatus_postStatusSucceeds() {
        Answer<Void> successAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                PostStatusObserver observer = invocation.getArgumentAt(1, PostStatusObserver.class);
                observer.handleSuccess("Successfully Posted!");
                return null;
            }
        };

        Mockito.doAnswer(successAnswer).when(mockMainService).postStatus(Mockito.any(), Mockito.any());

        mainPresenterSpy.postStatus(newStatus);

        Mockito.verify(mockView).successfulPost("Successfully Posted!");
    }

    @Test
    public void testPostStatus_postStatusFails() {
        Answer<Void> failureAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                PostStatusObserver observer = invocation.getArgumentAt(1, PostStatusObserver.class);
                observer.handleFailure("failure string");
                return null;
            }
        };

        Mockito.doAnswer(failureAnswer).when(mockMainService).postStatus(Mockito.any(), Mockito.any());

        mainPresenterSpy.postStatus(newStatus);

        Mockito.verify(mockView, Mockito.times(0)).successfulPost("Successfully Posted!");
        Mockito.verify(mockView).displayErrorMessage("failure string");
    }

    @Test
    public void testPostStatus_incorrectParameter() {
        Answer<Void> failureAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                PostStatusObserver observer = invocation.getArgumentAt(1, PostStatusObserver.class);
                observer.handleFailure("failure string");
                return null;
            }
        };

        Mockito.doAnswer(failureAnswer).when(mockMainService).postStatus(Mockito.any(), Mockito.any());

        mainPresenterSpy.postStatus(null);

        Mockito.verify(mockView, Mockito.times(0)).successfulPost("Successfully Posted!");
        Mockito.verify(mockView).displayErrorMessage("failure string");
    }
}
