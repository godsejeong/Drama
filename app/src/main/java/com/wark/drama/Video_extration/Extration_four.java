package com.wark.drama.Video_extration;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;



public class Extration_four extends Thread{
    String address;
    String data;
    String get_data;

    public Extration_four(String address){
        this.address = address;
        data = new String();
        Log.e("data",address);
    }


    public void run() {
        try {
            Document doc = Jsoup.connect(address).timeout(5000).get();
            get_data = doc.select("script").eq(4).html();

            Log.e("asdfasdf",get_data);

            int data_one = get_data.indexOf("sources");
            int data_two = get_data.indexOf("modes") - 50;
            String data_result = get_data.substring(data_one,data_two);

            Log.e("data_result",data_result);

            int data_three = data_result.indexOf("480p") + 31;

            data = data_result.substring(data_three);
            Log.e("data_asdf",data);
            } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getResult() {
        return data.toString();
    }
}
