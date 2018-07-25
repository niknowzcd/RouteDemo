package com.dly.route_api;

import java.lang.reflect.Method;

/**
 * com.dly.routeDemo.TestActivity1_Autowired
 * Created by dly on 2018/7/25.
 */
public class InjectManager {

    public static void inject(Object object) {
        String suffix = "_Autowired";
        String targetClassName = object.getClass().getName() + suffix;

        try {
            Method method = Class.forName(targetClassName).getMethod("autowired", Object.class);
            method.invoke(null, object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
