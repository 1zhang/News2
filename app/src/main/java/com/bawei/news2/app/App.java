package com.bawei.news2.app;

import android.app.Application;

import org.xutils.x;

/**
 * Created by 设计风格 on 2017/8/30.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
