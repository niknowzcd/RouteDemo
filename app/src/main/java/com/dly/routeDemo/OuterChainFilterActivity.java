package com.dly.routeDemo;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.dly.route_api.RouteDemo;

/**
 * 专门用来处理外部链接 主要通过 scheme来区分来源
 * Created by dly on 2018/7/25.
 */
public class OuterChainFilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri uri = getIntent().getData();
        if (uri != null) {
            RouteDemo.getInstance().open(uri.toString());
        }
    }
}
