package com.dly.routedemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.dly.route_annotation.Route;

/**
 * Created by dly on 2018/6/22.
 */
@Route("route://test")
public class TestActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        String extraName = getIntent().getStringExtra("name");
        TextView textView = findViewById(R.id.name);
        textView.setText(extraName);
    }
}
