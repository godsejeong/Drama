package com.wark.drama.Video_extration;

import android.util.Base64;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by pc on 2017-09-09.
 */

public class Extration_one extends Thread{
    String address;
    String src, value;
    String Base = new String();

    public Extration_one(String address){
        this.address = address;
        Log.e("asdfasdfasdf",address);
        Base = new String();
    }

    public void run() {
                    try {
                        Document doc = Jsoup.connect(address).timeout(5000).get();
                        src = doc.select("div").select("script").html();

                        String par = src.replace("jwplayer(\"myElement\").setup(", "").replace(");\n" + "\t\tjwplayer().onError(function(){\n" + "        //jwplayer().load({file:\"k-vid.mp4\", image:\"/images/change.png\"});\n" + "      \tjwplayer().stop();\n" + "    \t});", "");
                        Log.e("src", par);

                        int a = par.indexOf("window.atob") + 13;
                        int b = par.indexOf("label") - 6;
                        value = par.substring(a, b);
                        Log.e("c", value);
                        Base = new String(Base64.decode(value, Base64.DEFAULT), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
    }

    public String getResult() {
        return Base.toString();
    }
}
