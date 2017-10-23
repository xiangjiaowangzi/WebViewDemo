package com.example.customweb;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.VideoView;

/**
 * Created by Lbin on 2017/10/23.
 */

public class CusWebChromeClient extends WebChromeClient{

    WebView mWebView ;

    public CusWebChromeClient(WebView webView) {
        mWebView = webView;
    }

    /**
     * 打印相关日志
     * */
    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        return super.onConsoleMessage(consoleMessage);
    }

    /**
     * 当document 的title变化时，会通知应用程序
     * */
    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
    }

    /**
     * 当前页面有个新的favicon时候，会回调这个函数。
     * */
    @Override
    public void onReceivedIcon(WebView view, Bitmap icon) {
        super.onReceivedIcon(view, icon);
    }

    /**
     * 通知应用程序当前网页加载的进度。
     * */
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
    }

    /**
     * 通知应用程序显示javascript alert对话框
     * */
    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return true;
    }

    /**
     * 通知应用程序提供confirm 对话框
     * */
    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        return super.onJsConfirm(view, url, message, result);
    }

    /**
     * webview请求得到focus 此时webview一般在后台
     * */
    @Override
    public void onRequestFocus(WebView view) {
        super.onRequestFocus(view);
    }

    /**
     *通知应用程序webview需要显示一个custom view，主要是用在视频全屏HTML5Video support
     * */
    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {
        super.onShowCustomView(view, callback);
        if (view == null) {
            return;
        }
//        if (mCustomView != null && callback != null) {
//            callback.onCustomViewHidden();
//            return;
//        }
//        try {
//            view.setKeepScreenOn(true);
//        } catch (SecurityException e) {
//            Logger.e("WebView is not allowed to keep the screen on");
//        }
//        Activity activity = AppManager.getAppManager().getCurrentActivity();
//        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        mOriginalOrientation = activity.getRequestedOrientation();
//        FrameLayout decor = (FrameLayout) activity.getWindow().getDecorView();
//        mFullscreenContainer = new FrameLayout(activity.getApplicationContext());
//        mFullscreenContainer.setBackgroundColor(ContextCompat.getColor(activity.getApplicationContext(), android.R.color.black));
//        mCustomView = view;
//        mFullscreenContainer.addView(mCustomView, COVER_SCREEN_PARAMS);
//        decor.addView(mFullscreenContainer, COVER_SCREEN_PARAMS);
//        setFullscreen(true, true);
////        mCurrentView.setVisibility(View.GONE);
//        if (view instanceof FrameLayout) {
//            if (((FrameLayout) view).getFocusedChild() instanceof VideoView) {
//                mVideoView = (VideoView) ((FrameLayout) view).getFocusedChild();
//                mVideoView.setOnErrorListener(new VideoCompletionListener());
//                mVideoView.setOnCompletionListener(new VideoCompletionListener());
//            }
//        }
//        mCustomViewCallback = callback;
    }

    /**
     * 退出视频
     * */
    @Override
    public void onHideCustomView() {
        super.onHideCustomView();
//        if (mCustomView == null || mCustomViewCallback == null) {
//            return;
//        }
//        Logger.d("onHideCustomView");
////        mCurrentView.setVisibility(View.VISIBLE);
//        try {
//            mCustomView.setKeepScreenOn(false);
//        } catch (SecurityException e) {
//            Logger.e("WebView is not allowed to keep the screen on");
//        }
//        setFullscreen(false, false);
//        Activity activity = AppManager.getAppManager().getCurrentActivity();
//        FrameLayout decor = (FrameLayout) activity.getWindow().getDecorView();
//        if (decor != null) {
//            decor.removeView(mFullscreenContainer);
//        }
//
//        if (API < Build.VERSION_CODES.KITKAT) {
//            try {
//                mCustomViewCallback.onCustomViewHidden();
//            } catch (Throwable ignored) {
//
//            }
//        }
//        mFullscreenContainer = null;
//        mCustomView = null;
//        if (mVideoView != null) {
//            mVideoView.setOnErrorListener(null);
//            mVideoView.setOnCompletionListener(null);
//            mVideoView = null;
//        }
//        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * This method sets whether or not the activity will display
     * in full-screen mode (i.e. the ActionBar will be hidden) and
     * whether or not immersive mode should be set. This is used to
     * set both parameters correctly as during a full-screen video,
     * both need to be set, but other-wise we leave it up to user
     * preference.
     *
     * @param enabled   true to enable full-screen, false otherwise
     * @param immersive true to enable immersive mode, false otherwise
     */
    private void setFullscreen(Activity activity , boolean enabled, boolean immersive) {
        Window window = activity.getWindow();
        View decor = window.getDecorView();
        if (enabled) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            if (immersive) {
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }

    private class VideoCompletionListener implements MediaPlayer.OnCompletionListener,
            MediaPlayer.OnErrorListener {

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            return false;
        }

        @Override
        public void onCompletion(MediaPlayer mp) {
            onHideCustomView();
        }

    }

}
