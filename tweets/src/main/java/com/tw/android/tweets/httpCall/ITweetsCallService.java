package com.tw.android.tweets.httpCall;

import com.tw.android.tweets.httpCall.bean.response.TweetsRsp;
import com.tw.android.tweets.httpCall.bean.response.UserInfoRsp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Tweets requestCall service
 * @author by morton_ws on 2017/11/29.
 */

public interface ITweetsCallService {
    String TWEETS_HOST_URL = "http://thoughtworks-ios.herokuapp.com/";

    /**
     * request userInfo, Method GET
     * @return return UserInfoRsp
     */
    @GET("user/jsmith")
    Call<UserInfoRsp> getUserInfo();

    /**
     * request TweetsListData
     * @return TweetsListData
     */
    @GET("user/jsmith/tweets")
    Call<List<TweetsRsp>> getTweets();
}