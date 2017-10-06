package com.wark.drama;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;



public class Video extends MainActivity{
    WebView webView;
    String address;
    private String CurrentUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);
        webView = (WebView) findViewById(R.id.video);

        Intent intent = getIntent();
        address = intent.getStringExtra("video");//받아온 값


        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(address);
        webView.setWebViewClient(new WebviewClientClass()); // 이걸 안해주면 새창이 뜸
    }


    private class WebviewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (CurrentUrl != null && url != null && url.equals(CurrentUrl)) {
                webView.goBack();
            } else {
                view.loadUrl(url);
                CurrentUrl = url;
            }
            return true;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}