package com.tw.android.tweets.httpCall.bean;

import com.google.gson.annotations.Expose;

/**
 * senderInfo for tweetsList each item data
 * @author by morton_ws on 2017/11/30.
 */

public class SenderInfoBean {
    public String username;
    public String nick;
    public String avatar;

    @Expose(deserialize = false)
    public String profile;
}
