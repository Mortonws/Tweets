package com.tw.android.tweets.utils;

import android.content.Context;

/**
 * BaseDensityUtil.java
 * @author morton_ws on 2017/12/4.
 */
public class BaseDensityUtil {

    /**
     * transform dip value to px value
     * @param context context for resource
     * @param dipValue dip source value
     * @return result px value
     */
    public static int dip2px(Context context, float dipValue) {
        float scale = context.getResources()
                .getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}