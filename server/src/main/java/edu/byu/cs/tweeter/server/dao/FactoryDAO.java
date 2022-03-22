package edu.byu.cs.tweeter.server.dao;

public class FactoryDAO {

    UserDAO userDAO;
    AuthTokenDAO authTokenDAO;
    FollowDAO followDAO;
    FeedDAO feedDAO;
    StoryDAO storyDAO;

    public AuthTokenDAO getAuthTokenDAO() {
        if (authTokenDAO == null) {
            authTokenDAO = new AuthTokenDAO();
        }
        return authTokenDAO;
    }

    public UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new UserDAO();
        }
        return userDAO;
    }

    public FollowDAO getFollowDAO() {
        if (followDAO == null) {
            followDAO = new FollowDAO();
        }
        return followDAO;
    }

    public FeedDAO getFeedDAO() {
        if (feedDAO == null) {
            feedDAO = new FeedDAO();
        }
        return feedDAO;
    }

    public StoryDAO getStoryDAO() {
        if (storyDAO == null) {
            storyDAO = new StoryDAO();
        }
        return storyDAO;
    }
}
