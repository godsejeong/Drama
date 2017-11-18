package com.wark.drama;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.wark.drama.Adapter.Adapter;
import com.wark.drama.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;

/**
 * Created by pc on 2017-11-19.
 */

public class Result extends AppCompatActivity{
    ListView listView;
    String address;
    String dramaaddress;
    String dramatext;
    String load_time;
    Boolean img_bl =true;
    String img_pasing;
    String image;
    Adapter adapter;
    int i=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        listView = (ListView) findViewById(R.id.reslut_list);
        adapter = new Adapter();
        Intent intent = getIntent();
        address = intent.getStringExtra("passurl");

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                adapter.addItem(dramatext,load_time,dramaaddress,image);
            }
        };

        final Handler adapter_handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                listView.setAdapter(adapter);
            }
        };

        new Thread(){
            public void run(){
                try {
                    Document doc =  Jsoup.connect(address).timeout(5000).get();
                    while(i<20) {
                        load_time = doc.select("time").eq(i).text().trim();
                        Elements href = doc.select("h3").select("a").eq(i);
                        Elements img = doc.select("img.entry-thumb").eq(i);
                        for(Element element : href) {
                            dramaaddress =href.attr("href").trim();
                            dramatext = href.attr("title").trim();
                        }
                        Log.e("drama_text",dramatext);

                        if(img_pasing.contains("https")){

                        }else {
                            img_bl=false;
                        }

                        Log.e("img_pasing",img_pasing);
                        if(img_bl) {
                            int data_a = img_pasing.indexOf("480w") + 4;
                            String img_string = img_pasing.substring(data_a - 80, data_a);
                            int data_b = img_string.indexOf("https");
                            int data_c = img_string.indexOf("480w");
                            image = img_string.substring(data_b,data_c);
                        }else{
                            image = "https://qooqootv.com/wp-content/themes/Newspaper/images/no-thumb/td_100x70.png";
                        }

                        Message mag = handler.obtainMessage();
                        handler.sendMessage(mag);

                       i++;
                    }

                    Message mag_one = adapter_handler.obtainMessage();
                    adapter_handler.sendMessage(mag_one);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}
