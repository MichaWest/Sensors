package com.example.sensors;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MapListPageActivity extends AppCompatActivity {
    private ListView listOfMap;
    private ArrayList<Field> fields;
    private static final String PREFS_NAME = "MapListPagePrefs";
    private static final String FIELDS_LIST_KEY = "fields_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_list_page);

        loadDataFromPrefs();

        listOfMap = findViewById(R.id.map_list_view);
        FieldAdapter adapter = new FieldAdapter(this, fields);
        listOfMap.setAdapter(adapter);

        listOfMap.setOnItemClickListener((parent, view, position, id) -> {
            // position - индекс нажатого элемента
            // view - сам элемент списка
            // parent - ListView

            // Получаем данные элемента
            Field item = (Field) parent.getItemAtPosition(position);

            Intent intent = new Intent(MapListPageActivity.this, SensorListPageActivity.class);
            //intent.putExtra("SensorsList", f.getSensors());
            startActivity(intent);

        });

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

    @Override
    protected void onPause(){
        super.onPause();
        saveDataToPrefs();
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

    private void loadDataFromPrefs(){
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(FIELDS_LIST_KEY, null);

        Type type = new TypeToken<ArrayList<Field>>() {}.getType();
        fields = gson.fromJson(json, type);

        if(fields == null){
            fields = new ArrayList<>();
        }
    }

    private void saveDataToPrefs(){
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();
        String json = gson.toJson(fields);

        editor.putString(FIELDS_LIST_KEY, json);
        editor.apply();
    }

    public class FieldAdapter extends ArrayAdapter<Field>{
        private List<Field> items;
        private final Context context;

        public FieldAdapter(@NonNull Context context, @NonNull List<Field> objects) {
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