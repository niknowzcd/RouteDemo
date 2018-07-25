package com.dly.routeDemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.dly.route_annotation.Autowired;
import com.dly.route_annotation.Route;
import com.dly.route_api.RouteDemo;

/**
 * Created by dly on 2018/6/22.
 */
@Route("route://webView")
public class TestWebActivity extends AppCompatActivity {

    @Autowired
    public String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        RouteDemo.getInstance().inject(this);

        WebView webView = findViewById(R.id.web_view);
        webView.loadUrl(url);
    }
}
