package com.dly.routeDemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.dly.route_annotation.Autowired;
import com.dly.route_annotation.Route;
import com.dly.route_api.RouteDemo;

/**
 * Created by dly on 2018/6/22.
 */
@Route("route://test")
public class TestActivity extends AppCompatActivity {

    @Autowired
    public String name = "123";

    @Autowired
    public int age;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        RouteDemo.getInstance().inject(this);
        TextView textView = findViewById(R.id.name);
        textView.setText("name = " + name + "  age = " + age);
    }
}
