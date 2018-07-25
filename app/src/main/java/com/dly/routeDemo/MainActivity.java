package com.dly.routeDemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dly.route_api.RouteDemo;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text1:
//                RouteDemo.getInstance().build("route://test")
//                        .withString("name", "张三")
//                        .withInt("age", 15)
//                        .open();
//
                // TODO: 2018/7/25 目前url携带参数形式 只能解析成字符串形式
                RouteDemo.getInstance().open("route://test?name=李四&age=20");
                break;
            case R.id.text2:
                RouteDemo.getInstance().build("route://webView")
                        .withString("url", "file:///android_asset/schame-test.html")
                        .open();
                break;
        }
    }
}
