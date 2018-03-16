package com.coolpad.www.todaynews;

import android.app.Application;
import android.content.Context;

import com.coolpad.www.todaynews.util.ImageLoaderEngine;

/**
 * Created by xiongjianbo on 2018/3/7.
 */

public class TodayNewsApp extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        ImageLoaderEngine.INSTANCE.initImageLoader(mContext);
    }

    public static Context getContext() {
        return mContext;
    }
}
