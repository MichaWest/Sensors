package com.example.sensors;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;


public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);

        registerViews();
    }

    private void registerViews(){
        final ImageButton mapButton = findViewById(R.id.account__btn_map);
        mapButton.setOnClickListener(view -> {
            Intent intent = new Intent(AccountActivity.this, MapActivity.class);
            startActivity(intent);
        });

        final ImageButton listButton = findViewById(R.id.account__btn_list);
        listButton.setOnClickListener(view -> {
            Intent intent = new Intent(AccountActivity.this, FieldListPageActivity.class);
            startActivity(intent);
        });
    }

}