package com.example.sensors;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;

public class MapActivity extends AppCompatActivity {
    static final Point DEFAULT_POINT = new Point(59.945933, 30.320045);
    private MapView mapView;

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

}