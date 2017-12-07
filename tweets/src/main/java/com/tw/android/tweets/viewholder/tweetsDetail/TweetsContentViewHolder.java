package com.tw.android.tweets.viewholder.tweetsDetail;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tw.android.tweets.R;
import com.tw.android.tweets.adpater.TweetsCommentsAdapter;
import com.tw.android.tweets.httpCall.bean.TweetsCommentsBean;
import com.tw.android.tweets.httpCall.bean.response.TweetsRsp;
import com.tw.android.tweets.viewholder.base.BaseViewHolder;

import java.util.List;

/**
 * @author by morton_ws on 2017/11/30.
 *         <p>
 *         tweets content viewholder, show tweets contents & tweets comments
 *         it is also base class for four type img and default viewholder
 */

public class TweetsContentViewHolder extends BaseViewHolder<TweetsRsp> {
    TextView mTweetsContents;
    private ImageView mSenderAvatar;
    private TextView mSenderName;

    private RecyclerView mTweetsCommentsList;
    private TweetsCommentsAdapter mAdapter;
    @SuppressWarnings("FieldCanBeLocal")
    private LinearLayoutManager mLayoutManager;

    public TweetsContentViewHolder(View view, Context context) {
        super(view, context);

        mTweetsCommentsList = view.findViewById(R.id.tweets_comments_list);
        mSenderAvatar = view.findViewById(R.id.sender_avatar);
        mSenderName = view.findViewById(R.id.sender_name);
        mTweetsContents = view.findViewById(R.id.contents);

        mAdapter = new TweetsCommentsAdapter(mContext);
        mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mTweetsCommentsList.setAdapter(mAdapter);
        mTweetsCommentsList.setLayoutManager(mLayoutManager);
        mTweetsCommentsList.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void showTweetsDetails() {
        String avatarUrl = mTweets.sender.avatar;
        String nickName = mTweets.sender.nick;

        if (TextUtils.isEmpty(avatarUrl)) {
            Picasso.with(mContext).load(R.mipmap.default_avatar).into(mSenderAvatar);
        } else {
            Picasso.with(mContext)
                    .load(avatarUrl)
                    .placeholder(R.mipmap.default_avatar)
                    .error(R.mipmap.default_avatar)
                    .fit()
                    .centerCrop()
                    .into(mSenderAvatar);
        }
        if (TextUtils.isEmpty(nickName)) {
            String senderName = mTweets.sender.username;
            mSenderName.setText(senderName);
        } else {
            mSenderName.setText(nickName);
        }

        if (TextUtils.isEmpty(mTweets.content)) {
            mTweetsContents.setVisibility(View.GONE);
        } else {
            mTweetsContents.setVisibility(View.VISIBLE);
            mTweetsContents.setText(mTweets.content);
        }

        showTweetsComments();
    }

    private void showTweetsComments() {
        List<TweetsCommentsBean> commentsList = mTweets.comments;
        if (commentsList == null || commentsList.size() == 0) {
            mTweetsCommentsList.setVisibility(View.GONE);
            return;
        }
        mTweetsCommentsList.setVisibility(View.VISIBLE);
        mAdapter.addComments(commentsList);
    }
}
