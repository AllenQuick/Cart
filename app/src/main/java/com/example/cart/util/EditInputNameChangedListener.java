package com.example.cart.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.example.cart.databinding.FragmentAddgoodsBinding;
import com.example.cart.databinding.FragmentFruitdealBinding;
import com.example.cart.fragment.ControllerFragment;
import com.example.cart.fragment.FruitDealFragment;

public class EditInputNameChangedListener implements TextWatcher {
    private static final String TAG = "EditTypeInputNameChangedListener";
    private FragmentAddgoodsBinding fruitdealBinding;
    private CharSequence temp;//监听前的文本
    private ControllerFragment FruitFragment;
    public EditInputNameChangedListener(FragmentAddgoodsBinding FruitBinding, ControllerFragment fruitFragment) {
        this.fruitdealBinding = FruitBinding;
        this.FruitFragment=fruitFragment;
    }

    @Override

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Log.i(TAG, "输入文本之前的状态");
        temp = s;
    }

    @Override

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.i(TAG, "输入文字中的状态，count是一次性输入字符数");

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(fruitdealBinding.editFrname.getText().toString().equals(""))
            fruitdealBinding.editFrtype.setSelection(0);
        else {
            FruitFragment.inTimeSearch();
        }
    }
}
