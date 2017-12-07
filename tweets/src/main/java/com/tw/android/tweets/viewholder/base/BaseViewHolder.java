package com.tw.android.tweets.viewholder.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author by morton_ws on 2017/11/30.
 * BaseViewHolder for both tweetsDetail & tweetsComments
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    protected Context mContext;
    protected T mTweets;

    public BaseViewHolder(View view, Context context) {
        super(view);
        this.mContext = context;
    }

    /**
     * show tweets detail info in ViewHolder
     * @param tweets adapter item data
     */
    public void showTweets(T tweets) {
        this.mTweets = tweets;
        showTweetsDetails();
    }

    /**
     * different ViewHolder deal different business
     */
    public abstract void showTweetsDetails();

}
