package edu.byu.cs.tweeter.client.presenter.view;

public interface MainView extends ogView {
    void uFolloweeCount(int count);
    void uFollowerCount(int count);
    void uButtonColor(boolean isFollower);
    void uFollow(boolean bool);
    void setFollowButton(boolean bool);
    void successfulPost(String msg);
    void onStatusPosted(String str);
    void displayLogout();
}
