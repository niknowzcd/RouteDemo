package com.dly.route_api;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import junit.framework.Assert;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by dly on 2018/6/23.
 */
public class RouteDemo {

    private static HashMap<String, Class> activityMap = new HashMap<>();
    private static Application mApplication;
    private static Bundle mBundle;

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
            if (checkUrlPath(url, key)) {
                Intent intent = new Intent(mApplication, activityMap.get(key));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent = parseParams(intent, url);
                mApplication.startActivity(intent);
            }
        }
    }

    private static Intent parseParams(Intent intent, String targetUrl) {
        Uri uri = Uri.parse(targetUrl);
        Set<String> queryParameterNames = uri.getQueryParameterNames();
        for (String queryParameterName : queryParameterNames) {
            intent.putExtra(queryParameterName, uri.getQueryParameter(queryParameterName));
        }
        return intent;
    }


    //Uri的标准格式 scheme、authority 二者是必须的
    private static boolean checkUrlPath(String targetUrl, String matchUrl) {
        Uri targetUri = Uri.parse(targetUrl);
        Uri matchUri = Uri.parse(matchUrl);

        Assert.assertNotNull(targetUri.getScheme());
        Assert.assertNotNull(targetUri.getHost());

//        TextUtils.equals(targetUri.getScheme(), matchUri.getScheme())

        if (targetUri.getScheme().equals(matchUri.getScheme()) && targetUri.getHost().equals(matchUri.getHost())) {
            return TextUtils.equals(targetUri.getPath(), matchUri.getPath());
        } else {
            return false;
        }
    }

}
