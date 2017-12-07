package com.tw.android.tweets.viewholder.factory;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tw.android.tweets.R;
import com.tw.android.tweets.httpCall.bean.response.TweetsRsp;
import com.tw.android.tweets.viewholder.base.BaseViewHolder;
import com.tw.android.tweets.viewholder.tweetsDetail.TweetsContentViewHolder;
import com.tw.android.tweets.viewholder.tweetsDetail.TweetsDefaultViewHolder;
import com.tw.android.tweets.viewholder.tweetsDetail.TweetsImageViewHolder;
import com.tw.android.tweets.viewholder.tweetsDetail.TweetsLoadViewHolder;
import com.tw.android.tweets.viewholder.tweetsDetail.HeaderUserInfoViewHolder;

import java.util.List;

/**
 * @author by morton_ws on 2017/11/30.
 * ViewHolderFactory for TweetsDetail
 */

public class TweetsViewHolderFactory {

    /**
     * according to different tweets get different itemType
     */
    public static int getItemType(TweetsRsp tweets) {
        int type = TweetsHolderType.TYPE_TWEETS_DEFAULT;
        List<TweetsRsp.ImagesBean> imagesBeanList = tweets.images;
        String content = tweets.content;
        if (imagesBeanList != null && imagesBeanList.size() != 0) {
            type = TweetsHolderType.TYPE_TWEETS_IMAGE;
        } else if (!TextUtils.isEmpty(content)) {
            type = TweetsHolderType.TYPE_TWEETS_CONTENT_ONLY;
        }
        return type;
    }

    /**
     * according to different itemType get different ViewHolder
     */
    public static BaseViewHolder<TweetsRsp> getViewHolder(Context context, int itemType, ViewGroup parent, LayoutInflater inflater) {
        BaseViewHolder<TweetsRsp> viewHolder;
        View view;
        switch (itemType) {
            case TweetsHolderType.TYPE_TWEETS_HEADER_USER_INFO:
                view = inflater.inflate(R.layout.item_tweets_header_user_info, parent, false);
                viewHolder = new HeaderUserInfoViewHolder(view, context);
                break;
            case TweetsHolderType.TYPE_TWEETS_LOAD:
                view = inflater.inflate(R.layout.item_tweets_loading, parent, false);
                viewHolder = new TweetsLoadViewHolder(view, context);
                break;
            case TweetsHolderType.TYPE_TWEETS_CONTENT_ONLY:
                view = inflater.inflate(R.layout.item_tweets_contents_only, parent, false);
                viewHolder = new TweetsContentViewHolder(view, context);
                break;
            case TweetsHolderType.TYPE_TWEETS_IMAGE:
                view = inflater.inflate(R.layout.item_tweets_image, parent, false);
                viewHolder = new TweetsImageViewHolder(view, context);
                break;
            case TweetsHolderType.TYPE_TWEETS_DEFAULT:
            default:
                view = inflater.inflate(R.layout.item_tweets_contents_only, parent, false);
                viewHolder = new TweetsDefaultViewHolder(view, context);
                break;

        }
        return viewHolder;
    }

    /**
     * list of tweetsHolderType
     */
    public interface TweetsHolderType {
        //TYPE USER PROFILE
        int TYPE_TWEETS_HEADER_USER_INFO = 0x1000;

        //TYPE LOAD, three scenes
        int TYPE_TWEETS_LOAD = 0x2010;

        //TYPE DEFAULT, maybe need update app version to show this tweets details
        int TYPE_TWEETS_DEFAULT = 0x2020;

        //TYPE TXT
        int TYPE_TWEETS_CONTENT_ONLY = 0x3000;
        //TYPE IMAGE
        int TYPE_TWEETS_IMAGE = 0x4001;

        //may you need "TYPE URL"
        // for Example int TYPE_TWEETS_URL = 0x5000;

        //or "TYPE ADs"
        // for Example int TYPE_TWEETS_AD = 0x6000;
    }

    /**
     * load scenes type, 1.loading tweets; 2.load tweets empty; 3.load tweets failed
     * different load scenes need show different tip view
     */
    public interface TweetsLoadScenes {
        // loading tweets
        int TWEETS_SCENES_LOADING = 0x2011;
        // load tweets empty
        int TWEETS_SCENES_LOAD_EMPTY = 0x2012;
        // load tweets failed
        int TWEETS_SCENES_LOAD_FAILED = 0x2013;
    }
}