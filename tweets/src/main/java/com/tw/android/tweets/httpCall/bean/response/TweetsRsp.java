package com.tw.android.tweets.httpCall.bean.response;

import com.google.gson.annotations.SerializedName;
import com.tw.android.tweets.httpCall.bean.SenderInfoBean;
import com.tw.android.tweets.httpCall.bean.TweetsCommentsBean;

import java.util.List;

/**
 * Tweets list data Rsp class
 * @author by morton_ws on 2017/11/29.
 */

public class TweetsRsp {

    public String content;
    public SenderInfoBean sender;
    public String error;
    @SerializedName("unknown error")
    public String mUnknownError;
    public List<ImagesBean> images;
    public List<TweetsCommentsBean> comments;

    public static class ImagesBean {
        public String url;
    }
}
