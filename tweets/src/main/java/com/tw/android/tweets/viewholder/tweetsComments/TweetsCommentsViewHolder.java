package com.tw.android.tweets.viewholder.tweetsComments;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.tw.android.tweets.R;
import com.tw.android.tweets.httpCall.bean.TweetsCommentsBean;
import com.tw.android.tweets.viewholder.base.BaseViewHolder;

/**
 * Tweets Comments ViewHolder java
 * @author by morton_ws on 2017/12/1.
 */

public class TweetsCommentsViewHolder extends BaseViewHolder<TweetsCommentsBean> {

    private TextView mSenderName;
    private TextView mTweetsComments;

    public TweetsCommentsViewHolder(View view, Context context) {
        super(view, context);

        mSenderName = view.findViewById(R.id.comments_name);
        mTweetsComments = view.findViewById(R.id.tweets_comments);
    }

    @Override
    public void showTweetsDetails() {
        String nickName = mTweets.sender.nick;
        String userName = mTweets.sender.username;
        String content = mTweets.content;

        mTweetsComments.setText(content);
        if (TextUtils.isEmpty(nickName)) {
            if (TextUtils.isEmpty(userName)) {
                mSenderName.setText("好友：");
            } else {
                userName = userName + "：";
                mSenderName.setText(userName);
            }
        } else {
            nickName = nickName + "：";
            mSenderName.setText(nickName);
        }
    }

}
