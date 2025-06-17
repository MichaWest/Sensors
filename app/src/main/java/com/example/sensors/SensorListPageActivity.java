package com.example.sensors;

import android.content.Context;
import android.content.Intent;
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
import com.example.sensors.objects.Sensor;

import java.util.ArrayList;
import java.util.List;

public class SensorListPageActivity extends AppCompatActivity {
    private ArrayList<Sensor> sensors;
    private ListView listOfSensor;
    private SensorAdapter sensorAdapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor_list_page);

        sensors = new ArrayList<>();

        listOfSensor = findViewById(R.id.sensor_list_view);
        sensorAdapter = new SensorAdapter(this, sensors);
        listOfSensor.setAdapter(sensorAdapter);

//        listOfSensor.setOnClickListener(view -> {
//            goToSensorPage();
//        });
//        listOfSensor.setOnItemLongClickListener((parent, view, position, id)  -> {
//            showPopupMenu(view, position, parent);
//            return true;
//        });

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
        addSensorButton.setOnClickListener(view -> {});

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

    private void goToMapPage(){
        Intent intent = new Intent(SensorListPageActivity.this, MapActivity.class);
        startActivity(intent);
    }

    private void goToMapListPage(){
        Intent intent = new Intent(SensorListPageActivity.this, MapListPageActivity.class);
        startActivity(intent);
    }

    private void goToSensorPage(){
        //Intent intent = new Intent(SensorListPageActivity.this, Sensor.class);
        //startActivity(intent);
    }

    private void showPopupMenu(View anchorView, int position, AdapterView parent) {
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
            sensors.remove((Sensor) parent.getItemAtPosition(position));
            sensorAdapter.notifyDataSetChanged();
            popupWindow.dismiss();
        });

        popupWindow.showAsDropDown(anchorView);
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

            TextView name = convertView.findViewById(R.id.sensor_name);
            name.setText(s.getName());

            TextView humidity = convertView.findViewById(R.id.humidity_info);
            humidity.setText(s.getHumidity() +" %");

            return convertView;
        }
    }
}
