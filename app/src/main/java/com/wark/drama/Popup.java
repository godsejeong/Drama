package com.wark.drama;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by pc on 2017-09-30.
 */

public class Popup extends Activity {
    Button button;
    LinearLayout click_one;
    LinearLayout click_two;
    String src_one,src_two;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup);


        button = (Button) findViewById(R.id.result_btn);
        click_one = (LinearLayout) findViewById(R.id.click_one);
        click_two = (LinearLayout) findViewById(R.id.click_two);

        Intent intent = getIntent();
        src_one = intent.getStringExtra("video");//받아온 값
        src_two = intent.getStringExtra("video_two");
        Log.e("src_one",src_one);
        Log.e("src_two",src_two);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        click_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_ = new Intent(Popup.this,Video.class);
                intent_.putExtra("video",src_one);
                startActivity(intent_);
                finish();
            }
        });

        click_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(src_two.equals("0")){
                Toast.makeText(getApplicationContext(),"링크가 삭제되었거나 존재하지 않습니다.",Toast.LENGTH_LONG).show();
            }else{
                Intent intent_ = new Intent(Popup.this,Video.class);
                intent_.putExtra("video",src_two);
                startActivity(intent_);
                finish();
            }
            }
        });
    }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
                return false;
            }
            return true;
        }






    public void video_start(String video){
        Intent tic  = new Intent(Intent.ACTION_VIEW);
        tic.setDataAndType(Uri.parse(video),"video/*");
        startActivity(tic);
    }



    }

