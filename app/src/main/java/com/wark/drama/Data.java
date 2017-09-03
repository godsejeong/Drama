package com.wark.drama;

/**
 * Created by pc on 2017-08-31.
 */

public class Data {
    private String name;
    private String sub_name;
    private String url;

    public String url(){return url;}
    public String getname(){return name;}
    public String getsub_name(){return sub_name;}

    public void url(String s_url){
        url = s_url;
    }

    public void setname(String s_name){
        name = s_name;
    }
    public void setsub_name(String s_sub_name){
        sub_name = s_sub_name;
    }
}
