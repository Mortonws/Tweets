package com.tw.android.tweets.viewholder.tweetsDetail;

import android.content.Context;
import android.view.View;

import com.tw.android.tweets.R;
import com.tw.android.tweets.views.TweetsImageView;

/**
 * deal with tweets image item ViewHolder java
 * @author by morton_ws on 2017/12/1.
 */

public class TweetsImageViewHolder extends TweetsContentViewHolder {
    private TweetsImageView mImageView;

    public TweetsImageViewHolder(View view, Context context) {
        super(view, context);
        mImageView = view.findViewById(R.id.tweets_image_view);
    }

    @Override
    public void showTweetsDetails() {
        super.showTweetsDetails();
        mImageView.setImageSources(mTweets.images);
    }
}
