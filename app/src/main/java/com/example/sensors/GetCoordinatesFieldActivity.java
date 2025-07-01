package com.example.sensors;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.GeoObjectTapEvent;
import com.yandex.mapkit.layers.GeoObjectTapListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.InputListener;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

import java.text.DecimalFormat;

public class GetCoordinatesFieldActivity extends AppCompatActivity {
    private double selectedLat = 59.927434;
    private double selectedLng = 30.341682;
    private PlacemarkMapObject currentMarker;
    private MapView mapView;
    static final Point DEFAULT_POINT = new Point(59.927434, 30.341682);


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_coordinates_field);
        registerViews();
    }

    private void registerViews(){
        MapKitFactory.initialize(this);
        mapView = findViewById(R.id.get_cord_map_view);

        mapView.getMap().move(new CameraPosition(DEFAULT_POINT, 17.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0), null);

        ImageButton btnConfirm = findViewById(R.id.confirm_button);
        btnConfirm.setOnClickListener(v -> returnLocation());

        setupMapListeners();
    }

    private void setupMapListeners(){
        mapView.getMapWindow().getMap().addInputListener(new InputListener() {
            @Override
            public void onMapTap(@NonNull Map map, @NonNull Point point) {
                handlePointSelection(point);
            }

            @Override
            public void onMapLongTap(@NonNull Map map, @NonNull Point point) {
                //handlePointSelection(point);
            }
        });
    }


    private void handlePointSelection(Point point){
        if (currentMarker != null) {
            mapView.getMapWindow().getMap().getMapObjects().remove(currentMarker);
        }

        currentMarker = getPlacemark(point);

        selectedLat = point.getLatitude();
        selectedLng = point.getLongitude();

        TextView cordText = findViewById(R.id.cord_text);

        DecimalFormat decimalFormat = new DecimalFormat("#.######");
        String res = decimalFormat.format(selectedLat)+", "+decimalFormat.format(selectedLng);
        cordText.setText(res);
    }

    private PlacemarkMapObject getPlacemark(Point point){
        PlacemarkMapObject placemark = mapView.getMapWindow().getMap().getMapObjects().addPlacemark();
        placemark.setGeometry(point);
        placemark.setOpacity(1);
        placemark.setIcon(ImageProvider.fromResource(this, R.drawable.sensor_icon_png));

        IconStyle iconStyle = new IconStyle();
        iconStyle.setAnchor(new PointF(0.5f, 1.0f)); // Точка привязки (центр по X, низ по Y)
        iconStyle.setScale(0.75f);

        placemark.setIconStyle(iconStyle);

        return placemark;
    }


    private void returnLocation() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("latitude", selectedLat);
        resultIntent.putExtra("longitude", selectedLng);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
