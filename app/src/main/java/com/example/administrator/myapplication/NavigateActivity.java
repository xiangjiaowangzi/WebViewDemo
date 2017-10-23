package com.example.administrator.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Lbin on 2017/10/23.
 */

public class NavigateActivity extends AppCompatActivity {

    private android.widget.Button VideoEnabledWebView;
    private android.widget.Button JsInjectVideoEnabledWebView;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigate_activity_layout);
        this.JsInjectVideoEnabledWebView = (Button) findViewById(R.id.JsInjectVideoEnabledWebView);
        this.VideoEnabledWebView = (Button) findViewById(R.id.VideoEnabledWebView);

        VideoEnabledWebView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                start(MainActivity.class);
            }
        });

        JsInjectVideoEnabledWebView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                start(JsInjectActivity.class);
            }
        });
    }

    private void start(Class pClass) {
        Intent lIntent = new Intent(this, pClass);
        startActivity(lIntent);
    }

}
