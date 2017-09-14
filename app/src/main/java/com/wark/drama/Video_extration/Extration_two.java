package com.wark.drama.Video_extration;

import android.content.Intent;
import android.net.Uri;
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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by pc on 2017-09-09.
 */

public class Extration_two extends AppCompatActivity {
    String address;
    String src, value;
    TextView text;
    String name;
    int indexone;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.load);
        Intent intent = getIntent();
        address = intent.getStringExtra("video");
        name = intent.getStringExtra("name");
        text = (TextView) findViewById(R.id.load_text);
        final ArrayList<Integer> link = new ArrayList<>();
        text.setText(name + "가 로딩중입니다.\n" + "잠시만 기다려주십시오...");

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Intent tic  = new Intent(Intent.ACTION_VIEW);
                Log.e("src",value);
                tic.setDataAndType(Uri.parse(value),"video/*");
                startActivity(tic);
                finish();
            }
        };

        Intent tic  = new Intent(Intent.ACTION_VIEW);
        //Log.e("src",value);
        tic.setDataAndType(Uri.parse("https://edg11.escdn.co/jg6ntvqulztu7m7cy3sfmpcm2665sr6py553jzxhgq7ij5355u3r6fke5uya/v.mp4"),"video/*");
        startActivity(tic);
        finish();


        new Thread() {
            public void run() {
                try {
                    Document doc = Jsoup.connect(address).timeout(5000).get();
                    Elements uri_address  = doc.select("video").select("source").eq(1);

                    for(Element element : uri_address){
                        value = uri_address.attr("src");
                        Log.e("value",value);
                    }


                    Message mag_one = handler.obtainMessage();
                    handler.sendMessage(mag_one);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
