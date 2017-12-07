package com.tw.android.tweets.frame.frameInterface;

import com.tw.android.tweets.httpCall.TweetsRequestCallback;
import com.tw.android.tweets.httpCall.bean.response.TweetsRsp;
import com.tw.android.tweets.httpCall.bean.response.UserInfoRsp;

import java.util.List;

/**
 * model interface for tweets
 * @author by morton_ws on 2017/12/3.
 */

public interface ITweetsModel {
    /**
     * request for userInfo
     * @param callback param for request UserInfo callback
     */
    void requestUserInfo(TweetsRequestCallback<UserInfoRsp> callback);

    /**
     * request for tweetsList
     * @param callback param for request TweetsList callback
     */
    void requestTweetsList(TweetsRequestCallback<List<TweetsRsp>> callback);
}