package com.wark.drama;



public class Data {
    private String name;
    private String sub_name;
    private String url;
    private String image;

    public String image(){return image;}
    public String url(){return url;}
    public String getname(){return name;}
    public String getsub_name(){return sub_name;}

    public void image(String s_image){image = s_image;}
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
