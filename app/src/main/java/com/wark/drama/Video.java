package com.wark.drama;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by pc on 2017-09-03.
 */

public class Video extends AppCompatActivity{
    WebView webView;
    String address;
    private String CurrentUrl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);
        webView = (WebView) findViewById(R.id.video);

        Intent intent = getIntent();
        address = intent.getStringExtra("video");
        Log.e("intent", String.valueOf(Uri.parse(address)));
        CurrentUrl = null;
//                Intent tic  = new Intent(Intent.ACTION_VIEW);
//                tic.setDataAndType(Uri.parse(address), "video/*");
//                startActivity(tic);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl(address);
                webView.setWebViewClient(new WebviewClientClass()); // 이걸 안해주면 새창이 뜸
    }



    private class WebviewClientClass extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if(CurrentUrl != null && url !=null && url.equals(CurrentUrl)) {
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
        if((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
