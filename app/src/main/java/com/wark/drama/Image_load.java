package com.wark.drama;


import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Image_load extends Thread{
    String address;
    Drawable image;
    public Image_load(String address){
        this.address = address;
    }
    public void run() {
        InputStream is = null;
        try {
            is = (InputStream)new URL(address).getContent();
            image = Drawable.createFromStream(is,"srcName");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Drawable getResult() {
        return image;
    }


}
