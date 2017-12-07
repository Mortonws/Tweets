package com.tw.android.tweets.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tw.android.tweets.httpCall.bean.TweetsCommentsBean;
import com.tw.android.tweets.viewholder.base.BaseViewHolder;
import com.tw.android.tweets.viewholder.factory.TweetsCommentsHolderFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * tweetsCommentsAdapter.java
 * @author by morton_ws on 2017/12/1.
 */

public class TweetsCommentsAdapter extends RecyclerView.Adapter<BaseViewHolder<TweetsCommentsBean>> {

    private List<TweetsCommentsBean> mCommentsList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public TweetsCommentsAdapter(Context context) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    /**
     * according to different itemData position, get different ItemType
     * @param position position of itemData
     * @return ItemType
     */
    @Override
    public int getItemViewType(int position) {
        return TweetsCommentsHolderFactory.getItemType(mCommentsList.get(position));
    }

    /**
     * according different viewType, get create different ViewHolder
     * @param parent parent ViewGroup
     * @param viewType viewHolder according this viewType
     * @return different ViewHolder
     */
    @Override
    public BaseViewHolder<TweetsCommentsBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        return TweetsCommentsHolderFactory.getViewHolder(mContext, viewType, mLayoutInflater, parent);
    }

    /**
     * deal with viewHolder
     * @param holder baseViewHolder
     * @param position itemData Position
     */
    @Override
    public void onBindViewHolder(BaseViewHolder<TweetsCommentsBean> holder, int position) {
        holder.showTweets(mCommentsList.get(position));
    }

    /**
     * itemCount
     * @return comments ItemCount
     */
    @Override
    public int getItemCount() {
        return mCommentsList.size();
    }

    /**
     * add tweets comments
     * @param tweetsCommentsList tweets Comments List
     */
    public void addComments(List<TweetsCommentsBean> tweetsCommentsList) {
        mCommentsList.clear();
        mCommentsList.addAll(tweetsCommentsList);
        notifyDataSetChanged();
    }
}
