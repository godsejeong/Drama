package com.wark.drama;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wark.drama.Adapter.Search_Adapter;
import com.wark.drama.data.Data;
import com.wark.drama.data.Search_data;

import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by pc on 2017-11-14.
 */

public class Search extends AppCompatActivity {
    EditText editText;
    ImageView imageView;
    ListView listView;
    Search_Adapter adapter;
    private String address="https://tivico.net/?s=";
    final ArrayList<Search_data> items=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        listView = (ListView) findViewById(R.id.search_list);
        editText = (EditText) findViewById(R.id.search_text);
        imageView = (ImageView) findViewById(R.id.image_search);
        adapter = new Search_Adapter(Search.this,items,R.layout.search_items);
        listView.setAdapter(adapter);
        load();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }//스테이터스바 투명하게 하기

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = URLEncoder.encode(editText.getText().toString());
                Intent intent = new Intent(Search.this,MainActivity.class);
                intent.putExtra("passurl",address+url);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),editText.getText().toString()+"을 검색중입니다.",Toast.LENGTH_SHORT).show();

                Search_data name = new Search_data(editText.getText().toString());
                items.add(name);
                adapter = new Search_Adapter(Search.this,items,R.layout.search_items);
                listView.setAdapter(adapter);
                save();
                adapter.notifyDataSetChanged();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
                final Search_data data = (Search_data) adapter.getItem(position);

                Intent intent = new Intent(Search.this,MainActivity.class);
                intent.putExtra("passurl",address+data.name());
                startActivity(intent);
                Toast.makeText(getApplicationContext(),data.name()+"을 검색중입니다.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    void load() {
        Gson gson = new Gson();
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        String json = pref.getString("save", "");
        ArrayList<Search_data> items_ = new ArrayList<>();
        items_ = gson.fromJson(json, new TypeToken<ArrayList<Search_data>>(){}.getType());
        if(items_ != null)items.addAll(items_);
    }


    void save() { //items 안의 내용이 저장됨
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String json = new Gson().toJson(items);
        editor.putString("save", json);
        editor.commit();
    }

}
