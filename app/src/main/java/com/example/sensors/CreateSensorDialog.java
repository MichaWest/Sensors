package com.example.sensors;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class CreateSensorDialog extends Dialog {

    public interface CustomDialogListener {
        void onConfirmClicked(String serialNumber, double latitude, double longitude);
        void onCancelClicked();
    }

    private CreateSensorDialog.CustomDialogListener listener;

    public CreateSensorDialog(Context context, CreateSensorDialog.CustomDialogListener listener) {
        super(context);
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
                listener.onConfirmClicked(serialNum, 59.926557, 30.336916);
            }
            dismiss();
        });
    }
}
