package com.example.sensors;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.sensors.database_contracts.DatabaseHelper;
import com.example.sensors.database_contracts.SensorReaderContract;
import com.example.sensors.objects.Field;
import com.example.sensors.objects.Sensor;

import java.util.ArrayList;
import java.util.List;

public class SensorListPageActivity extends AppCompatActivity {
    private ArrayList<Sensor> sensors;
    private ListView listOfSensor;
    private SensorAdapter sensorAdapter;
    private DatabaseHelper dbHelper;

    private CreateSensorDialog addItemDialog;

    private String fieldName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor_list_page);

        Bundle arguments = getIntent().getExtras();
        fieldName = arguments.get("Field name").toString();

        dbHelper = new DatabaseHelper(this);
        sensors = getAllSensor();

        registerViews();
    }

    private void registerViews(){
        listOfSensor = findViewById(R.id.sensor_list_view);
        sensorAdapter = new SensorAdapter(this, sensors);
        listOfSensor.setAdapter(sensorAdapter);

        listOfSensor.setOnItemClickListener(this::goToSensorPage);
        listOfSensor.setOnItemLongClickListener((parent, view, position, id)  -> {
            showDeleteMenu(view, position, parent);
            return true;
        });

        final ImageButton mapButton = findViewById(R.id.sensor_list_page__btn_map);
        mapButton.setOnClickListener(view -> {
            goToMapPage();
        });

        final ImageButton backButton = findViewById(R.id.sensor_list__btn_back);
        backButton.setOnClickListener(view -> {
            goToMapListPage();
        });

        final ImageButton notifyButton = findViewById(R.id.sensor_list__btn_notify);
        notifyButton.setOnClickListener(view -> {});

        final ImageButton addSensorButton = findViewById(R.id.sensor_list__btn_add);
        addSensorButton.setOnClickListener(view -> showAddItemDialog());

        ConstraintLayout filter = findViewById(R.id.clickable_filter_icon_area);
        filter.setOnClickListener(v -> {
            showFilterDialog();
        });

        ConstraintLayout sort = findViewById(R.id.clickable_sort_icon_area);
        sort.setOnClickListener(v -> {
            showSortDialog();
        });
    }

    private void showFilterDialog(){

    }

    private void showSortDialog(){

    }

    private ArrayList<Sensor> getAllSensor(){
        ArrayList<Sensor> sensors = new ArrayList<>();

        Cursor sensorCursor = dbHelper.getSensorsForField(fieldName);

        if (sensorCursor != null && sensorCursor.moveToFirst()){
            int sensorSerialIndex = sensorCursor.getColumnIndex(SensorReaderContract.SensorEntry.COLUMN_NAME_SERIAL_NUMBER);
            do{
                String serialNumber = sensorCursor.getString(sensorSerialIndex);
                Sensor sensor = new Sensor(serialNumber);
                sensors.add(sensor);
            }while (sensorCursor.moveToNext());

            sensorCursor.close();
        }

        return sensors;
    }

    private void showAddItemDialog(){
        addItemDialog = new CreateSensorDialog(this, new CreateSensorDialog.CustomDialogListener() {
            @Override
            public void onConfirmClicked(String serialNumber, double latitude, double longitude) {
                long res = dbHelper.addSensor(serialNumber, latitude, longitude, true, 0, 0, 0, fieldName);

                Sensor newSensor = new Sensor(serialNumber);
                newSensor.setLatitude(latitude);
                newSensor.setLongitude(longitude);
                sensors.add(newSensor);
                sensorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelClicked() {
                // На будущее
            }
        });

        addItemDialog.show();
    }

    private void goToMapPage(){
        Intent intent = new Intent(SensorListPageActivity.this, MapActivity.class);
        startActivity(intent);
    }

    private void goToMapListPage(){
        Intent intent = new Intent(SensorListPageActivity.this, MapListPageActivity.class);
        startActivity(intent);
    }

    private void goToSensorPage(AdapterView parent, View view, int position, long id){
        Intent intent = new Intent(SensorListPageActivity.this, Sensor.class);
        startActivity(intent);
    }

    private void showDeleteMenu(View anchorView, int position, AdapterView parent) {
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
            Sensor sensor = (Sensor)  parent.getItemAtPosition(position);
            sensors.remove(sensor);
            dbHelper.deleteSensor(sensor.getSerialNumber());
            sensorAdapter.notifyDataSetChanged();
            popupWindow.dismiss();
        });

        popupWindow.showAsDropDown(anchorView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Передаем результат в диалог, если он открыт
        if (addItemDialog != null && addItemDialog.isShowing()) {
            addItemDialog.onActivityResult(requestCode, resultCode, data);
        }
    }

    public class SensorAdapter extends ArrayAdapter<Sensor>{
        private List<Sensor> items;
        private final Context context;

        public SensorAdapter(@NonNull Context context, @NonNull List<Sensor> objects) {
            super(context, 0, objects);
            this.context = context;
            this.items = objects;
        }

        @Override
        public int getCount(){
            return items.size();
        }

        @Override
        public Sensor getItem(int i) {
            return items.get(i);
        }

        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.sensor_content, parent, false);
            convertView.setBackgroundColor(Color.TRANSPARENT);

            Sensor s = items.get(position);

            TextView textSerialNumber = convertView.findViewById(R.id.sensor_name);
            textSerialNumber.setText(s.getSerialNumber());

            TextView humidity = convertView.findViewById(R.id.humidity_info);
            humidity.setText(s.getHumidity() +" %");

            return convertView;
        }
    }


}
