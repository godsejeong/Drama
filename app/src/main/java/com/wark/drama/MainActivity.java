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

import com.wark.drama.Adapter.Adapter;
import com.wark.drama.data.Data;

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
    String img_pasing;
    int k;
    int i;
    ArrayList<String> items;
    String image;
    Boolean img_bl =true;
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
                    adapter.addItem(first_Data,second_Data,addressdrama,image);
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
                    i=0;
                    //i=121;
                    k=0;
                    doc = org.jsoup.Jsoup.connect(addressdrama).timeout(5000).get();

                    while(k<=20 && i <20) {
                        second_Data = doc.select("time").eq(k).text().trim();//드라마 업뎃 시간
                        Elements href = doc.select("div").select("h3").select("a").eq(i);
                        Elements img = doc.select("img.entry-thumb").eq(i);
                        for(Element element : href){
                            addressdrama =href.attr("href").trim();
                            first_Data = href.attr("title").trim();
                          }

                        for(Element element : img){
                            img_pasing = img.attr("srcset").trim();

                        }

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
                            Log.e("image",image);
//                        int image_a = image.indexOf("300")+3;
//                        int image_b = image.indexOf("480")+4;
//                        drama_image = image.substring(image_b,image_a);
                      //  Log.e("drama_image",drama_image);


                        Message mag = handler.obtainMessage();
                        handler.sendMessage(mag);
                        //adapteritemset
                        k++;
                        i++;
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
                intent.putExtra("image",data.image());
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
                Intent intent = new Intent(this,Search.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
