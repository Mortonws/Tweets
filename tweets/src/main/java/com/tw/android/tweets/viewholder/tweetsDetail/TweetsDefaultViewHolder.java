package com.tw.android.tweets.viewholder.tweetsDetail;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.tw.android.tweets.R;

/**
 * @author by morton_ws on 2017/12/1.
 * default viewholder if some itemType not define, it will show default tip
 */

public class TweetsDefaultViewHolder extends TweetsContentViewHolder {
    public TweetsDefaultViewHolder(View view, Context context) {
        super(view, context);
    }

    @Override
    public void showTweetsDetails() {
        super.showTweetsDetails();
        String content = mTweets.content;
        if (TextUtils.isEmpty(content)) {
            mTweetsContents.setVisibility(View.VISIBLE);
            mTweetsContents.setText(R.string.tweets_default_tip);
        }
    }
}
