package com.wark.drama.Video_extration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.wark.drama.R;

/**
 * Created by pc on 2017-09-09.
 */

public class Extration_three extends AppCompatActivity {
    WebView webView;
    private String CurrentUrl;
    String src, value;
    String Base;
    TextView text;
    String name;
    String address = "blob:http://streamango.com/0d046b59-0ded-422d-82ae-712411678077";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load);
        setContentView(R.layout.video);
        webView = (WebView) findViewById(R.id.video);

        Intent intent = getIntent();
        address = intent.getStringExtra("video");
        name = intent.getStringExtra("name");
        text = (TextView) findViewById(R.id.load_text);
        text = (TextView) findViewById(R.id.load_text);
        CurrentUrl = null;

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


//        Intent tic  = new Intent(Intent.ACTION_VIEW);
//        //Log.e("src",value);
//        tic.setDataAndType(Uri.parse("blob:https://streamango.com/4c4292ea-b435-4bd5-a9b9-269a453c027b"), "video/*");
//        startActivity(tic);

