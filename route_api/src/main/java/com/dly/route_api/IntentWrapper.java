package com.dly.route_api;

import android.os.Bundle;

/**
 * Created by dly on 2018/6/26.
 */
public class IntentWrapper {

    private Bundle mBundle;
    private String originalUrl;
    private RouteDemo routeDemo;

    private volatile static IntentWrapper instance = null;

    public static IntentWrapper getInstance() {
        if (instance == null) {
            synchronized (IntentWrapper.class) {
                if (instance == null) {
                    instance = new IntentWrapper();
                }
            }
        }
        return instance;
    }

    public IntentWrapper build(RouteDemo routeDemo, String url) {
        this.routeDemo = routeDemo;
        this.originalUrl = url;
        mBundle = new Bundle();
        return this;
    }

    public IntentWrapper withString(String key, String value) {
        mBundle.putString(key, value);
        return this;
    }

    public IntentWrapper withInt(String key, int value) {
        mBundle.putInt(key, value);
        return this;
    }

    public void open() {
        routeDemo.open(originalUrl, mBundle);
    }

}
