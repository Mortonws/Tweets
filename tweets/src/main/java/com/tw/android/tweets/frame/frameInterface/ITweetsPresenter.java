package com.tw.android.tweets.frame.frameInterface;

/**
 * interface presenter for tweets MVP
 * @author by morton_ws on 2017/12/3.
 */

public interface ITweetsPresenter {
    /**
     * request for userInfo data
     */
    void requestUserInfo();

    /**
     * request for tweetsList data
     */
    void requestTweetsList();

    /**
     * add more tweets after loading tweets or response tweets
     */
    void addMoreTweets();

    /**
     * refresh tweets
     */
    void refreshTweets();
}