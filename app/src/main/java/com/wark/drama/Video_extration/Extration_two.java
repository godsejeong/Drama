package com.wark.drama.Video_extration;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.wark.drama.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by pc on 2017-09-09.
 */

public class Extration_two extends AppCompatActivity {
    String address;
    String src, value;
    String Base;
    TextView text;
    String name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.load);
        Intent intent = getIntent();
        address = intent.getStringExtra("video");
        name = intent.getStringExtra("name");
        text = (TextView) findViewById(R.id.load_text);

        int b = name.indexOf("회")+2;
        String c = name.substring(0,b);
        Log.e("c",c);
        text.setText(c + "가 로딩중입니다.\n" + "잠시만 기다려주십시오...");

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                getIntent().putExtra("addressfirst",Base);
                setResult(RESULT_OK,getIntent());
            }
        };

        new Thread() {
            public void run() {
                try {
                    Document doc = Jsoup.connect(address).timeout(5000).get();
                    src = doc.select("script").eq(9).html();

                    Log.e("src",src);
//
//                    int a = par.indexOf("window.atob") + 13;
//                    int b = par.indexOf("label") - 6;
//                    value = par.substring(a, b);
//                    Log.e("c", value);
//                    Base = new String(Base64.decode(value, Base64.DEFAULT), "UTF-8");
                    Message mag_one = handler.obtainMessage();
                    handler.sendMessage(mag_one);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
