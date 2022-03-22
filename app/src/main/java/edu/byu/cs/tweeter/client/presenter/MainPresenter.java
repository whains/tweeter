package edu.byu.cs.tweeter.client.presenter;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.services.MainService;
import edu.byu.cs.tweeter.client.model.service.observers.FollowObserver;
import edu.byu.cs.tweeter.client.model.service.observers.GetFollowCountObserver;
import edu.byu.cs.tweeter.client.model.service.observers.IsFollowerObserver;
import edu.byu.cs.tweeter.client.model.service.observers.LogoutObserver;
import edu.byu.cs.tweeter.client.model.service.observers.PostStatusObserver;
import edu.byu.cs.tweeter.client.model.service.observers.UnfollowObserver;
import edu.byu.cs.tweeter.client.presenter.view.MainView;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.GetFollowCountRequest;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.request.UnfollowRequest;

public class MainPresenter extends ogPresenter {
    public MainPresenter(MainView view) {
        super(view);
        this.view = view;
    }

    private MainView view;
    private MainService mainService;

    public MainService getMainService() {
        if (mainService == null) {
            mainService = new MainService();
        }
        return mainService;
    }

    public void postStatus(Status status) {

        PostStatusRequest request = new PostStatusRequest(status, Cache.getInstance().getCurrUserAuthToken());

        getMainService().postStatus(request, new PostStatusObserver() {
            @Override
            public void handleSuccess(String msg) {
                view.successfulPost(msg);
            }

            @Override
            public void handleFailure(String msg) {
                ogHandleFailure(msg);
            }
        });
    }

    public void unfollow(User user) {

        User currentUser = Cache.getInstance().getCurrUser();
        IsFollowerRequest request = new IsFollowerRequest(user, currentUser, Cache.getInstance().getCurrUserAuthToken());

        getMainService().unfollow(request, new UnfollowObserver() {
            @Override
            public void handleSuccess() {
                uFollowStatus(true);
                uFollowButton(true);
            }

            @Override
            public void handleFailure(String msg) {
                ogHandleFailure(msg);
                uFollowButton(true);
            }
        });
    }

    public void follow(User selectedUser) {

        User currentUser = Cache.getInstance().getCurrUser();
        IsFollowerRequest request = new IsFollowerRequest(selectedUser, currentUser, Cache.getInstance().getCurrUserAuthToken());

        getMainService().follow(request, new FollowObserver() {
            @Override
            public void handleSuccess() {
                uFollowStatus(false);
                uFollowButton(true);
            }

            @Override
            public void handleFailure(String msg) {
                ogHandleFailure(msg);
                uFollowButton(true);
            }
        });
    }

    public void isFollower(User user) {

        IsFollowerRequest request = new IsFollowerRequest(user, Cache.getInstance().getCurrUser(), Cache.getInstance().getCurrUserAuthToken());

        getMainService().isFollower(request, new IsFollowerObserver() {
            @Override
            public void handleSuccess(boolean isFollower) {
                view.uButtonColor(isFollower);
            }

            @Override
            public void handleFailure(String msg) {
                ogHandleFailure(msg);
            }
        });
    }

    public void getFollowCount(User user) {

        GetFollowCountRequest request = new GetFollowCountRequest(user, Cache.getInstance().getCurrUserAuthToken());

        getMainService().getFollowCount(request, new GetFollowCountObserver() {
            @Override
            public void handleSuccessFollowee(int count) {
                view.uFolloweeCount(count);
            }

            @Override
            public void handleSuccessFollower(int count) {
                view.uFollowerCount(count);
            }

            @Override
            public void handleFailure(String msg) {
                ogHandleFailure(msg);
            }
        });
    }

    public void logout() {

        LogoutRequest request = new LogoutRequest(Cache.getInstance().getCurrUserAuthToken());

        getMainService().logout(request, new LogoutObserver() {
            @Override
            public void handleSuccess() {
                view.displayLogout();
            }

            @Override
            public void handleFailure(String msg) {
                ogHandleFailure(msg);
            }
        });
    }

    public void uFollowStatus(boolean b) {
        view.uFollow(b);
    }

    public void uFollowButton(boolean b) {
        view.setFollowButton(b);
    }

    public List<String> parseMentions(String post, List<String> mentions) {
        for (String word : post.split("\\s")) {
            if (word.startsWith("@")) {
                word = word.replaceAll("[^a-zA-Z0-9]", "");
                word = "@".concat(word);

                mentions.add(word);
            }
        }

        return mentions;
    }

    public List<String> parseURLs(String post, List<String> urls) {
        for (String word : post.split("\\s")) {
            if (word.startsWith("http://") || word.startsWith("https://")) {
                int index = findUrlEndIndex(word);
                word = word.substring(0, index);
                urls.add(word);
            }
        }

        return urls;
    }

    public int findUrlEndIndex(String str) {
        if (str.contains(".com")) {
            int index = str.indexOf(".com");
            index += 4;
            return index;
        } else if (str.contains(".org")) {
            int index = str.indexOf(".org");
            index += 4;
            return index;
        } else if (str.contains(".edu")) {
            int index = str.indexOf(".edu");
            index += 4;
            return index;
        } else if (str.contains(".net")) {
            int index = str.indexOf(".net");
            index += 4;
            return index;
        } else if (str.contains(".mil")) {
            int index = str.indexOf(".mil");
            index += 4;
            return index;
        } else {
            return str.length();
        }
    }

}
