package com.tw.android.tweets.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.tw.android.tweets.viewholder.factory.TweetsViewHolderFactory;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Tweets Singleton Manager java
 *
 * @author by morton_ws on 2017/12/1.
 */

public class TweetsManager {
    @SuppressLint("StaticFieldLeak")
    private static TweetsManager sInstance;
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private Context mApplicationContext;

    // universe manage handler reference, manage memory efficiently
    private List<WeakReference<Handler>> mWeakReferenceList = new ArrayList<>();
    private int mTweetsLoadScenes = TweetsViewHolderFactory.TweetsLoadScenes.TWEETS_SCENES_LOADING;
    private boolean isRequestUserInfo = false;

    private TweetsManager(Context context) {
        mApplicationContext = context.getApplicationContext();
    }


    public static TweetsManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (TweetsManager.class) {
                if (sInstance == null) {
                    sInstance = new TweetsManager(context);
                }
            }
        }
        return sInstance;
    }

    /**
     * register handler, unified manage handler msg send
     *
     * @param target target handler for registering
     */
    public void registerHandler(Handler target) {
        for (WeakReference<Handler> reference : mWeakReferenceList) {
            Handler handler = reference.get();
            if (target == handler) {
                return;
            }
        }
        mWeakReferenceList.add(new WeakReference<>(target));
    }

    /**
     * remove unused handler , then reduce memory usage
     *
     * @param handler which handler need to be removed
     */
    public void unregisterHandler(Handler handler) {
        int i = 0;
        while (i < mWeakReferenceList.size()) {
            Handler curHandler = mWeakReferenceList.get(i).get();
            if (handler == curHandler) {
                mWeakReferenceList.remove(mWeakReferenceList.get(i));
            } else {
                i++;
            }
        }
    }

    /**
     * send msg to all registered handler
     *
     * @param msg waiting for sending msg
     */
    public void sendMsg(Message msg) {
        for (WeakReference<Handler> handlerRef : mWeakReferenceList) {
            Handler handler = handlerRef.get();
            if (handler != null) {
                Message sendMsg = new Message();
                sendMsg.copyFrom(msg);
                handler.sendMessage(sendMsg);
            }
        }
    }

    /**
     * header userInfo ViewHolder according to {@link #isRequestUserInfo} judge whether need to load user detail info
     * when {@link #isRequestUserInfo} is true can load user detail info, otherwise will not
     * @return isRequestUserInfo detail true or false
     */
    public boolean isRequestUserInfo() {
        return isRequestUserInfo;
    }

    /**
     * after request user detail info should set true for {@link #isRequestUserInfo}
     * @param requestUserInfo whether requestUserInfo
     */
    public void setRequestUserInfo(@SuppressWarnings("SameParameterValue") boolean requestUserInfo) {
        isRequestUserInfo = requestUserInfo;
    }

    /**
     * get now tweets load scenes
     * @return load scenes value
     */
    public int getTweetsLoadScenes() {
        return mTweetsLoadScenes;
    }

    /**
     * set tweets load scenes, see {@link com.tw.android.tweets.viewholder.factory.TweetsViewHolderFactory.TweetsLoadScenes}
     * @param tweetsLoadScenes three load scenes value
     */
    public void setTweetsLoadScenes(int tweetsLoadScenes) {
        mTweetsLoadScenes = tweetsLoadScenes;
    }

    /**
     * Tweets Event Msg Type
     */
    public interface EventMsgType {
        /**
         * event refresh
         */
        int TWEETS_REFRESH = 0x1000;
        /**
         * event load more tweets
         */
        int TWEETS_LOADING_MORE = 0x1001;
        /**
         * event retry request after request failed
         */
        int TWEETS_REQUEST_RETRY = 0x1002;
    }

    /**
     * different delay time for handler
     */
    public interface HandlerDelayTime {
        /**
         * Just for See Better Result Effect
         */
        int TWEETS_LOAD_TIME_DELAY = 1500;
    }
}
