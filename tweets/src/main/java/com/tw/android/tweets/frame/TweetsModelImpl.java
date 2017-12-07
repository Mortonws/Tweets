package com.tw.android.tweets.frame;

import com.tw.android.tweets.frame.frameInterface.ITweetsModel;
import com.tw.android.tweets.httpCall.TweetsRequestClient;
import com.tw.android.tweets.httpCall.TweetsRequestCallback;
import com.tw.android.tweets.httpCall.bean.response.TweetsRsp;
import com.tw.android.tweets.httpCall.bean.response.UserInfoRsp;

import java.util.List;

/**
 * impl for interface ITweetsModel
 * do request for tweets
 * @author by morton_ws on 2017/12/3.
 */

public class TweetsModelImpl implements ITweetsModel {
    /**
     * specific method for request UserInfo, call TweetsRequestClient's requestUserInfo method
     * @param callback param for request UserInfo callback
     */
    @Override
    public void requestUserInfo(TweetsRequestCallback<UserInfoRsp> callback) {
        TweetsRequestClient.getInstance().requestUserInfo(callback);
    }

    /**
     * specific method for request TweetsList, call TweetsRequestClient's requestTweetsList method
     * @param callback param for request TweetsList callback
     */
    @Override
    public void requestTweetsList(TweetsRequestCallback<List<TweetsRsp>> callback) {
        TweetsRequestClient.getInstance().requestTweets(callback);
    }
}