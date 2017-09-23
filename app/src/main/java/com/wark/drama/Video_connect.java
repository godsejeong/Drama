package com.wark.drama;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wark.drama.Video_extration.Extration_one;
import com.wark.drama.Video_extration.Extration_three;
import com.wark.drama.Video_extration.Extration_two;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by pc on 2017-09-07.
 */

public class Video_connect extends AppCompatActivity implements View.OnClickListener{
    Button one,two,three,four,five,six,two_two;
    String address,name;
    String result;
    TextView textView;
    ArrayList<String> link_item = new ArrayList<String>();
    int save_i;
    boolean bl=false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_connect);
        textView = (TextView) findViewById(R.id.connect_text);
        one = (Button) findViewById(R.id.btn_one);
        two = (Button) findViewById(R.id.btn_two);
        three = (Button) findViewById(R.id.btn_three);
        four = (Button) findViewById(R.id.btn_four);
        five = (Button) findViewById(R.id.btn_five);
        six = (Button) findViewById(R.id.btn_six);
        //<-버튼 생성

        Intent intent = getIntent();
        address = intent.getStringExtra("video");//받아온 값
        name = intent.getStringExtra("name");

        int b = name.indexOf("회")+2;
        result = name.substring(0,b);
        textView.setText(result);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                    //reload_two();

                for (int i = 0; i < link_item.size(); i++) {
                        if (link_item.get(i).contains("https://www.dailymotion.com")) {
                            link_item.set(i, String.valueOf(0));
                        }
                    }


                for (int i = 0; i < link_item.size(); i++) {
                    if(!link_item.get(0).contains("https://")){
                        one.setVisibility(View.GONE);
                    }
                    if(!link_item.get(1).contains("https://")){
                        two.setVisibility(View.GONE);
                    }
                    if(!link_item.get(2).contains("https://")){
                        three.setVisibility(View.GONE);
                    }
                    if(!link_item.get(3).contains("https://")){
                        four.setVisibility(View.GONE);
                    }
                    if(!link_item.get(4).contains("https://")){
                        five.setVisibility(View.GONE);
                    }
                    if(!link_item.get(5).contains("https://")){
                        six.setVisibility(View.GONE);
                    }
                }
            }
        };

        new Thread() {
            public void run() {
                try {
                    Elements drama_one;
                    Elements drama_two;
                    Elements drama_three;
                    Elements drama_four;
                    Elements drama_five;
                    Elements drama_six;
                    Document doc = Jsoup.connect(address).timeout(5000).get();

                    drama_one = doc.select("div").select("div").select("iframe").eq(0);
                    link_item.add(drama_one.attr("src"));
                    //1
                    drama_two = doc.select("div").select("div").select("iframe").eq(1);
                    link_item.add(drama_two.attr("src"));
                    //2
                    drama_three = doc.select("div").select("div").select("iframe").eq(2);
                    link_item.add(drama_three.attr("src"));
                    //3
                    drama_four = doc.select("div").select("div").select("iframe").eq(3);
                    link_item.add(drama_four.attr("src"));
                    //4
                    drama_five = doc.select("div").select("div").select("iframe").eq(4);
                    link_item.add(drama_five.attr("src"));
                    //5
                    drama_six = doc.select("div").select("div").select("iframe").eq(5);
                    link_item.add(drama_six.attr("src"));
                    //6 링크등록

                    Log.e("tset", String.valueOf(link_item.get(2).contains("https://")));
                    for (int i = 0; i < link_item.size(); i++) {
                        if(link_item.get(i).contains("https://")) {
                            Log.e("result", link_item.get(i));
                        }else{
                            link_item.set(i, String.valueOf(0));
                        }
                    }

                    Message mag_one = handler.obtainMessage();
                    handler.sendMessage(mag_one);

                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
//        get_address_first();
//        get_address_second();
//        get_address_third();
//        get_address_fourth();
//        get_address_fifth();
//        get_address_sixth();
//        if(s){
//            get_address_second_second();
//        }//버튼이 생성될때만 호출
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.search:
                Toast.makeText(getApplicationContext(), "검색하기", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Toast.makeText(getApplication(), "뒤로가기", Toast.LENGTH_SHORT).show();
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_one:
                for(int i=0;i<link_item.size();i++) {
                    Log.e("item", String.valueOf(link_item.get(i)));
                    if (link_item.get(i).contains("https://k-vid.info/")) {
                        bl=true;
                        Intent intent = new Intent(this, Extration_one.class);
                        intent.putExtra("video", link_item.get(i));
                        intent.putExtra("name", result);
                        startActivity(intent);
                    }if(bl){
                        Toast.makeText(getApplication(),"링크가 삭제되었거나 존재하지 않습니다.",Toast.LENGTH_SHORT).show();
                    }
                }//k-vid
                    break;
            case R.id.btn_two:
                for(int i=0;i<link_item.size();i++){
                    if(link_item.get(i).contains("https://estream.to/")){
                        bl=true;
                        Intent intent = new Intent(this, Extration_two.class);
                        intent.putExtra("video",link_item.get(i));
                        intent.putExtra("name",result);
                        startActivity(intent);
                    }if(bl){
                        Toast.makeText(getApplication(),"링크가 삭제되었거나 존재하지 않습니다.",Toast.LENGTH_SHORT).show();
                    }
                }//estream
                break;
            case R.id.btn_three:
                for(int i=0;i<link_item.size();i++){
                    if(link_item.get(i).contains("https://streamango.com")){
                        bl=true;
                        Intent intent = new Intent(this, Extration_three.class);
                        intent.putExtra("video",link_item.get(i));
                        intent.putExtra("name",result);
                        startActivity(intent);
                    }
                    if(bl){
                        Toast.makeText(getApplication(),"링크가 삭제되었거나 존재하지 않습니다.",Toast.LENGTH_SHORT).show();
                    }
                }//streamango
                break;
            case R.id.btn_four:
                for(int i=0;i<link_item.size();i++) {
                    if (link_item.get(i).contains("https://streamango.com")) {
                        Intent intent = new Intent(this, Extration_three.class);
                        intent.putExtra("video", link_item.get(i));
                        intent.putExtra("name", result);
                        startActivity(intent);
                        bl = true;
                    }
                    if (bl) {
                        Toast.makeText(getApplication(), "링크가 삭제되었거나 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btn_five:
                break;
            case R.id.btn_six:
                break;
        }
    }
}

