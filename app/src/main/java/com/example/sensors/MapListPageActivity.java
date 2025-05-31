package com.example.sensors;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.sensors.objects.Field;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MapListPageActivity extends AppCompatActivity {
    private ListView listOfMap;
    private ArrayList<Field> fields;
    private static final String PREFS_NAME = "MapListPagePrefs";
    private static final String FIELDS_LIST_KEY = "fields_list";
    private FieldAdapter fieldAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_list_page);

        loadDataFromPrefs();

        listOfMap = findViewById(R.id.map_list_view);
        fieldAdapter = new FieldAdapter(this, fields);
        listOfMap.setAdapter(fieldAdapter);
        // position - индекс нажатого элемента
        // view - сам элемент списка
        // parent - ListView
        // Получаем данные элемента
        listOfMap.setOnItemClickListener(this::goToSensorListPage);
        listOfMap.setOnItemLongClickListener((parent, view, position, id) -> {
            // position - индекс нажатого элемента
            // view - сам элемент списка
            // parent - ListView
            return showPopupMenu(view, position, parent);
        });

        final ImageButton mapButton = findViewById(R.id.maplist__btn_map);
        mapButton.setOnClickListener(view -> goToMapPage());

        final ImageButton accountButton = findViewById(R.id.maplist__btn_account);
        accountButton.setOnClickListener(view -> goToAccountPage());

        final ImageButton addFieldButton = findViewById(R.id.maplist__btn_add);
        addFieldButton.setOnClickListener(view -> showAddItemDialog());

        ConstraintLayout filter = findViewById(R.id.clickable_filter_icon_area);
        filter.setOnClickListener(v -> showFilterDialog());

        ConstraintLayout sort = findViewById(R.id.clickable_sort_icon_area);
        sort.setOnClickListener(v -> showSortDialog());

    }

    @Override
    protected void onPause(){
        super.onPause();
        saveDataToPrefs();
    }

    private boolean showPopupMenu(View anchorView, int position, AdapterView parent) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.map_list_popup_menu, null);

        PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true // Фокус для обработки кликов
        );

        popupWindow.setElevation(10);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);

        popupView.findViewById(R.id.delete_btn).setOnClickListener(v -> {
            fields.remove((Field) parent.getItemAtPosition(position));
            fieldAdapter.notifyDataSetChanged();
            popupWindow.dismiss();
        });

        popupWindow.showAsDropDown(anchorView);
        return true;
    }
    private void showSortDialog(){

    }

    private void showFilterDialog(){

    }
    private void showAddItemDialog(){
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

    private void goToAccountPage(){
        Intent intent = new Intent(MapListPageActivity.this, AccountActivity.class);
        startActivity(intent);
    }

    private void goToMapPage(){
        Intent intent = new Intent(MapListPageActivity.this, MapActivity.class);
        startActivity(intent);
    }

    private void goToSensorListPage(AdapterView parent, View view, int position, long id){
        Field item = (Field) parent.getItemAtPosition(position);

        Intent intent = new Intent(MapListPageActivity.this, SensorListPageActivity.class);
        //intent.putExtra("SensorsList", f.getSensors());
        startActivity(intent);
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
            return items.get(i);
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