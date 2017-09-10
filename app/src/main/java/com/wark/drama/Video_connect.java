package com.wark.drama;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wark.drama.Video_extration.Extration_one;
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
    boolean s;
    ArrayList<String> link_item = new ArrayList<String>();
    ArrayList<String> addresss_item = new ArrayList<String>();
    int save_i;
    boolean bl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_connect);
        one = (Button) findViewById(R.id.btn_one);
        two = (Button) findViewById(R.id.btn_two);
        two_two = (Button) findViewById(R.id.btn_two_two);
        three = (Button) findViewById(R.id.btn_three);
        four = (Button) findViewById(R.id.btn_four);
        five = (Button) findViewById(R.id.btn_five);
        six = (Button) findViewById(R.id.btn_six);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //<-버튼 생성

        Intent intent = getIntent();
        address = intent.getStringExtra("video");//받아온 값
        name = intent.getStringExtra("name");
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        two_two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (!bl) {
                    s = true;
                    two.setText("링크2");
                    two_two.setVisibility(View.GONE);//데일리모션링크가 아닐시 버튼을 지움
                } else {

                    two_two.setVisibility(View.VISIBLE);
                    Log.e("사이즈", String.valueOf(link_item.size()));
                    for (int i = save_i; i <link_item.size(); i++) {
                        Log.e("i", String.valueOf(i));
                        link_item.set(i + 1, link_item.get(i + 2));
                        //데일리모션1-2링크에 다른링크를 삽입
                        link_item.set(i+2, String.valueOf(0));
                        Log.e("number", String.valueOf(i));
                        Log.e("change_link", link_item.get(i+1));
                        if(i+2>=5){
                            break;
                        }
                    }//값을 땡김

                    //reload_two();
                }
                    for (int i = 0; i < link_item.size(); i++) {
                        if (!link_item.get(1).contains("https://")) {
                            two.setVisibility(View.GONE);
                            three.setVisibility(View.GONE);
                            four.setVisibility(View.GONE);
                            five.setVisibility(View.GONE);
                            six.setVisibility(View.GONE);
                        } else if (!link_item.get(2).contains("https://")) {
                            three.setVisibility(View.GONE);
                            four.setVisibility(View.GONE);
                            five.setVisibility(View.GONE);
                            six.setVisibility(View.GONE);
                        } else if (!link_item.get(3).contains("https://")) {
                            four.setVisibility(View.GONE);
                            five.setVisibility(View.GONE);
                            six.setVisibility(View.GONE);
                        } else if (!link_item.get(4).contains("https://")) {
                            five.setVisibility(View.GONE);
                            six.setVisibility(View.GONE);
                        } else if (!link_item.get(5).contains("https://")) {
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
                    for (int i = 0; i < link_item.size(); i++) {
                        if (link_item.get(i).contains("https://www.dailymotion.com/")) {
                            save_i = i;
                            Elements test = doc.select("iframe").eq(i + 1);
                            String drama_test = test.attr("src");
                            bl = true;
                            Log.e("save_number", String.valueOf(save_i));
                            Log.e("stringd", String.valueOf(bl));
                           }
                    }
                    //데일리모션링크1-2가 있는지 판별
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
        get_address_second();
//        get_address_third();
//        get_address_fourth();
//        get_address_fifth();
//        get_address_sixth();
        if(s){
            get_address_second_second();
        }//버튼이 생성될때만 호출
    }

    public void get_address_first(){
        for(int i=0;i<link_item.size();i++){
            Log.e("item", String.valueOf(link_item.get(i)));
            if(link_item.get(i).contains("https://k-vid.info/")) {
                Intent intent = new Intent(this, Extration_one.class);
                intent.putExtra("video",link_item.get(i));
                intent.putExtra("name",name);
                startActivityForResult(intent,1);
            }else{
                addresss_item.add(String.valueOf(0));
            }
        }
    }

    public void get_address_second(){
        for(int i=0;i<link_item.size();i++){
            Log.e("item", String.valueOf(link_item.get(i)));
            if(link_item.get(i).contains("https://www.dailymotion.com/")) {
                Intent intent = new Intent(this, Extration_two.class);
                intent.putExtra("video",link_item.get(i));
                intent.putExtra("name",name);
                startActivityForResult(intent,1);
            }else{
                addresss_item.add(String.valueOf(0));
            }
        }
    }

    public void get_address_third(){
        for(int i=0;i<link_item.size();i++){
            Log.e("item", String.valueOf(link_item.get(i)));
            if(link_item.get(i).contains("https://k-vid.info/")) {
                Intent intent = new Intent(this, Extration_one.class);
                intent.putExtra("video",link_item.get(i));
                intent.putExtra("name",name);
                startActivityForResult(intent,1);
            }else{
                addresss_item.add(String.valueOf(0));
            }
        }
    }

    public void get_address_fourth(){
        for(int i=0;i<link_item.size();i++){
            Log.e("item", String.valueOf(link_item.get(i)));
            if(link_item.get(i).contains("https://k-vid.info/")) {
                Intent intent = new Intent(this, Extration_one.class);
                intent.putExtra("video",link_item.get(i));
                intent.putExtra("name",name);
                startActivityForResult(intent,1);
            }else{
                addresss_item.add(String.valueOf(0));
            }
        }
    }

    public void get_address_fifth(){
        for(int i=0;i<link_item.size();i++){
            Log.e("item", String.valueOf(link_item.get(i)));
            if(link_item.get(i).contains("https://k-vid.info/")) {
                Intent intent = new Intent(this, Extration_one.class);
                intent.putExtra("video",link_item.get(i));
                intent.putExtra("name",name);
                startActivityForResult(intent,1);
            }else{
                addresss_item.add(String.valueOf(0));
            }
        }
    }

    public void get_address_sixth(){
        for(int i=0;i<link_item.size();i++){
            Log.e("item", String.valueOf(link_item.get(i)));
            if(link_item.get(i).contains("https://k-vid.info/")) {
                Intent intent = new Intent(this, Extration_one.class);
                intent.putExtra("video",link_item.get(i));
                intent.putExtra("name",name);
                startActivityForResult(intent,1);
            }else{
                addresss_item.add(String.valueOf(0));
            }
        }
    }
    public void get_address_second_second(){
        for(int i=0;i<link_item.size();i++){
            Log.e("item", String.valueOf(link_item.get(i)));
            if(link_item.get(i).contains("https://k-vid.info/")) {
                Intent intent = new Intent(this, Extration_one.class);
                intent.putExtra("video",link_item.get(i));
                intent.putExtra("name",name);
                startActivityForResult(intent,1);
            }else{
                addresss_item.add(String.valueOf(0));
            }
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RESULT_OK){
            if(requestCode == 1){
                addresss_item.add(data.getStringExtra("addressfirst"));
                Log.e("링크",addresss_item.get(0));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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
        Toast.makeText(getApplication(), "saasdfsdasf", Toast.LENGTH_SHORT).show();
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_one:
                break;
            case R.id.btn_two:
//                for(int i=0;i<link_item.size();i++){
//                    if(link_item.get(i).contains("https://www.dailymotion.com/")) {
//                        Intent intent = new Intent(this, Extration_one.class);
//                        intent.putExtra("video",link_item.get(i));
//                        intent.putExtra("name",name);
//                        startActivity(intent);
//                    }else{
//                        Toast.makeText(getApplicationContext(),"링크가 삭제되었거나 존재하지 않습니다.",Toast.LENGTH_SHORT).show();
//                    }
//                }
                break;
            case R.id.btn_two_two:
                break;
            case R.id.btn_three:
                break;
            case R.id.btn_four:
                break;
            case R.id.btn_five:
                break;
            case R.id.btn_six:
                break;
        }
    }
}

