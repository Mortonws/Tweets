package com.tw.android.tweets;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.tw.android.tweets.adpater.TweetsAdapter;
import com.tw.android.tweets.frame.TweetsModelImpl;
import com.tw.android.tweets.frame.TweetsPresenterImpl;
import com.tw.android.tweets.frame.frameInterface.ITweetsPresenter;
import com.tw.android.tweets.frame.frameInterface.ITweetsView;
import com.tw.android.tweets.httpCall.bean.response.TweetsRsp;
import com.tw.android.tweets.utils.TAGUtils;
import com.tw.android.tweets.utils.TweetsManager;

import java.util.List;

import static com.tw.android.tweets.utils.TweetsManager.EventMsgType.TWEETS_LOADING_MORE;
import static com.tw.android.tweets.utils.TweetsManager.EventMsgType.TWEETS_REFRESH;
import static com.tw.android.tweets.utils.TweetsManager.EventMsgType.TWEETS_REQUEST_RETRY;
import static com.tw.android.tweets.utils.TweetsManager.HandlerDelayTime.TWEETS_LOAD_TIME_DELAY;
import static com.tw.android.tweets.viewholder.factory.TweetsViewHolderFactory.TweetsLoadScenes.TWEETS_SCENES_LOADING;
import static com.tw.android.tweets.viewholder.factory.TweetsViewHolderFactory.TweetsLoadScenes.TWEETS_SCENES_LOAD_EMPTY;
import static com.tw.android.tweets.viewholder.factory.TweetsViewHolderFactory.TweetsLoadScenes.TWEETS_SCENES_LOAD_FAILED;

/**
 * TweetsList Activity
 * @author morton_ws on 2017/12/1.
 */
public class ThoughtWorksTweetsActivity extends BaseActivity implements ITweetsView{

    private ITweetsPresenter mTweetsPresenter;

    private SwipeRefreshLayout mTweetsRefresh;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mTweetsList;
    private TweetsAdapter mAdapter;

    private boolean isLoadingTweets = false;
    private boolean isRequestUserInfoSuc = true;

    /**
     * receive tweets msg event to deal
     */
    private Handler mTweetsHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            int what = message.what;
            switch (what) {
                case TWEETS_REFRESH:
                    mTweetsRefresh.setRefreshing(false);
                    mAdapter.clearTweets();
                    mTweetsPresenter.refreshTweets();
                    break;
                case TWEETS_LOADING_MORE:
                    mTweetsPresenter.addMoreTweets();
                    isLoadingTweets = false;
                    break;
                case TWEETS_REQUEST_RETRY:
                    mAdapter.loadScenesChange(TWEETS_SCENES_LOADING);
                    if (!isRequestUserInfoSuc) {
                        mTweetsPresenter.requestUserInfo();
                    }
                    // the delay time is just for better result efficiently
                    mTweetsHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mTweetsPresenter.requestTweetsList();
                        }
                    }, TWEETS_LOAD_TIME_DELAY);
                    break;
            }
            return false;
        }
    });

    /**
     * init view
     */
    protected void initView() {
        mTweetsList = findViewById(R.id.tweets_list);
        mTweetsRefresh = findViewById(R.id.tweets_refresh);
    }

    /**
     * init param
     */
    protected void initPages() {
        TweetsManager.getInstance(mContext).registerHandler(mTweetsHandler);
        mTweetsPresenter = new TweetsPresenterImpl(this, new TweetsModelImpl());

        mAdapter = new TweetsAdapter(mContext);
        mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mTweetsList.setAdapter(mAdapter);
        mTweetsList.setLayoutManager(mLayoutManager);
        mTweetsList.setItemAnimator(new DefaultItemAnimator());

        mTweetsRefresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light, android.R.color.holo_green_light);

        mTweetsPresenter.requestUserInfo();
        mTweetsPresenter.requestTweetsList();
    }

    /**
     * init listener
     */
    protected void initListener() {
        mTweetsRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isRequestUserInfoSuc) {
                    mTweetsPresenter.requestUserInfo();
                }
                if (TweetsManager.getInstance(mContext).getTweetsLoadScenes() == TWEETS_SCENES_LOAD_FAILED) {
                    mTweetsPresenter.requestTweetsList();
                } else {
                    mTweetsHandler.removeMessages(TWEETS_REFRESH);
                    mTweetsHandler.sendEmptyMessageDelayed(TWEETS_REFRESH, TWEETS_LOAD_TIME_DELAY);
                }
            }
        });
        mTweetsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastVisibleItemPos = mLayoutManager.findLastVisibleItemPosition();
                if (!isLoadingTweets
                        && TweetsManager.getInstance(mContext).getTweetsLoadScenes() == TWEETS_SCENES_LOADING
                        && newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPos == mAdapter.getItemCount() - 1) {
                    isLoadingTweets = true;
                    mTweetsHandler.sendEmptyMessageDelayed(TWEETS_LOADING_MORE, TWEETS_LOAD_TIME_DELAY);
                }
            }
        });
    }

    /**
     * get contentViewLayoutId
     * @return layoutId
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_thought_works_tweets;
    }

    /**
     * activity finish callback
     */
    @Override
    public void finish() {
        super.finish();
        TweetsManager.getInstance(mContext).unregisterHandler(mTweetsHandler);
    }

    /**
     * implements for {@link ITweetsView} onReqInfoSuccess method
     * @param userInfo callback obj, the response is UserInfoRsp, reWrap param for new TweetsRsp obj
     */
    @Override
    public void onReqUserInfoSuccess(TweetsRsp userInfo) {
        TweetsManager.getInstance(mContext).setRequestUserInfo(true);
        mAdapter.addUserProfile(userInfo);
        isRequestUserInfoSuc = true;
    }

    /**
     * implements for {@link ITweetsView} onReqUserInfoFail method
     * @param throwable failed throwable
     */
    @Override
    public void onReqUserInfoFail(Throwable throwable) {
        TweetsManager.getInstance(mContext).setRequestUserInfo(true);
        mAdapter.notifyDataSetChanged();
        isRequestUserInfoSuc = false;
        Log.e(TAGUtils.TAG_TWEETS_ACTIVITY, "request userProfileFailed", throwable);
    }

    /**
     * implements for {@link ITweetsView} onReqTweetsListSuccess method
     * @param tweetsList the response for tweetsList data
     */
    @Override
    public void onReqTweetsListSuccess(List<TweetsRsp> tweetsList) {
            if (tweetsList.size() == 0) {
                mAdapter.loadScenesChange(TWEETS_SCENES_LOAD_EMPTY);
            } else {
                mTweetsPresenter.addMoreTweets();
            }
    }

    /**
     * implements for {@link ITweetsView} onReqTweetsListFail method
     * @param throwable if request tweetsList fail, this contains failed reason
     */
    @Override
    public void onReqTweetsListFail(Throwable throwable) {
        Log.e(TAGUtils.TAG_TWEETS_ACTIVITY, "request TweetsListFailed", throwable);
        mAdapter.loadScenesChange(TWEETS_SCENES_LOAD_FAILED);
    }

    /**
     * implements for {@link ITweetsView} tweetsLoadScenesChange method
     * @param loadScenes the diff scenes for tweets load
     */
    @Override
    public void tweetsLoadScenesChange(int loadScenes) {
        mAdapter.loadScenesChange(loadScenes);
    }

    /**
     * implements for {@link ITweetsView} addTweetsList method
     * @param tweetsList the tweets list data
     */
    @Override
    public void addTweetsList(List<TweetsRsp> tweetsList) {
        mAdapter.addTweets(tweetsList);
    }
}