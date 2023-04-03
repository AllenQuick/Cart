package com.example.cart.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.cart.Adapter.ShopAdapter;
import com.example.cart.Dao.UserDao;
import com.example.cart.databinding.FragmentHomeBinding;
import com.example.cart.fragment.HomeFragment;

public class EditTypeChangedListener implements TextWatcher {
    private static final String TAG = "EditTypeChangedListener";
    private FragmentHomeBinding homeBinding;
    private CharSequence temp;//监听前的文本
    private HomeFragment homeFragment;

    public EditTypeChangedListener(FragmentHomeBinding homeBinding, HomeFragment homeFragment) {
        this.homeBinding = homeBinding;
        this.homeFragment=homeFragment;
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
//        if(homeBinding.textInput.getText().toString().equals(""))
//            homeFragment.initData();
//        else {
//            homeFragment.inTimeSearch();
//        }
    }
}
