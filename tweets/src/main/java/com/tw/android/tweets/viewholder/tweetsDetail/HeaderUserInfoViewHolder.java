package com.tw.android.tweets.viewholder.tweetsDetail;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tw.android.tweets.R;
import com.tw.android.tweets.httpCall.bean.SenderInfoBean;
import com.tw.android.tweets.httpCall.bean.response.TweetsRsp;
import com.tw.android.tweets.utils.TweetsManager;
import com.tw.android.tweets.viewholder.base.BaseViewHolder;

/**
 * Tweets list header UserInfo ViewHolder java
 * @author by morton_ws on 2017/11/30.
 */

public class HeaderUserInfoViewHolder extends BaseViewHolder<TweetsRsp> {
    private ImageView mImgProfile;
    private ImageView mImgAvatar;
    private TextView mUserName;

    public HeaderUserInfoViewHolder(View view, Context context) {
        super(view, context);
        mImgProfile = view.findViewById(R.id.img_profile);
        mImgAvatar = view.findViewById(R.id.img_avatar);
        mUserName = view.findViewById(R.id.user_name);
    }

    @Override
    public void showTweetsDetails() {
        if (!TweetsManager.getInstance(mContext).isRequestUserInfo()) {
            return;
        }
        SenderInfoBean senderInfo = mTweets.sender;
        String profileUrl = "";
        String avatarUrl = "";
        String nickName = "";
        String name = "";
        if (senderInfo != null) {
            profileUrl = mTweets.sender.profile;
            avatarUrl = mTweets.sender.avatar;
            nickName = mTweets.sender.nick;
            name = mTweets.sender.username;
        }
        if (TextUtils.isEmpty(profileUrl)) {
            Picasso.with(mContext)
                    .load(R.mipmap.profile_error_placeholder)
                    .into(mImgProfile);
        } else {
            Picasso.with(mContext)
                    .load(profileUrl)
                    .error(R.mipmap.profile_error_placeholder)
                    .into(mImgProfile);
        }
        if (TextUtils.isEmpty(avatarUrl)) {
            Picasso.with(mContext)
                    .load(R.mipmap.default_avatar)
                    .into(mImgAvatar);
        } else {
            Picasso.with(mContext)
                    .load(avatarUrl)
                    .error(R.mipmap.default_avatar)
                    .into(mImgAvatar);
        }
        if (TextUtils.isEmpty(nickName)) {
            if (TextUtils.isEmpty(name)) {
                mUserName.setVisibility(View.INVISIBLE);
            } else {
                mUserName.setText(name);
                mUserName.setVisibility(View.VISIBLE);
            }
        } else {
            mUserName.setText(nickName);
            mUserName.setVisibility(View.VISIBLE);
        }
    }
}
