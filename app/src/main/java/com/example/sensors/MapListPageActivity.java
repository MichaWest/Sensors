package com.example.sensors;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.SensorPrivacyManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MapListPageActivity extends AppCompatActivity {
    private ListView listOfMap;
    private ArrayList<Field> fields = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_list_page);

        listOfMap = findViewById(R.id.map_list_view);

        fields.add(new Field("Michelle"));
        fields.add(new Field("Matvei"));
        fields.add(new Field("Dascha"));

        MyAdapter adapter = new MyAdapter(this, fields);
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

        final ImageButton addFieldButton = findViewById(R.id.maplist__btn_add);
        addFieldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shoeAddItemDialog();
            }
        });

    }

    private void shoeAddItemDialog(){
        CreateFieldDialog dialog = new CreateFieldDialog(this, new CreateFieldDialog.CustomDialogListener() {
            @Override
            public void onConfirmClicked(String name) {
                fields.add(new Field(name));
            }

            @Override
            public void onCancelClicked() {
                // На будущее
            }
        });

        dialog.show();
    }

    public class MyAdapter extends ArrayAdapter<Field>{
        private List<Field> items;
        private final Context context;

        public MyAdapter(@NonNull Context context, @NonNull List<Field> objects) {
            super(context, 0, objects);
            this.context = context;
            this.items = objects;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Field getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @NonNull
        @SuppressLint("ViewHolder")
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.map_content, parent, false);
            convertView.setBackgroundColor(Color.TRANSPARENT);
            TextView text = convertView.findViewById(R.id.field_name);

            Field f = items.get(position);
            text.setText(f.getFieldName());
            return convertView;
        }
    }



}