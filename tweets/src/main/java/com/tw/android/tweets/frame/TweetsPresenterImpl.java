package com.tw.android.tweets.frame;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.tw.android.tweets.frame.frameInterface.ITweetsModel;
import com.tw.android.tweets.frame.frameInterface.ITweetsPresenter;
import com.tw.android.tweets.frame.frameInterface.ITweetsView;
import com.tw.android.tweets.httpCall.TweetsRequestCallback;
import com.tw.android.tweets.httpCall.bean.SenderInfoBean;
import com.tw.android.tweets.httpCall.bean.response.TweetsRsp;
import com.tw.android.tweets.httpCall.bean.response.UserInfoRsp;

import java.util.ArrayList;
import java.util.List;

import static com.tw.android.tweets.viewholder.factory.TweetsViewHolderFactory.TweetsLoadScenes.TWEETS_SCENES_LOADING;
import static com.tw.android.tweets.viewholder.factory.TweetsViewHolderFactory.TweetsLoadScenes.TWEETS_SCENES_LOAD_EMPTY;

/**
 * impl for interface ITweetsPresenter
 * @author by morton_ws on 2017/12/3.
 */

public class TweetsPresenterImpl implements ITweetsPresenter {
    private int mTweetsStartIndex = 0;
    @SuppressWarnings("FieldCanBeLocal")
    private int DEFAULT_ADD_COUNT = 5;
    private List<TweetsRsp> mTweetsData = new ArrayList<>();

    private ITweetsView mView;
    private ITweetsModel mModel;

    public TweetsPresenterImpl(ITweetsView view, ITweetsModel model) {
        mView = view;
        mModel = model;
    }

    /**
     * specific method for requestUserInfo, wrap new TweetsRsp obj by UserInfoRsp response data
     */
    @Override
    public void requestUserInfo() {
        mModel.requestUserInfo(new TweetsRequestCallback<UserInfoRsp>() {
            @Override
            public void onSuccess(@NonNull UserInfoRsp userInfoRsp) {
                SenderInfoBean userProfile = new SenderInfoBean();
                userProfile.profile = userInfoRsp.profileImage;
                userProfile.avatar = userInfoRsp.avatar;
                userProfile.nick = userInfoRsp.nick;
                userProfile.username = userInfoRsp.username;

                TweetsRsp tweetsRsp = new TweetsRsp();
                tweetsRsp.sender = userProfile;

                mView.onReqUserInfoSuccess(tweetsRsp);
            }

            @Override
            public void onFail(Throwable throwable) {
                mView.onReqUserInfoFail(throwable);
            }
        });
    }

    /**
     * specific method for requestTweetsList
     */
    @Override
    public void requestTweetsList() {
        mModel.requestTweetsList(new TweetsRequestCallback<List<TweetsRsp>>() {
            @Override
            public void onSuccess(@NonNull List<TweetsRsp> tweetsRsp) {
                mTweetsData.clear();
                for (TweetsRsp tweets : tweetsRsp) {
                    String content = tweets.content;
                    List<TweetsRsp.ImagesBean> tweetsImg = tweets.images;
                    if (TextUtils.isEmpty(content) && (tweetsImg == null || tweetsImg.size() == 0)) {
                        continue;
                    }
                    mTweetsData.add(tweets);
                }
                mView.onReqTweetsListSuccess(mTweetsData);
            }

            @Override
            public void onFail(Throwable throwable) {
                mView.onReqTweetsListFail(throwable);
            }
        });
    }

    /**
     * specific method addMoreTweets, when load tweets or request new tweetsList data
     */
    @Override
    public void addMoreTweets() {
        int toIndex = mTweetsStartIndex + DEFAULT_ADD_COUNT;
        int tweetsScenes = TWEETS_SCENES_LOADING;
        if (toIndex > mTweetsData.size()) {
            toIndex = mTweetsData.size();
            tweetsScenes = TWEETS_SCENES_LOAD_EMPTY;
        }
        mView.addTweetsList(mTweetsData.subList(mTweetsStartIndex, toIndex));
        mView.tweetsLoadScenesChange(tweetsScenes);
        mTweetsStartIndex = toIndex;
    }

    /**
     * refresh tweets
     */
    public void refreshTweets() {
        mTweetsStartIndex = 0;
        addMoreTweets();
    }
}
