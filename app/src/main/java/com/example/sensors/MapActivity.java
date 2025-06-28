package com.example.sensors;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sensors.database_contracts.DatabaseHelper;
import com.example.sensors.database_contracts.SensorReaderContract;
import com.example.sensors.objects.Field;
import com.example.sensors.objects.Sensor;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Circle;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CircleMapObject;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity {
    static final Point DEFAULT_POINT = new Point(59.927434, 30.341682);

    private MapView mapView;
    private View gradientOverlay;
    private DatabaseHelper dbHelper;

    private ArrayList<Sensor> sensors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapKitFactory.initialize(this);

        setContentView(R.layout.activity_main);
        mapView = findViewById(R.id.map_view);

        mapView.getMap().move(new CameraPosition(
                        DEFAULT_POINT, 17.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);

        final ImageButton accountButton = findViewById(R.id.main__btn_account);
        accountButton.setOnClickListener(view -> {
            Intent intent = new Intent(MapActivity.this, AccountActivity.class);
            startActivity(intent);
        });

        final ImageButton listButton = findViewById(R.id.main__btn_list);
        listButton.setOnClickListener(view -> {
            Intent intent = new Intent(MapActivity.this, MapListPageActivity.class);
            startActivity(intent);
        });


        dbHelper = new DatabaseHelper(this);
        sensors = getAllSensor();

        for(Sensor sensor: sensors){
            addSensorToMap(sensor);
        }

        for(Sensor sensor: sensors){
            drawGradientCircle(sensor, 50);
        }

    }

    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

    private ArrayList<Sensor> getAllSensor(){
        ArrayList<Sensor> sensors = new ArrayList<>();

        Cursor sensorCursor = dbHelper.getAllSensor();

        if(sensorCursor != null && sensorCursor.moveToFirst()){
            int sensorSerialNumber = sensorCursor.getColumnIndex(SensorReaderContract.SensorEntry.COLUMN_NAME_SERIAL_NUMBER);
            int sensorLatitude = sensorCursor.getColumnIndex(SensorReaderContract.SensorEntry.COLUMN_NAME_LATITUDE);
            int sensorLongitude = sensorCursor.getColumnIndex(SensorReaderContract.SensorEntry.COLUMN_NAME_LONGITUDE);

            do {
                String serialNumber = sensorCursor.getString(sensorSerialNumber);
                double latitude = sensorCursor.getDouble(sensorLatitude);
                double longitude = sensorCursor.getDouble(sensorLongitude);

                Sensor sensor = new Sensor(serialNumber);
                sensor.setLongitude(longitude);
                sensor.setLatitude(latitude);

                sensors.add(sensor);
            } while (sensorCursor.moveToNext());

            sensorCursor.close();
        }

        return sensors;
    }

    private void addSensorToMap(Sensor sensor){
        Point point = new Point(sensor.getLatitude(), sensor.getLongitude());
        PlacemarkMapObject placemark = mapView.getMapWindow().getMap().getMapObjects().addPlacemark();
        placemark.setGeometry(point);
        placemark.setOpacity(1);
        placemark.setIcon(ImageProvider.fromResource(this, R.drawable.sensor_icon_png));

        IconStyle iconStyle = new IconStyle();
        iconStyle.setAnchor(new PointF(0.5f, 1.0f)); // Точка привязки (центр по X, низ по Y)
        iconStyle.setScale(0.75f);

        placemark.setIconStyle(iconStyle);
    }

    private void drawGradientCircle(Sensor sensor, int radius){
        Point point = new Point(sensor.getLatitude(),sensor.getLongitude());
        CircleMapObject circle = mapView.getMapWindow().getMap().getMapObjects().addCircle(new Circle(point, radius));
        circle.setFillColor(hexToColor("9FB8B8"));
        circle.setStrokeColor(0);
    }

    public static int hexToColor(String hexColor) {
        // Удаляем # если есть
        hexColor = hexColor.replace("#", "");

        // Добавляем прозрачность если нужно (FF = 255 = полностью непрозрачный)
        if (hexColor.length() == 6) {
            hexColor = "FF" + hexColor;
        }

        try {
            // Парсим HEX в long и преобразуем в int
            long colorLong = Long.parseLong(hexColor, 16);
            return (int) colorLong;
        } catch (Exception e) {
            // Возвращаем цвет по умолчанию (красный) в случае ошибки
            return Color.RED;
        }
    }
}