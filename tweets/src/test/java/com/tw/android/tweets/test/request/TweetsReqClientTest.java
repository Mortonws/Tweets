package com.tw.android.tweets.test.request;

import android.support.annotation.NonNull;

import com.tw.android.tweets.httpCall.TweetsRequestClient;
import com.tw.android.tweets.httpCall.TweetsRequestCallback;
import com.tw.android.tweets.httpCall.bean.response.TweetsRsp;
import com.tw.android.tweets.httpCall.bean.response.UserInfoRsp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author by morton_ws on 2017/12/2.
 */
public class TweetsReqClientTest {

    private String mFailMsg;
    private boolean isRequestSuc = true;

    private CountDownLatch mLatch = new CountDownLatch(1);

    @Before
    public void setUp() {
        isRequestSuc = true;
        mFailMsg = null;
    }

    @Test
    public void requestUserInfo() throws Exception {
        TweetsRequestClient.getInstance().requestUserInfo(new TweetsRequestCallback<UserInfoRsp>() {
            @Override
            public void onSuccess(@NonNull UserInfoRsp userInfoRsp) {
                mLatch.countDown();
            }

            @Override
            public void onFail(Throwable throwable) {
                isRequestSuc = false;
                mFailMsg = throwable.getMessage();
                mLatch.countDown();
            }
        });
        mLatch.await();
        if (isRequestSuc) {
            System.out.println("requestUserInfoSuc");
        } else {
            Assert.assertNotNull(mFailMsg);
            System.out.println("requestUserInfoFail: " + mFailMsg);
        }
    }

    @Test
    public void requestTweetsList() throws Exception{
        TweetsRequestClient.getInstance().requestTweets(new TweetsRequestCallback<List<TweetsRsp>>() {
            @Override
            public void onSuccess(@NonNull List<TweetsRsp> tweetsRsp) {
                mLatch.countDown();
            }

            @Override
            public void onFail(Throwable throwable) {
                isRequestSuc = false;
                mFailMsg = throwable.getMessage();
                mLatch.countDown();
            }
        });
        mLatch.await();
        if (isRequestSuc) {
            System.out.println("requestTweetsLisSuc");
        } else {
            Assert.assertNotNull(mFailMsg);
            System.out.println("requestUserInfoFail: " + mFailMsg);
        }
    }
}
