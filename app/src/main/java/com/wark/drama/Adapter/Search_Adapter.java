package com.wark.drama.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wark.drama.R;
import com.wark.drama.Search;
import com.wark.drama.data.Search_data;

import java.util.ArrayList;

/**
 * Created by pc on 2017-11-19.
 */

public class Search_Adapter extends BaseAdapter{
    private LayoutInflater inflater;
    private ArrayList<Search_data> items = new ArrayList<>();
    private int layout;
    TextView textView;

    public Search_Adapter(Context context, ArrayList<Search_data> items, int layout){
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = items;
        this.layout = layout;
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

            view = inflater.inflate(R.layout.search_items,viewGroup, false);
        }

        Search_data data = items.get(i);
        textView = view.findViewById(R.id.search_text_items);
        textView.setText(data.name());

        return view;
    }
}
