package com.wark.drama;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.wark.drama.Adapter.Search_Adapter;

import java.net.URLEncoder;

/**
 * Created by pc on 2017-11-14.
 */

public class Search extends AppCompatActivity {
    EditText editText;
    ImageView imageView;
    ListView listView;
    Search_Adapter adapter;
    private String address="https://tivico.net/?s=";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        adapter = new Search_Adapter();

        listView = (ListView) findViewById(R.id.search_list);
        editText = (EditText) findViewById(R.id.search_text);
        imageView = (ImageView) findViewById(R.id.image_search);


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


                adapter.addItem(editText.getText().toString());
                listView.setAdapter(adapter);
            }
        });

    }
}
