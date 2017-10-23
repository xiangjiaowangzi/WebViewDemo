package com.example.administrator.myapplication;

import android.app.Activity;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Lbin on 2017/10/23.
 */

public class WindowManagerUtils {
    public static void fullScreen(Activity pActivity) {
        WindowManager.LayoutParams attrs = pActivity.getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        attrs.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        pActivity.getWindow().setAttributes(attrs);
        if (android.os.Build.VERSION.SDK_INT >= 14) {
            //noinspection all
            pActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
        }
    }

    public static void smallScreen(Activity pActivity) {
        WindowManager.LayoutParams attrs = pActivity.getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        pActivity.getWindow().setAttributes(attrs);
        if (android.os.Build.VERSION.SDK_INT >= 14) {
            //noinspection all
            pActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }
}
