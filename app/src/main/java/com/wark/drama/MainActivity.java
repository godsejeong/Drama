package com.wark.drama;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    Adapter adapter;
    String addressdrama = "http://www.marutv.com/drama/";
    Document doc;
    String first_Data;
    String second_Data;
    int k;
    int i;
    ArrayList<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        items = new ArrayList<String>();
        listView = (ListView) findViewById(R.id.listview);
        adapter = new Adapter();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
//                for (int k = 1; k <= items.size(); k++){
//                    Log.e("asdf",items.get(k));
                    adapter.addItem(first_Data,second_Data);
//                }
            }
        };


        final Handler adapter_handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                listView.setAdapter(adapter);
            }
        };



        new Thread() {
            public void run() {
                try {
                    i=34;
                    k=4;
                    doc = org.jsoup.Jsoup.connect(addressdrama).header("User-Agent", "Chrome/19.0.1.84.52").get();
                    //a = doc.select("a");
                    //34 -> 청춘시대
                    //37 -> 최강배달꾼
                    while(k<=72 && i <=85) {
                            second_Data = doc.select("span").eq(k).text().trim();
                            first_Data = doc.select("a").eq(i).text().trim();

                        Message mag = handler.obtainMessage();
                        handler.sendMessage(mag);
                        k+=4;
                        i+=3;
                    }
                    Message mag_one = adapter_handler.obtainMessage();
                    adapter_handler.sendMessage(mag_one);
                    } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();//파싱

    }

    public void add(){

    }

}
