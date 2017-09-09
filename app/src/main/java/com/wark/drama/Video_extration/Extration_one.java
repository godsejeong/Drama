package com.wark.drama.Video_extration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by pc on 2017-09-09.
 */

public class Extration_one extends AppCompatActivity{
    String address;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        address = intent.getStringExtra("video");

        Log.e("Extration",address);

//    new Thread() {
//        public void run() {
//            try {
//                Document doc = Jsoup.connect(address).timeout(5000).get();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        }.start();
    }
}

