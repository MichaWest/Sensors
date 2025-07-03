package com.example.sensors;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sensors.validation_classes.AddFieldViewModel;

import java.util.ArrayList;
import java.util.Arrays;

import javax.microedition.khronos.egl.EGLDisplay;

public class FieldInputDialogFragment extends DialogFragment {
    public interface FieldInputDialogListener {
        void onFieldDataSubmitted(String fieldName, String soilType, String crop);
        void onCancelClicked();
    }

    private FieldInputDialogListener listener;
    private EditText fieldNameEditText;
    private ImageButton submitButton;
    private ImageButton cancelButton;
    private Spinner soilTypeSpinner;
    private Spinner cultureSpinner;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_map_dialog, null);

        builder.setView(view).setTitle("");
        Dialog dialog = builder.create();

        // Инициализация ViewModel
        AddFieldViewModel viewModel = new ViewModelProvider(this).get(AddFieldViewModel.class);

        // Регистрируем View-элементы
        registerViews(view);

        // Настраиваем наблюдением за состоянием валидации
        viewModel.getFieldNameValidationResult().observe(this, result -> {
            if (result != null && !result.isValid()){
                fieldNameEditText.setError(getString(result.getErrorMessage()));
            } else {
                fieldNameEditText.setError(null);
            }
        });

        // Наблюдаем за состоянием кнопки
        viewModel.getSubmitButtonEnabled().observe(this, isEnabled -> {
            submitButton.setEnabled(isEnabled != null && isEnabled);
        });

        // Добавляем обработчики ввода текста
        fieldNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                viewModel.onFieldNameChanged(editable);
            }
        });

        // Обработчик кнопки отправки
        submitButton.setOnClickListener(v -> {
            if (viewModel.getSubmitButtonEnabled().getValue() != null &&
            viewModel.getSubmitButtonEnabled().getValue()) {
                String fieldName = fieldNameEditText.getText().toString();
                String soilType = soilTypeSpinner.getSelectedItem().toString();
                String culture = cultureSpinner.getSelectedItem().toString();

                if (listener != null){
                    listener.onFieldDataSubmitted(fieldName, soilType, culture);
                }

                dialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(v -> {
            if(listener != null){
                listener.onCancelClicked();
            }
            dialog.dismiss();
        });

        return dialog;
    }

    public void setCustomDialogListener(FieldInputDialogListener listener) {
        this.listener = listener;
    }

    private void registerViews(View view){
        fieldNameEditText = view.findViewById(R.id.input_field_name);
        soilTypeSpinner = view.findViewById(R.id.soil_type_spinner);
        cultureSpinner = view.findViewById(R.id.culture_spinner);
        submitButton = view.findViewById(R.id.submit_button);
        cancelButton = view.findViewById(R.id.cancel_button);

        setupSpinners();
    }

    private void setupSpinners() {
        soilTypeSpinner.setPopupBackgroundResource(R.color.background);

        ArrayAdapter<CharSequence> soilTypeAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.soil_types_array,
                R.layout.custom_spinner_item
        );
        soilTypeSpinner.setAdapter(soilTypeAdapter);

        cultureSpinner.setPopupBackgroundResource(R.color.background);
        ArrayAdapter<CharSequence> cultureAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.culture_array,
                R.layout.custom_spinner_item
        );
        cultureSpinner.setAdapter(cultureAdapter);
    }
}
