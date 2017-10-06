package com.wark.drama;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wark.drama.Video_extration.Extration_four;
import com.wark.drama.Video_extration.Extration_one;
import com.wark.drama.Video_extration.Extration_two;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;



public class Video_connect extends AppCompatActivity implements View.OnClickListener{
    Button one,two,three,five;
    String address,name;
    String result;
    TextView textView;
    ImageView image;
    String image_data;
    Drawable img_drawable;
    ArrayList<String> link_item = new ArrayList<String>();
    boolean bl=false;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_connect);

        Toolbar toolbar = (Toolbar) findViewById(R.id.connect_toolbar);
        setSupportActionBar(toolbar);

        image = (ImageView) findViewById(R.id.image);
        textView = (TextView) findViewById(R.id.connect_text);
        one = (Button) findViewById(R.id.btn_one);
        two = (Button) findViewById(R.id.btn_two);
        three = (Button) findViewById(R.id.btn_three);
        five = (Button) findViewById(R.id.btn_five);
        //<-버튼 생성

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//뒤로가기 버튼

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }//스테이터스바 투명하게 하기)

        // 툴바를 스테이터스바 밑으로 가게하기
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);

        Intent intent = getIntent();
        address = intent.getStringExtra("video");//받아온 값
        name = intent.getStringExtra("name");
        image_data = intent.getStringExtra("image");
        int b = name.indexOf("회")+2;
        result = name.substring(0,b);
        textView.setText(result);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        five.setOnClickListener(this);



        Image_load img_load = new Image_load(image_data);
        img_load.start();//쓰레드 시작
        try {
            img_load.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        img_drawable = img_load.getResult();
        image.setImageDrawable(img_drawable);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {


//                for (int i = 0; i < link_item.size(); i++) {
//                    if(!link_item.get(0).contains("https://")){
//                        one.setVisibility(View.GONE);
//                    }
//                    if(!link_item.get(1).contains("https://")){
//                        two.setVisibility(View.GONE);
//                    }
//                    if(!link_item.get(2).contains("https://")){
//                        three.setVisibility(View.GONE);
//                    }
//                    if(!link_item.get(3).contains("https://")){
//                        four.setVisibility(View.GONE);
//                    }
//                    if(!link_item.get(4).contains("https://")){
//                        five.setVisibility(View.GONE);
//                    }
//                    if(!link_item.get(5).contains("https://")){
//                        six.setVisibility(View.GONE);
//                    }
//                }
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
//버튼이 생성될때만 호출
    }    @Override
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
            case R.id.home:
                onSupportNavigateUp();
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
        switch (v.getId()) {
            case R.id.btn_one:
                for (int i = 0; i < link_item.size(); i++) {
                    Log.e("item", String.valueOf(link_item.get(i)));
                    if (link_item.get(i).contains("https://k-vid.info/")) {
                        bl = true;
                        Extration_one one = new Extration_one(link_item.get(i));
                        one.start();//쓰레드 시작
                        try {
                            one.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }//쓰레드에 들어간다

                        Log.e("data", one.getResult());//그 속에서 지정한 값을 return받음

                        video_start(one.getResult());
                        break;
                    }
                }
                if (!bl) {
                    Toast.makeText(getApplication(), "링크가 삭제되었거나 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                }//k-vid
                bl = false;
                break;
            case R.id.btn_two:
                for (int i = 0; i < link_item.size(); i++) {
                    if (link_item.get(i).contains("https://estream.to/")) {
                        bl = true;
                        Extration_two two = new Extration_two(link_item.get(i));
                        two.start();//쓰레드 시작
                        try {
                            two.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }//쓰레드에 들어간다

                        Log.e("data", two.getResult());//그 속에서 지정한 값을 return받음

                        video_start(two.getResult());
                        break;

                    }
                }
                if (!bl) {
                    Toast.makeText(getApplication(), "링크가 삭제되었거나 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                }//estream
                bl = false;
                break;
            case R.id.btn_three:
                //arraylist가 로드되기 까지 기다려준다 //로딩창 셋팅ㄱㄱ
                for (int i = 0; i < link_item.size(); i++) {
                    if (link_item.get(i).contains("dailymotion")) {
                        bl = true;
                        Log.e("12345667", "데일리모션링크가 존재합니다.");
                        Log.e("link_i", String.valueOf(i));
                        Intent intent = new Intent(this, Popup.class);
                        intent.putExtra("video", link_item.get(i));

                        if (link_item.get(i + 1).contains("https://www.dailymotion.com/")) {
                            intent.putExtra("video_two", link_item.get(i + 1));
                            Log.e("1번들어감", "1번들어감");
                        } else {
                            intent.putExtra("video_two", "0");
                            Log.e("2번들어감", "2번들어감");
                        }

                        startActivity(intent);
                        break;
                    }
                }

                if (!bl) {
                    Toast.makeText(getApplication(), "링크가 삭제되었거나 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                }//dailymotion
                bl = false;
                break;

            case R.id.btn_five:
                for (int i = 0; i < link_item.size(); i++) {
                    if (link_item.get(i).contains("ggvid.net")) {
                        bl = true;
                        Extration_four four = new Extration_four(link_item.get(i));
                        four.start();//쓰레드 시작
                        try {
                            four.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }//쓰레드에 들어간다

                        Log.e("data", four.getResult());//그 속에서 지정한 값을 return받음

                        video_start(four.getResult());
                        break;
                    }
                }
                if (!bl) {
                    Toast.makeText(getApplication(), "링크가 삭제되었거나 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                }//ggvid
                bl = false;
                break;
        }
    }
    public void video_start(String video){
        Intent tic  = new Intent(Intent.ACTION_VIEW);
        tic.setDataAndType(Uri.parse(video),"video/*");
        startActivity(tic);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}

