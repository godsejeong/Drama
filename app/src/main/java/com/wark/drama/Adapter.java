package com.wark.drama;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by pc on 2017-08-31.
 */

public class Adapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<Data> items = new ArrayList<Data>();

    public Adapter(){

    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public String getItem(int i) {
        return items.get(i).getname();
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

        TextView text = (TextView) view.findViewById(R.id.main_text);
        text.setText(data.getname());
        TextView sub_text = (TextView) view.findViewById(R.id.sub_text);
        sub_text.setText(data.getsub_name());
        return view;
    }

    public void addItem(String main_name, String sub_name) {
        Data data = new Data();

        data.setname(main_name);
        data.setsub_name(sub_name);
        items.add(data);
    }


}
