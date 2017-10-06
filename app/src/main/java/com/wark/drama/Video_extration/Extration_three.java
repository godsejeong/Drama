package com.wark.drama.Video_extration;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.UnsupportedEncodingException;



public class Extration_three extends Thread {
String address;
String src;
String data;
    public Extration_three(String address){
        this.address = address;
        Log.e("asdfasdfasdf",address);
        src = new String();
    }

    public void run() {
        try {
            Document doc = Jsoup.connect(address).timeout(5000).get();

             data = doc.select("script").eq(8).html();
            int a = data.indexOf("video\\/mp4") + 10;
            int b = data.indexOf("reporting")+10;
            String start = data.substring(a, b);


            int d = start.indexOf("video\\/mp4") + 19;
            int c_b = start.indexOf("reporting")+10;
            String second = start.substring(d,c_b);

            Log.e("asdfs",second);

             if(second.contains("480")) {
                 int re_one = src.indexOf("video\\/mp4") + 19;
                 int re_two = src.indexOf("reporting") - 6;
                 src = src.substring(re_one, re_two);

                 Log.e("asdf", src);
             }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getResult() {
        return src.toString();
    }

}