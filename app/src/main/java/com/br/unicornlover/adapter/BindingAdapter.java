package com.br.unicornlover.adapter;

import android.view.View;
import android.widget.EditText;

import androidx.databinding.InverseBindingAdapter;

public class BindingAdapter {

    @androidx.databinding.BindingAdapter(value = "text")
    public static void setIntText(EditText editText, int text) {
        if (!editText.getText().toString().equals(String.valueOf(text))) {
            editText.setText(String.valueOf(text));
        }
    }

    @InverseBindingAdapter(attribute = "text", event = "android:textAttrChanged")
    public static int getIntText(EditText editText) {
        return Integer.parseInt(editText.getText().toString());
    }

    @androidx.databinding.BindingAdapter(value = "app:visibility")
    public static void setVisibility(View view, boolean isVisible) {
        view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

}