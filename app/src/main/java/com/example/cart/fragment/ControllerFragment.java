package com.example.cart.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cart.Adapter.FruitDealAdapter;
import com.example.cart.Bean.ShopBean;
import com.example.cart.Dao.ShopDao;
import com.example.cart.Dao.UserDao;
import com.example.cart.MainActivity;
import com.example.cart.R;
import com.example.cart.databinding.FragmentAddgoodsBinding;
import com.example.cart.moudle.LoginState;
import com.example.cart.util.EditInputNameChangedListener;
import com.example.cart.util.PostUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerFragment extends Fragment {
    private static final String TAG="controller";
    private View root;
    private FragmentAddgoodsBinding binding;
    private ShopDao shopDao;
    private UserDao userDao;
    private String usernum;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentAddgoodsBinding.inflate(inflater, container, false);
        initData();
        binding.editFrtype.setSelection(0);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){}
        else {
            if(MainActivity.userNumNeedChange) {
                usernum = MainActivity.userNum;
                MainActivity.userNumNeedChange=false;
            }
        }
    }
    public void inTimeSearch(){
        int i=0;
        Boolean selectFlag=false;
        Resources res = getResources () ;
        String [] planets = res.getStringArray(R.array.selectFruitType);
        for (String planet : planets) {
            i++;
            String[] str=planet.split("、");
            for (String s : str)
                if(binding.editFrname.getText().toString().contains(s)) {
                    binding.editFrtype.setSelection(i - 1);
                    selectFlag=true;
                }
        }
        if (!selectFlag){
            binding.editFrtype.setSelection(0);
            selectFlag=false;
        }
    }
    public String theTag(){
        return this.TAG;
    }
    private void initData() {
            root=binding.getRoot();
            userDao=new UserDao(getContext());
            shopDao=new ShopDao(getContext());
            usernum=MainActivity.userNum;
            binding.btLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.userNum="";
                    MainActivity.userNumNeedChange=true;
                    MainActivity.controller=false;
                    SharedPreferences userNum=getActivity().getSharedPreferences("VALUE",0);
                    SharedPreferences.Editor editor=userNum.edit();
                    editor.putString("user_loginNum","");
                    editor.commit();
                    new Thread(){
                        @Override
                        public void run() {
                            Map<String,String> msg=new HashMap<>();
                            msg.put("user_loginNum",usernum);
                            msg.put("user_state","0");
                            PostUtil.sendPost("http://8.130.80.195/msg/loginstate.php",msg,"utf-8");

                        }
                    }.start();
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .hide(getActivity().getSupportFragmentManager().findFragmentByTag("controller"))
                            .show(getActivity().getSupportFragmentManager().findFragmentByTag("login"))
                            .addToBackStack(null)
                            .commit();
                    Toast.makeText(getContext(), "成功登出！", Toast.LENGTH_SHORT).show();
//                    if (userDao.loginState(usernum, LoginState.LOGINOUT)) {
//
//
//                    }
//                    else
//                        Toast.makeText(getContext(),"网络异常！",Toast.LENGTH_SHORT).show();
                }
            });
            binding.btSubmitfruit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Nm=binding.editFrname.getText().toString();
                    String FType=binding.editFrtype.getSelectedItem().toString();
                    String Pric=binding.editFrprice.getText().toString();
                    String Num=binding.editFrcount.getText().toString();
                    if(Nm.equals("")  ||  FType.equals("请选择水果类型") || Pric.equals("") || Num.equals("")){
                        Toast.makeText(getContext(), "输入不合法！", Toast.LENGTH_SHORT).show();
                    }else {
                        List<ShopBean> shopBeans=HomeFragment.shopbeanlist;
                        Boolean tag=true;
                        for (ShopBean shopb: shopBeans) {
                            if(shopb.getPro_name().equals(Nm)) {
                                Toast.makeText(getContext(), "已存在该水果，请到首页去修改该水果信息", Toast.LENGTH_SHORT).show();
                                tag=false;
                                break;
                            }
                        }
                        if(tag){
                            shopDao.InsertGoods(Nm,FType,Pric,Num);
                            ShopBean shopBean=new ShopBean();
                            shopBean.setPro_name(Nm);
                            shopBean.setPro_type(FType);
                            shopBean.setPro_shopPrice(Integer.parseInt(Pric));
                            shopBean.setPro_count(Integer.parseInt(Num));
                            shopBean.setPro_picture(shopDao.setPicture(FType,Nm));
                            shopBeans.add(shopBean);
                            Toast.makeText(getContext(), "添加成功", Toast.LENGTH_LONG).show();
                            binding.editFrname.setText("");
                            binding.editFrcount.setText("");
                            binding.editFrprice.setText("");
                            binding.editFrtype.setSelection(0);
                            binding.editFrname.requestFocus();
                            FruitDealFragment.frashView();
                            HomeFragment.frashView();
                        }
                    }
                }
            });
            binding.editFrname.addTextChangedListener(new EditInputNameChangedListener(binding,this));
        }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        //super.onSaveInstanceState(outState);
    }
}
