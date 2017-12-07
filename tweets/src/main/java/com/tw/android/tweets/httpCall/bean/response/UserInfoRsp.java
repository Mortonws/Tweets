package com.tw.android.tweets.httpCall.bean.response;

import com.google.gson.annotations.SerializedName;

/**
 * UserInfo Rsp java
 * @author by morton_ws on 2017/11/29.
 */

public class UserInfoRsp {
    @SerializedName("profile-image")
    public String profileImage;
    public String avatar;
    public String nick;
    public String username;
}
