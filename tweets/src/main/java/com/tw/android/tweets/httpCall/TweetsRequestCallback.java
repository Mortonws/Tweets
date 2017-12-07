package com.tw.android.tweets.httpCall;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author by morton_ws on 2017/12/2.
 *
 * tweets request call, deal response, log json
 */

public abstract class TweetsRequestCallback<T> implements Callback<T> {
    private String TAG_TWEETS_REQUEST_CALLBACK = TweetsRequestCallback.class.getSimpleName();
    @Override
    final public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        if (!response.isSuccessful()) {
            String throwErrorMsg = String.format(Locale.getDefault(), "code:%s; msg:%s", response.code(), response.message());
            onFail(new Throwable(throwErrorMsg));
            return;
        }
        T t = response.body();
        if (t != null) {
            Gson gson = new Gson();
            String resJson = gson.toJson(t);
            System.out.println(TAG_TWEETS_REQUEST_CALLBACK + " : " + resJson);
//            Log.d(TAG_TWEETS_REQUEST_CALLBACK, resJson);// because junit not support load android resource
            onSuccess(t);
        } else {
            onFail(new Throwable("response json convert to Obj null"));
        }
    }

    @Override
    final public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        onFail(t);
    }

    /**
     * request success callback
     * @param t callback rsp obj
     */
    public abstract void onSuccess(@NonNull T t);

    /**
     * request failed callback
     * @param throwable error msg throw
     */
    public abstract void onFail(Throwable throwable);
}
