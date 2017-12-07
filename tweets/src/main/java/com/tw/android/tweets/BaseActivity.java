package com.tw.android.tweets;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author by morton_ws on 2017/12/1.
 *
 * BaseActivity for the whole Activity
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mContext = this;
        initView();
        initPages();
        initListener();
    }

    protected abstract void initView();
    protected abstract void initPages();
    protected abstract int getLayoutId();

    protected void initListener() {

    }
}
