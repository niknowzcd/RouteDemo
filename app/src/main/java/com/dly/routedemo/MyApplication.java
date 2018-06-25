package com.dly.routedemo;

import android.app.Application;

import com.dly.route_annotation.Route;
import com.dly.route_api.RouteDemo;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RouteDemo.init(this);
    }
}
