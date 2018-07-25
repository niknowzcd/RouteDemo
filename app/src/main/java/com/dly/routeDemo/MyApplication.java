package com.dly.routeDemo;

import android.app.Application;

import com.dly.route_api.RouteDemo;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RouteDemo.getInstance().init(this);
    }
}
