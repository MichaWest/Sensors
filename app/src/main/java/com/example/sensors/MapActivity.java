package com.example.sensors;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

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
    static final Point DEFAULT_POINT = new Point(59.945933, 30.320045);
    private MapView mapView;
    private View gradientOverlay;

    private ArrayList<Point> sensors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapKitFactory.initialize(this);

        setContentView(R.layout.activity_main);
        mapView = findViewById(R.id.map_view);

        mapView.getMap().move(new CameraPosition(
                        new Point(59.92668, 30.33845), 17.0f, 0.0f, 0.0f),
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

        // Значки сенсора
        double[][] coords = new double[][] {{59.927362, 30.337506}, {59.926205, 30.342234}, {59.926382, 30.342552}, {59.928542, 30.341957}};

        for(int i=0; i< coords.length; i++){
            sensors.add(new Point(coords[i][0], coords[i][1]));
        }

        for(Point sensor: sensors){
            addSensorToMap(sensor);
        }

        for(Point sensor: sensors){
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

    private void addSensorToMap(Point point){
        PlacemarkMapObject placemark = mapView.getMapWindow().getMap().getMapObjects().addPlacemark();
        placemark.setGeometry(point);
        placemark.setOpacity(1);
        placemark.setIcon(ImageProvider.fromResource(this, R.drawable.sensor_icon_png));

        IconStyle iconStyle = new IconStyle();
        iconStyle.setAnchor(new PointF(0.5f, 1.0f)); // Точка привязки (центр по X, низ по Y)
        iconStyle.setScale(0.75f);

        placemark.setIconStyle(iconStyle);
    }

    private void drawGradientCircle(Point point, int radius){
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