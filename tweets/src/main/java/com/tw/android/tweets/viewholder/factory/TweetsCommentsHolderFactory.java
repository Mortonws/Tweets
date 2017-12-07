package com.tw.android.tweets.viewholder.factory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tw.android.tweets.R;
import com.tw.android.tweets.httpCall.bean.TweetsCommentsBean;
import com.tw.android.tweets.viewholder.base.BaseViewHolder;
import com.tw.android.tweets.viewholder.tweetsComments.TweetsCommentsViewHolder;

/**
 * @author by morton_ws on 2017/12/1.
 * ViewHolderFactory for TweetsComments
 */

public class TweetsCommentsHolderFactory {
    /**
     * get ItemType for comments Adapter's ViewHolder
     * @param commentsBean according to it get itemType, but now just one type, follow method easy to expend
     * @return itemType
     */
    @SuppressWarnings("unused")
    public static int getItemType(TweetsCommentsBean commentsBean) {
        return TweetsCommentsHolderType.TYPE_COMMENTS_SEND;
    }

    /**
     * according different itemType create different ViewHolder which extends BaseViewHolder
     * @param context each ViewHolder extends BaseView need this context
     * @param itemType according different item create different ViewHolder
     * @param inflater help to create different view for it's match ViewHolder
     * @param parent view inflate need
     * @return different viewHolder
     */
    public static BaseViewHolder<TweetsCommentsBean> getViewHolder(Context context, int itemType,
                                                                   LayoutInflater inflater, ViewGroup parent) {
        BaseViewHolder<TweetsCommentsBean> viewHolder;
        View view;
        switch (itemType) {
            default:
            case TweetsCommentsHolderType.TYPE_COMMENTS_SEND:
                view = inflater.inflate(R.layout.item_tweets_comments, parent, false);
                viewHolder = new TweetsCommentsViewHolder(view, context);
                break;
        }
        return viewHolder;
    }

    /**
     * Comments ViewHolder Type
     */
    public interface TweetsCommentsHolderType {
        int TYPE_COMMENTS_SEND = 0x1000;
        //maybe you need add reply type
        // int TYPE_COMMENTS_REPLY = 0x2000;
    }
}
