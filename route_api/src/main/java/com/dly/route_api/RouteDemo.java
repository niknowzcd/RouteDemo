package com.dly.route_api;

import android.app.Application;
import android.content.Intent;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by dly on 2018/6/23.
 */
public class RouteDemo {

    private static HashMap<String, Class> activityMap = new HashMap<>();
    private static Application mApplication;

    public static void init(Application application) {
        mApplication = application;
        try {
            //通过反射调用AutoCreateModuleActivityMap_app类的方法,并给activityMap赋值
            Class clazz = Class.forName("com.dly.routeDemo.AutoCreateModuleActivityMap_app");
            Method method = clazz.getMethod("initActivityMap", HashMap.class);
            method.invoke(null, activityMap);
            for (String key : activityMap.keySet()) {
                System.out.println("activityMap = " + activityMap.get(key));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void open(String url) {
        for (String key : activityMap.keySet()) {
            if (url.equals(key)) {
                Intent intent = new Intent(mApplication, activityMap.get(key));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                mApplication.startActivity(intent);
            }
        }
    }

}
