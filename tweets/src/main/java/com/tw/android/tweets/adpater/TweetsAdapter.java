package com.tw.android.tweets.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tw.android.tweets.httpCall.bean.response.TweetsRsp;
import com.tw.android.tweets.utils.TweetsManager;
import com.tw.android.tweets.viewholder.base.BaseViewHolder;
import com.tw.android.tweets.viewholder.factory.TweetsViewHolderFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * TweetsAdapter.java
 * @author by morton_ws on 2017/12/1.
 */

public class TweetsAdapter extends RecyclerView.Adapter<BaseViewHolder<TweetsRsp>> {
    private Context mContext;
    private LayoutInflater mInflater;
    @SuppressWarnings("FieldCanBeLocal")
    private TweetsRsp mUserProfile;
    private List<TweetsRsp> mTweetsList = new ArrayList<>();

    public TweetsAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mUserProfile = new TweetsRsp();
        mTweetsList.add(mUserProfile);
    }

    /**
     * according to different position itemData, return different itemType
     * @param position position of different item
     * @return itemType
     */
    @Override
    public int getItemViewType(int position) {
        int itemViewType;
        if (position == 0) {
            itemViewType = TweetsViewHolderFactory.TweetsHolderType.TYPE_TWEETS_HEADER_USER_INFO;
        } else if (getItemCount() - 1 == position) {
            itemViewType = TweetsViewHolderFactory.TweetsHolderType.TYPE_TWEETS_LOAD;
        } else {
            itemViewType = TweetsViewHolderFactory.getItemType(mTweetsList.get(position));
        }
        return itemViewType;
    }

    /**
     * according to different viewType create differentViewHolder
     * @param parent parenet viewGroup
     * @param viewType different itemViewType
     * @return different ViewHolder
     */
    @Override
    public BaseViewHolder<TweetsRsp> onCreateViewHolder(ViewGroup parent, int viewType) {
        return TweetsViewHolderFactory.getViewHolder(mContext, viewType, parent, mInflater);
    }

    /**
     * deal with TweetsViewHolder
     * @param holder different viewHolder
     * @param position different item position
     */
    @Override
    public void onBindViewHolder(BaseViewHolder<TweetsRsp> holder, int position) {
        TweetsRsp tweets = null;
        if (position < getItemCount() - 1) {
            tweets = mTweetsList.get(position);
        }
        holder.showTweets(tweets);
    }

    /**
     * itemCount, the 1 is loading view
     * @return itemCount
     */
    @Override
    public int getItemCount() {
        return mTweetsList.size() + 1;
    }

    /**
     * add tweets header
     * @param tweets this tweets contains userInfo, the obj is UserInfoBean
     */
    public void addUserProfile(TweetsRsp tweets) {
        mTweetsList.set(0, tweets);
        notifyDataSetChanged();
    }

    /**
     * add tweetsListData
     * @param tweetsList this tweetsList data need added
     */
    public void addTweets(List<TweetsRsp> tweetsList) {
        mTweetsList.addAll(tweetsList);
        notifyDataSetChanged();
    }

    /**
     * clear all tweetsList Data ,but except the header first userInfo
     */
    public void clearTweets() {
        if (mTweetsList.size() > 1) {
            List<TweetsRsp> tempTweetsList = new ArrayList<>();
            tempTweetsList.addAll(mTweetsList.subList(1, mTweetsList.size() - 1));
            mTweetsList.removeAll(tempTweetsList);
        }
        notifyDataSetChanged();
    }

    /**
     * load scenes changed, show different view,
     * @param loadScenes 1.loading more; 2.load empty; 3.load failed
     */
    public void loadScenesChange(int loadScenes) {
        TweetsManager.getInstance(mContext).setTweetsLoadScenes(loadScenes);
        notifyDataSetChanged();
    }
}
