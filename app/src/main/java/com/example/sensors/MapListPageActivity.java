package com.example.sensors;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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

import com.example.sensors.database_contracts.DatabaseHelper;
import com.example.sensors.database_contracts.FieldReaderContract;
import com.example.sensors.objects.Field;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;

public class MapListPageActivity extends AppCompatActivity {
    private ListView listOfMap;
    private ArrayList<Field> fields;
    private FieldAdapter fieldAdapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_list_page);

        dbHelper = new DatabaseHelper(this);
        fields = getAllField();

        registerViews();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    private void registerViews(){
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
            return showDeleteMenu(view, position, parent);
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
    private boolean showDeleteMenu(View anchorView, int position, AdapterView parent) {
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
            Field field = (Field) parent.getItemAtPosition(position);
            fields.remove(field);
            dbHelper.deleteField(field.getFieldName());
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
            public void onConfirmClicked(String name, String filedCulture, String nameSoilType) {
                long res = dbHelper.addField(name, filedCulture, nameSoilType);
                Field newField = new Field(name);
                newField.setCultureOfCultivation(filedCulture);
                newField.setTypeOfSoil(nameSoilType);
                fields.add(newField);
                fieldAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelClicked() {
                // На будущее
            }
        });

        dialog.show();
    }
    private ArrayList<Field> getAllField() {
        ArrayList<Field> fields = new ArrayList<>();

        // 1. Сначала получаем все поля
        Cursor fieldCursor = dbHelper.getAllFields();

        if (fieldCursor != null && fieldCursor.moveToFirst()) {
            int fieldNameIndex = fieldCursor.getColumnIndex(FieldReaderContract.FieldEntry.COLUMN_NAME_FIELD_NAME);
            int fieldCultureIndex = fieldCursor.getColumnIndex(FieldReaderContract.FieldEntry.COLUMN_NAME_CULTURE_OF_CULTIVATION);
            int fieldTypeOfSoilIndex = fieldCursor.getColumnIndex(FieldReaderContract.FieldEntry.COLUMN_NAME_TYPE_OF_SOIL);

            do {
                // 2. Для каждого поля создаем объект Field
                String fieldName = fieldCursor.getString(fieldNameIndex);
                String cultureType = fieldCursor.getString(fieldCultureIndex);
                String typeOfSoil = fieldCursor.getString(fieldTypeOfSoilIndex);

                Field field = new Field(fieldName);
                field.setCultureOfCultivation(cultureType);
                field.setTypeOfSoil(typeOfSoil);

                // 3. Добавляем поле в результирующий список
                fields.add(field);
            } while (fieldCursor.moveToNext());

            fieldCursor.close();
        }

        return fields;
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
        intent.putExtra("Field name", item.getFieldName());
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

            TextView textFieldName = convertView.findViewById(R.id.field_name);
            TextView textInfo = convertView.findViewById(R.id.field_info);

            Field f = items.get(position);
            textFieldName.setText(f.getFieldName());
            textInfo.setText(f.getCultureOfCultivation()+", "+f.getTypeOfSoil());

            return convertView;
        }
    }


}