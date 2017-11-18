package com.wark.drama.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wark.drama.data.Data;
import com.wark.drama.R;

import java.util.ArrayList;



public class Adapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<Data> items = new ArrayList<Data>();
    TextView text;
    TextView sub_text;
    public Adapter(){

    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();

        if(view == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.ltems,viewGroup, false);
        }

        Data data = items.get(i);
        text = view.findViewById(R.id.main_text);
        text.setText(data.getname());
        sub_text = view.findViewById(R.id.sub_text);
        sub_text.setText(data.getsub_name());
        return view;
    }

    public void addItem(String main_name,String sub_name,String url,String img_data) {
        Data data = new Data();
        Log.e("main",main_name);
        data.setname(main_name);
        data.image(img_data);
        data.url(url);
        data.setsub_name(sub_name);
        items.add(data);
    }
}
