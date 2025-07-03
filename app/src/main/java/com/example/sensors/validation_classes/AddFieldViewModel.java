package com.example.sensors.validation_classes;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sensors.R;

public class AddFieldViewModel extends ViewModel{
    private final MutableLiveData<String> fieldName = new MutableLiveData<>();
    private final MutableLiveData<ValidationResult> fieldNameValidationResult = new MutableLiveData<>();

    private final Validator<String> fieldNameValidator = new Validator<>(
            Conditions.TextMaxLength(R.string.field_name_max_length_error, 16)
    );

    private final MutableLiveData<Boolean> submitButtonEnabled = new MutableLiveData<>(false);

    public LiveData<String> getFieldName(){
        return fieldName;
    }

    public LiveData<ValidationResult> getFieldNameValidationResult(){
        return fieldNameValidationResult;
    }

    public void onFieldNameChanged(CharSequence s){
        String newFieldName = s.toString();
        fieldNameValidationResult.setValue(fieldNameValidator.validate(newFieldName));
        updateSubmitButtonState();
    }

    public LiveData<Boolean> getSubmitButtonEnabled() {
        return submitButtonEnabled;
    }

    private void updateSubmitButtonState(){
        boolean isFieldNameValid = fieldNameValidationResult.getValue() != null &&
                fieldNameValidationResult.getValue().isValid();
        submitButtonEnabled.setValue(isFieldNameValid);
    }
}
