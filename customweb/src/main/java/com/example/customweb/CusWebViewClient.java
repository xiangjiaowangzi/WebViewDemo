package com.example.customweb;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.URLUtil;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.InputStream;

/**
 * Created by Lbin on 2017/10/23.
 */

public class CusWebViewClient extends WebViewClient {

    public CusWebViewClient() {
    }

    /**
     * 页面开始调用
     */
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (view == null) {
            return;
        }
        //设定加载开始的操作
    }

    /**
     * 页面结束调用
     */
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        //设定加载结束的操作
    }

    /**
     * 加载url调用
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (URLUtil.isValidUrl(url)) {
            return false;
        }
        return true;//默认当前页面打开
    }

    @TargetApi(23)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return super.shouldOverrideUrlLoading(view, request);
    }

    /**
     * 页面错误回调
     */
    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

    }

    @TargetApi(23)
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
    }

    /**
     * 在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次
     */
    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
        //设定加载资源的操作
    }

    /**
     * 处理https请求
     */
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();    //表示等待证书响应
        // handler.cancel();      //表示挂起连接，为默认方式
        // handler.handleMessage(null);    //可做其他处理
    }

    /**
     * 拦截所有的url请求，若返回非空，则不再进行网络资源请求，而是使用返回的资源数据
     */
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//        // 步骤1:判断拦截资源的条件，即判断url里的图片资源的文件名
//        // 此处图片的url为:http://s.ip-cdn.com/img/logo.gif
//        // 图片的资源文件名为:logo.gif
//        if (request.getUrl().toString().contains("logo.gif")) {
//            InputStream is = null;
//            // 步骤2:创建一个输入流
//            try {
//                is = getApplicationContext().getAssets().open("images/error.png");
//                // 步骤3:打开需要替换的资源(存放在assets文件夹里)
//                // 在app/src/main下创建一个assets文件夹
//                // assets文件夹里再创建一个images文件夹,放一个error.png的图片
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            //步骤4:替换资源
//
//            WebResourceResponse response = new WebResourceResponse("image/png",
//                    "utf-8", is);
//            // 参数1：http请求里该图片的Content-Type,此处图片为image/png
//            // 参数2：编码类型
//            // 参数3：存放着替换资源的输入流（上面创建的那个）
//
//            return response;
//        }
        return super.shouldInterceptRequest(view, request);
    }
}
