package com.tw.android.tweets.httpCall;

import com.tw.android.tweets.httpCall.bean.response.TweetsRsp;
import com.tw.android.tweets.httpCall.bean.response.UserInfoRsp;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.tw.android.tweets.httpCall.ITweetsCallService.TWEETS_HOST_URL;

/**
 * Singleton TweetsRequestClient
 * @author by morton_ws on 2017/11/29.
 */

public class TweetsRequestClient {
    private Retrofit mRetrofit;

    private TweetsRequestClient() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(TWEETS_HOST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().build())
                .build();
    }

    public static TweetsRequestClient getInstance() {
        return TweetsRequestSingleton.INSTANCE;
    }

    /**
     * request userProfile url
     * can see unit test method in package test
     *
     * @param callback response callback
     */
    public void requestUserInfo(TweetsRequestCallback<UserInfoRsp> callback) {
        mRetrofit.create(ITweetsCallService.class)
                .getUserInfo()
                .enqueue(callback);
    }

    /**
     * request tweets list url
     * can see unit test method in package test
     *
     * @param callback tweets list response callback
     */
    public void requestTweets(TweetsRequestCallback<List<TweetsRsp>> callback) {
        mRetrofit.create(ITweetsCallService.class)
                .getTweets()
                .enqueue(callback);
    }

    private static class TweetsRequestSingleton {
        private static TweetsRequestClient INSTANCE = new TweetsRequestClient();
    }
}
