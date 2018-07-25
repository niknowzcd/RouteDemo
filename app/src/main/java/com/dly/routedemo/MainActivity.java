package com.dly.routedemo;

import android.content.Intent;
import android.net.Uri;
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
                RouteDemo.getInstance().build("route://test")
                        .withString("name", "张三")
                        .withInt("age", 15)
                        .open();
//                RouteDemo.getInstance().open("route://test?name=555");
                break;
            case R.id.text2:
                RouteDemo.getInstance().open("test2");
                break;
        }
    }
}
