package com.example.customweb;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.lang.reflect.Method;
import java.util.logging.Logger;

/**
 * Created by Lbin on 2017/10/23.
 */

public class WebViewUtil {


    @TargetApi(21)
    public static void setWebSettings(WebView webView) {
        if (webView == null) {
            return;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            webView.removeJavascriptInterface("searchBoxJavaBridge_");//android 4.2以下google默认添加的
            webView.removeJavascriptInterface("accessibility");//此漏洞需要用户启动系统设置中的第三方辅助服务,为了安全一并删除
            webView.removeJavascriptInterface("accessibilityTraversal");
        } else {
            WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);//如果是4.4加上debug
        }
        WebSettings setting = webView.getSettings();
        /**
         * 是否支持Javascript
         * */
        setting.setJavaScriptEnabled(true);
//        /**
//         * 支持插件
//         * */
//        setting.setPluginsEnabled(true);
        /**
         * 设置自适应屏幕，两者合用
         * */
        setting.setUseWideViewPort(true); //将图片调整到适合webview的大小
        setting.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        setting.setDomStorageEnabled(true);//应该设置为true，否则视频的缩略图会被放大
        /**
         * 缩放操作
         * */
        setting.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        setting.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        setting.setDisplayZoomControls(false); //隐藏原生的缩放控件

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        setPageCacheCapacity(setting);
        //支持通过JS打开新窗口
        setting.setJavaScriptCanOpenWindowsAutomatically(true);
        //设置可以访问文件
        setting.setAllowFileAccess(false);

        //允许通过file域url中的Javascript读取其他本地文件，在JELLY_BEAN及以后的版本中默认已被是禁止。
//        setting.setAllowFileAccessFromFileURLs(true);

        // 是否保存表单数据
        setting.setSaveFormData(false);
        // 控制页面的布局
        setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        // 不使用缓存
        setting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 编码格式
        setting.setDefaultTextEncodingName("UTF-8");
        setting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // 设置UA
        String userAgent = "userAgent";
        setting.setUserAgentString(userAgent);
    }

    public void setCookie(Context context, String cookies) {
        String DOMAIN = "baidu.com";
        try {
            CookieSyncManager manager = CookieSyncManager.createInstance(context);
            CookieManager cookieManager = CookieManager.getInstance();
            if (!TextUtils.isEmpty(cookies)) {
                String[] cookie = cookies.split(";");
                cookieManager.setCookie(DOMAIN, cookies);
            }
            manager.sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置缓存
     * */
    public void autoCache(WebView webView){
        WebSettings webSettings = webView.getSettings();
//        if (NetStatusUtil.isConnected(getApplicationContext())) {
//            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据。
//        } else {
//            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
//        }
        webSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
        webSettings.setDatabaseEnabled(true);   //开启 database storage API 功能
        webSettings.setAppCacheEnabled(true);//开启 Application Caches 功能
//        String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
//        webSettings.setAppCachePath(cacheDirPath); //设置  Application Caches 缓存目录
        //  每个 Application 只调用一次 WebSettings.setAppCachePath()，WebSettings.setAppCacheMaxSize()
    }

    /**
     * 这个函数是用来处理 当进行goBack的时候 使用前一个页面的缓存 避免每次都从新载入
     *
     * @param webSettings webView的settings
     */
    protected static void setPageCacheCapacity(WebSettings webSettings) {
        try {
            Class<?> c = Class.forName("android.webkit.WebSettingsClassic");

            Method tt = c.getMethod("setPageCacheCapacity", int.class);

            tt.invoke(webSettings, 5);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void wideViewPortSetting(WebView webView) {

        setWebSettings(webView);

        WebSettings webSettings = webView.getSettings();

        //设置此属性，可任意比例缩放
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        // 支持双指缩放
        webSettings.setBuiltInZoomControls(true);
    }

    /**
     * 调用系统浏览器下载
     * */
    public static void defaultDownload(final Context context , WebView webView, DownloadListener downloadListener) {
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                 {
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
                }
            }
        });
    }

    // 智能开启硬件加速
    public static void refreshHardwareAccelerationSetting(WebView _webView) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB
                && _webView.getHeight() < 4096
                && _webView.getWidth() < 4096) {
            _webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            _webView.setLayerType(View.LAYER_TYPE_NONE, null);
        }
    }

    // 关闭硬件加速
    public static void closeHardwareAcceleration(WebView _webView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            _webView.setLayerType(View.LAYER_TYPE_NONE, null);
        }
    }

    public static void releaseWebViewResource(@Nullable WebView webView) {
        if (webView != null) {
            webView.removeAllViews();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.setTag(null);
            webView.clearHistory();
            webView.destroy();
            webView = null;
        }
    }

    public static void onPageStarted(WebView _webView) {
        closeHardwareAcceleration(_webView);
    }

    public static void onPageFinished(WebView _webView) {
        refreshHardwareAccelerationSetting(_webView);
    }

    public static void onDestory(WebView _webView) {
        releaseWebViewResource(_webView);
    }
}
