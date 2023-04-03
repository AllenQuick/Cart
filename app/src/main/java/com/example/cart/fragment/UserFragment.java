package com.example.cart.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cart.Adapter.BuyAdapter;
import com.example.cart.Bean.BuyBean;
import com.example.cart.Dao.UserDao;
import com.example.cart.MainActivity;
import com.example.cart.R;
import com.example.cart.databinding.FragmentUserBinding;
import com.example.cart.moudle.LoginState;
import com.example.cart.util.PostUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserFragment extends Fragment {
    private static final String TAG="user";
    private FragmentUserBinding binding;
    private UserDao userDao;
    private View root;
    public static ArrayList<BuyBean> arrayList;
    public static BuyAdapter buyAdapter;
    public String userNickName;

    @SuppressLint("SuspiciousIndentation")
    public static void freshView() {
        if(buyAdapter!=null)
        buyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentUserBinding.inflate(inflater,container,false);
        root=binding.getRoot();
        initData();
        initOnclick();
        return root;
    }
    public void initView(){
        userDao=new UserDao(getContext());
        arrayList=userDao.getUserHasBuy(MainActivity.userNum);
        if(arrayList.size()>0)
            if(arrayList.get(0).getPro_name().equals("nobuy"))
                arrayList.remove(0);
    }
    private void initData(){
        buyAdapter=new BuyAdapter(getContext(),arrayList);
        binding.lvHasBuy.setAdapter(buyAdapter);
    }
    private void initOnclick(){
        binding.btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.userNum="";
                MainActivity.userNumNeedChange=true;
                MainActivity.controller=false;
                ShopCartFragment.ArrayCart=null;
                arrayList.clear();
                SharedPreferences userNum=getActivity().getSharedPreferences("VALUE",0);
                SharedPreferences.Editor editor=userNum.edit();
                editor.putString("user_loginNum","");
                editor.commit();
                new Thread(){
                    @Override
                    public void run() {
                        Map<String,String> msg=new HashMap<>();
                        msg.put("user_loginNum",MainActivity.userNum);
                        msg.put("user_state","0");
                        PostUtil.sendPost("http://8.130.80.195/msg/loginstate.php",msg,"utf-8");
                    }
                }.start();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .hide(getActivity().getSupportFragmentManager().findFragmentByTag("user"))
                        .show(getActivity().getSupportFragmentManager().findFragmentByTag("login"))
                        .addToBackStack(null)
                        .commit();
                binding.lvHasBuy.setAdapter(null);
//                if(userDao.loginState(userDao.IsLogin(), LoginState.LOGINOUT)){
//                    getActivity().getSupportFragmentManager()
//                            .beginTransaction()
//                            .hide(getActivity().getSupportFragmentManager().findFragmentByTag("user"))
//                            .show(getActivity().getSupportFragmentManager().findFragmentByTag("login"))
//                            .addToBackStack(null)
//                            .commit();
//                    Toast.makeText(getContext(), "成功登出！", Toast.LENGTH_SHORT).show();
//                }
//                else
//                    Toast.makeText(getContext(),"网络异常！",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){}
        else {
            if(MainActivity.querryUserNickName) {
                binding.textUserName.setText(userDao.querryNickName(MainActivity.userNum));
                MainActivity.querryUserNickName=false;
                initView();
                initData();
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    public String theTag(){
        return this.TAG;
    }
}
