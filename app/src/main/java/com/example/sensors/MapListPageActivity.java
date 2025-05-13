package com.example.sensors;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class MapListPageActivity extends AppCompatActivity {
    private ListView listOfMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_list_page);

        listOfMap = findViewById(R.id.map_list_view);

        MyAdapter adapter = new MyAdapter();
        listOfMap.setAdapter(adapter);

        final ImageButton mapButton = findViewById(R.id.maplist__btn_map);
        mapButton.setOnClickListener(view -> {
            Intent intent = new Intent(MapListPageActivity.this, MapActivity.class);
            startActivity(intent);
        });

        final ImageButton accountButton = findViewById(R.id.maplist__btn_account);
        accountButton.setOnClickListener(view -> {
            Intent intent = new Intent(MapListPageActivity.this, AccountActivity.class);
            startActivity(intent);
        });

    }

    public class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.map_content, parent, false);
            convertView.setBackgroundColor(Color.TRANSPARENT);
            return convertView;
        }
    }



}