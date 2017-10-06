package com.wark.drama.Video_extration;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;



public class Extration_two extends Thread {
    String address;
    String value = new String();

    public Extration_two(String address){
        this.address = address;
        value = new String();
    }

            public void run(){
                try {
                    Document doc = Jsoup.connect(address).timeout(5000).get();
                    Elements uri_address  = doc.select("video").select("source").eq(1);

                    for(Element element : uri_address){
                        value = uri_address.attr("src");
                        Log.e("value",value);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    public String getResult() {
        return value.toString();
    }

}

