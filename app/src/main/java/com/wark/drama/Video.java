package com.wark.drama;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by pc on 2017-09-03.
 */

public class Video extends MainActivity{
    WebView webView;
    String address;
    private String CurrentUrl;
    String src;
    String value;
    String Base;
    Document doc;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);
        webView = (WebView) findViewById(R.id.video);

        Intent intent = getIntent();
        address = intent.getStringExtra("video");
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {

                Intent tic  = new Intent(Intent.ACTION_VIEW);
                Log.e("src",value);
                Uri uri = Uri.parse(Base);
                tic.setDataAndType(uri, "video/*");
                startActivity(tic);
            }
        };

        new Thread(){
            public void run(){
                try {
                    doc = Jsoup.connect(address).timeout(5000).get();
                    src = doc.select("div").select("script").html();

                    String par = src.replace("jwplayer(\"myElement\").setup(","").replace(");\n" + "\t\tjwplayer().onError(function(){\n" + "        //jwplayer().load({file:\"k-vid.mp4\", image:\"/images/change.png\"});\n" + "      \tjwplayer().stop();\n" + "    \t});","");
                    Log.e("src",par);

                    int a = par.indexOf("window.atob") + 13;
                    int b = par.indexOf("label" ) - 6;
                     value = par.substring(a,b);
                    Log.e("c",value);
                    Base = new String(Base64.decode(value,Base64.DEFAULT),"UTF-8");
                    Log.e("base",Base);

                    Message mag = handler.obtainMessage();
                    handler.sendMessage(mag);
                    } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();



    }

    private class WebviewClientClass extends WebViewClient {
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
