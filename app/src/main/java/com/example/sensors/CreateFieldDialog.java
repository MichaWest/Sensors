package com.example.sensors;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class CreateFieldDialog extends Dialog {

    public interface CustomDialogListener {
        void onConfirmClicked(String name);
        void onCancelClicked();

    }

    private CustomDialogListener listener;

    public CreateFieldDialog(Context context, CustomDialogListener listener) {
        super(context);
        this.listener = listener;
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // Убираем заголовок
        setContentView(R.layout.add_map_dialog);

        EditText fieldName = findViewById(R.id.input_field_name);

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
                String name = fieldName.getText().toString();
                listener.onConfirmClicked(name);
            }
            dismiss();
        });
    }
}
