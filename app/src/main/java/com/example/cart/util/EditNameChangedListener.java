package com.example.cart.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.example.cart.databinding.FragmentFruitdealBinding;
import com.example.cart.databinding.FragmentHomeBinding;
import com.example.cart.fragment.FruitDealFragment;
import com.example.cart.fragment.HomeFragment;

public class EditNameChangedListener implements TextWatcher {
    private static final String TAG = "EditTypeChangedListener";
    private FragmentFruitdealBinding fruitdealBinding;
    private CharSequence temp;//监听前的文本
    private FruitDealFragment FruitFragment;
    public EditNameChangedListener(FragmentFruitdealBinding FruitBinding, FruitDealFragment fruitFragment) {
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
        if(fruitdealBinding.editText.getText().toString().equals(""))
            FruitFragment.initData();
        else {
            FruitFragment.inTimeSearch();
        }
    }
}
