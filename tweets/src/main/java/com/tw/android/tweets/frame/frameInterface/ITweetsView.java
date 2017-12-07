package com.tw.android.tweets.frame.frameInterface;

import com.tw.android.tweets.httpCall.bean.response.TweetsRsp;

import java.util.List;

/**
 * interface view for tweets MVP
 * @author by morton_ws on 2017/12/3.
 */

public interface ITweetsView {
    /**
     * success callback for request userInfo
     *
     * @param userInfo callback obj, the response is UserInfoRsp, reWrap param for new TweetsRsp obj
     */
    void onReqUserInfoSuccess(TweetsRsp userInfo);

    /**
     * fail callback for request userInfo
     *
     * @param throwable failed throwable
     */
    void onReqUserInfoFail(Throwable throwable);

    /**
     * success callback for request tweetsList
     *
     * @param tweetsList the response for tweetsList data
     */
    void onReqTweetsListSuccess(List<TweetsRsp> tweetsList);

    /**
     * fail callback for request tweetsList
     *
     * @param throwable if request tweetsList fail, this contains failed reason
     */
    void onReqTweetsListFail(Throwable throwable);

    /**
     * three scenes for tweets load, Loading, Empty, Failed,
     * different scenes show diff view, failed scenes can click for reRequest
     *
     * @param loadScenes the diff scenes for tweets load
     */
    void tweetsLoadScenesChange(int loadScenes);

    /**
     * add tweets list to RecyclerView Adapter
     *
     * @param tweetsList the tweets list data
     */
    void addTweetsList(List<TweetsRsp> tweetsList);
}