package com.example.sensors;

import static android.app.PendingIntent.getActivity;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DecimalFormat;

public class CreateSensorDialog extends Dialog {

    public interface CustomDialogListener {
        void onConfirmClicked(String serialNumber, double latitude, double longitude);
        void onCancelClicked();
    }

    private CreateSensorDialog.CustomDialogListener listener;
    private static final int REQUEST_MAP = 1001;
    private Activity hostActivity;
    private double selectedLat = 0;
    private double selectedLng = 0;

    public CreateSensorDialog(Activity context, CreateSensorDialog.CustomDialogListener listener) {
        super(context);
        this.hostActivity = context;
        this.listener = listener;
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_sensor_dialog);

        EditText serialNumber = findViewById(R.id.input_sensor_serial_number);
        TextView cordText = findViewById(R.id.cord_text);
        ImageButton getCordBtn = findViewById(R.id.get_cord_button);

        ImageButton btnCancel = findViewById(R.id.cancel_button);
        ImageButton btnConfirm = findViewById(R.id.ok_button);

        btnCancel.setOnClickListener(view -> {
            if(listener != null){
                listener.onCancelClicked();
            }
            dismiss();
        });

        btnConfirm.setOnClickListener(view -> {
            if(listener != null){
                String serialNum = serialNumber.getText().toString();
                listener.onConfirmClicked(serialNum, selectedLat, selectedLng);
            }
            dismiss();
        });

        getCordBtn.setOnClickListener(v -> {
            Intent mapIntent = new Intent(getContext(), GetCoordinatesFieldActivity.class);
            hostActivity.startActivityForResult(mapIntent, REQUEST_MAP);
        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_MAP && resultCode == Activity.RESULT_OK){
            selectedLat = data.getDoubleExtra("latitude", 0);
            selectedLng = data.getDoubleExtra("longitude", 0);

            DecimalFormat decimalFormat = new DecimalFormat("#.######");
            String res = decimalFormat.format(selectedLat)+", "+decimalFormat.format(selectedLng);

            TextView cordText = findViewById(R.id.cord_text);
            cordText.setText(res);
        }
    }
}
