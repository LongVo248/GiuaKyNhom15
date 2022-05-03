package com.holo2k.giuakynhom15.error;

import android.text.TextUtils;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class CheckError {
    public static boolean checkEmptyEditText(EditText editText, TextInputLayout textInputLayout){
        if (TextUtils.isEmpty(editText.getText().toString())){
            textInputLayout.setError("Không được để trống");
            return false;
        }else {
            return true;
        }
    }

}
