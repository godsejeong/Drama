package com.wark.drama;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends ActionBarActivity{
    ListView listView;
    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<Data> items = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listview);
         adapter = new Adapter();
        listView.setAdapter(adapter);
        adapter.addItem("asdf","fdsa");
    }
}
