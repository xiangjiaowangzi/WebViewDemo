package com.example.customweb;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by Lbin on 2017/10/23.
 */

public class CusWebView extends WebView {

    private static final String TAG = "CusWebView";

    public CusWebView(Context context) {
        super(context);
        init();
    }

    public CusWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CusWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public CusWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {

    }

    public void create(){
        resume();
    }

    public void resume() {
        onResume();
        resumeTimers();
    }

    public void pause() {
        onPause();
        pauseTimers();
    }

    public void destroy(){
        if (getParent() != null) {
            ((ViewGroup) getParent()).removeView(this);
        }
        removeAllViews();
        setWebChromeClient(null);
        setWebViewClient(null);
        setOnCreateContextMenuListener(null);
        super.destroy();
    }


    public synchronized void find(String text) {
        if (Build.VERSION.SDK_INT >= 16) {
            findAllAsync(text);
        } else {
            findAll(text);
        }
    }

    public void loadJavascript(String js) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            evaluateJavascript(js, null);
        } else {
            loadUrl("javascript:" + js);
        }
    }

    public synchronized void freeWebViewMemory() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            freeMemory();
        }
    }

    @Override
    public ActionMode startActionMode(ActionMode.Callback callback) {
        return super.startActionMode(new CustomCallback(getContext(), callback));
    }

    public static class CustomCallback implements ActionMode.Callback {
        private ActionMode.Callback callback;

        public CustomCallback(Context context, ActionMode.Callback callback) {
            this.callback = callback;
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return callback.onCreateActionMode(mode, menu);
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            // 定制自己的菜单模式;
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return callback.onActionItemClicked(mode, item);
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            callback.onDestroyActionMode(mode);
        }
    }
}
