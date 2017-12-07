package com.tw.android.tweets.viewholder.tweetsDetail;

import android.content.Context;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tw.android.tweets.R;
import com.tw.android.tweets.httpCall.bean.response.TweetsRsp;
import com.tw.android.tweets.utils.TweetsManager;
import com.tw.android.tweets.viewholder.base.BaseViewHolder;

import static com.tw.android.tweets.viewholder.factory.TweetsViewHolderFactory.TweetsLoadScenes.TWEETS_SCENES_LOADING;
import static com.tw.android.tweets.viewholder.factory.TweetsViewHolderFactory.TweetsLoadScenes.TWEETS_SCENES_LOAD_EMPTY;
import static com.tw.android.tweets.viewholder.factory.TweetsViewHolderFactory.TweetsLoadScenes.TWEETS_SCENES_LOAD_FAILED;

/**
 * @author by morton_ws on 2017/11/30.
 * load tip viewholder
 * 1) loading more tweets
 * 2) load empty
 * 3) load failed, click retry request
 */

public class TweetsLoadViewHolder extends BaseViewHolder<TweetsRsp> {
    private TextView mLoadFailed;
    private ViewGroup mLoadingMore;
    private ViewGroup mLoadEmpty;

    public TweetsLoadViewHolder(View view, Context context) {
        super(view, context);
        mLoadFailed = view.findViewById(R.id.load_failed);
        mLoadingMore = view.findViewById(R.id.loading_more);
        mLoadEmpty = view.findViewById(R.id.load_empty);
    }

    @Override
    public void showTweetsDetails() {
        int loadScenes = TweetsManager.getInstance(mContext).getTweetsLoadScenes();
        mLoadEmpty.setVisibility(View.GONE);
        mLoadingMore.setVisibility(View.GONE);
        mLoadFailed.setVisibility(View.GONE);
        if (loadScenes == TWEETS_SCENES_LOAD_FAILED) {
            mLoadFailed.setVisibility(View.VISIBLE);
        } else if (loadScenes == TWEETS_SCENES_LOADING) {
            mLoadingMore.setVisibility(View.VISIBLE);
        } else if (loadScenes == TWEETS_SCENES_LOAD_EMPTY) {
            mLoadEmpty.setVisibility(View.VISIBLE);
        }

        mLoadFailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg = Message.obtain();
                msg.what = TweetsManager.EventMsgType.TWEETS_REQUEST_RETRY;
                TweetsManager.getInstance(mContext).sendMsg(msg);
            }
        });
    }
}
