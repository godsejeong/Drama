package com.wark.drama;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
    String addressdrama = "https://qooqootv.com/category/%EB%93%9C%EB%9D%BC%EB%A7%88/";
    Document doc;
    String first_Data;
    String second_Data;
    int k;
    int i;
    int asdfasdfasdf;
    ArrayList<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new Adapter();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        items = new ArrayList<String>();
        listView = (ListView) findViewById(R.id.listview);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }//스테이터스바 투명하게 하기

        // 툴바를 스테이터스바 밑으로 가게하기
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);


        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                    adapter.addItem(first_Data,second_Data,addressdrama);
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
                    i=119;
                    k=0;
                    doc = org.jsoup.Jsoup.connect(addressdrama).timeout(10000).get();

                    while(k<=25 && i <=158) {
                        second_Data = doc.select("time").eq(k).text().trim();//드라마 업뎃 시간
                        Elements href = doc.select("a").eq(i);
                        for(Element element : href){
                            addressdrama =href.attr("href").trim();
                            first_Data = href.attr("title").trim();
                        }
                        Message mag = handler.obtainMessage();
                        handler.sendMessage(mag);
                        //adapteritemset
                        k++;
                        i+=2;
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

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
                final Data data = (Data) adapter.getItem(position);

                Intent intent = new Intent(MainActivity.this,Video_connect.class);
                intent.putExtra("video",data.url());
                intent.putExtra("name",data.getname());
                startActivity(intent);//선택한 드라마 URL넘기기
                Log.e("addressasdf", data.url());
                Toast.makeText(getApplicationContext(),data.getname(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.search:
                Toast.makeText(getApplicationContext(),"검색하기",Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
