package com.wark.drama;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
                    adapter.addItem(first_Data,second_Data,addressdrama);
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

                    while(k<=72 && i <=85) {
                        second_Data = doc.select("span").eq(k).text().trim();//드라마 업뎃 시간
                        first_Data = doc.select("a").eq(i).text().trim();//드라마 제목
                        Elements href = doc.select("a").eq(i);
                        for(Element element : href){
                            addressdrama =href.attr("href").trim();
                        }
                        Message mag = handler.obtainMessage();
                        handler.sendMessage(mag);
                        //adapteritemset
                        k+=4;
                        i+=3;
                    }

                    Message mag_one = adapter_handler.obtainMessage();
                    adapter_handler.sendMessage(mag_one);
                    //setadapter
                    } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();//파싱

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            String a;
            String url = "com.android.chrome";
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
//                    Intent internet = new Intent(Intent.ACTION_VIEW);
//                    internet.setData(Uri.parse(a));
//                    internet.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    internet.setPackage(url);
//
//                    List<ResolveInfo> activitiesList = getPackageManager().queryIntentActivities(internet,-1);
//                    if(activitiesList.size() > 0) {
//                        startActivity(internet);
//                    }else{
//                        Intent playStoreIntent = new Intent(Intent.ACTION_VIEW);
//                        playStoreIntent.setData(Uri.parse("market://details?id="+url));
//                        playStoreIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(playStoreIntent);
//                    }

                    Intent intent = new Intent(MainActivity.this,Video.class);
                    intent.putExtra("video",a);
                    startActivity(intent);
                    finish();
                }
            };

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
                final Data data = (Data) adapter.getItem(position);
                    new Thread(){
                        public void run(){
                            Document item_doc = null;
                            try {

                                item_doc = org.jsoup.Jsoup.connect(data.url()).header("User-Agent", "Chrome/19.0.1.84.52").get();
                                Elements drama_data = item_doc.select("div").select("iframe").eq(1);

                                for(Element element : drama_data){
                                    a = drama_data.attr("src");
                                    Log.e("asdf",a);
                                }
                                Message mag = handler.obtainMessage();
                                handler.sendMessage(mag);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                Log.e("addressasdf", data.url());
                Toast.makeText(getApplicationContext(),data.getname(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
