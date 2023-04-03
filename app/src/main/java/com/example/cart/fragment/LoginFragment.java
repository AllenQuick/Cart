package com.example.cart.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.cart.Dao.UserDao;
import com.example.cart.MainActivity;
import com.example.cart.RegisterActivity;
import com.example.cart.databinding.FragmentLoginBinding;
import com.example.cart.util.PostUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginFragment extends Fragment {
    private static final String TAG="login";

    private FragmentLoginBinding binding;
    private UserDao userDao;
    public static String lgnum;
    private String lgpass;
    private View root;
    private Fragment frag;
    private Fragment control;
    private Fragment user;
    String response="";
    private String strResult="";
    private boolean isFirstLoading;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        isFirstLoading=true;
        initData();
        return root;
    }
    private void initData() {
        root = binding.getRoot();
        userDao=new UserDao(getContext());
        frag=getActivity().getSupportFragmentManager().findFragmentByTag("login");
        control=getActivity().getSupportFragmentManager().findFragmentByTag("controller");
        user=getActivity().getSupportFragmentManager().findFragmentByTag("user");
        binding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lgnum=binding.editLgnum.getText().toString();
                lgpass=binding.editLgpass.getText().toString();
                if(lgnum.length()>0 && lgpass.length()>0)
                    new Thread()
                    {
                        @Override
                        public void run()
                        {
                            Map<String,String> map=new HashMap<String, String>();
                            map.put("num",lgnum);
                            map.put("user_password",lgpass);
                            //StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
                            response = PostUtil.sendPost(
                                    "http://8.130.80.195/msg/loginScan.php"
                                    ,map,"utf-8");
                            SharedPreferences userNum=getActivity().getSharedPreferences("VALUE",0);
                            SharedPreferences.Editor editor=userNum.edit();
                            switch (PostUtil.parseLoginJsonLoginScan(response)){
                                case 0:
                                    Looper.prepare();
                                    Toast.makeText(getContext(), "登陆失败，请重新登录！", Toast.LENGTH_LONG).show();
                                    Looper.loop();
                                    break;
                                case 1:
                                    MainActivity.userNum=lgnum;
                                    HomeFragment.usname=lgnum;
                                    MainActivity.userNumNeedChange=true;
                                    MainActivity.userCartNeedChange=true;
                                    MainActivity.querryUserNickName=true;
                                    editor.putString("user_loginNum",lgnum);
                                    editor.commit();
                                    getActivity().getSupportFragmentManager()
                                            .beginTransaction()
                                            .hide(frag)
                                            .show(user)
                                            .addToBackStack(null)
                                            .commit();
                                    break;
                                case 2:
                                    MainActivity.userNum=lgnum;
                                    MainActivity.userNumNeedChange=true;
                                    MainActivity.userCartNeedChange=true;
                                    MainActivity.controller=true;
                                    editor.putString("user_loginNum",lgnum);
                                    editor.commit();
                                    getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .hide(frag)
                                    .show(control)
                                    .addToBackStack(null)
                                    .commit();
                                    break;
                                default: break;
                            }
                       }
                    }.start();
                // 发送消息通知UI线程更新UI组件
//                Log.i("login",lgnum+"  "+lgpass);
//                if(lgpass.equals(userDao.CorrectOneWhenLogin(lgnum))){
//                    userDao.loginState(lgnum, LoginState.LOGININ);
//                    if(userDao.IsController(lgnum)){
//                        Toast.makeText(getContext(), "管理员登陆成功", Toast.LENGTH_SHORT).show();
//                        /*getActivity().getSupportFragmentManager().beginTransaction()
//                                .hide(fr)
//                                .show(to)
//                                .commit();*/
//                        getActivity().getSupportFragmentManager()
//                                .beginTransaction()
//                                .hide(frag)
//                                .show(control)
//                                .addToBackStack(null)
//                                .commit();
//                    }
//                    else{
//                        getActivity().getSupportFragmentManager()
//                                .beginTransaction()
//                                .hide(frag)
//                                .show(user)
//                                .addToBackStack(null)
//                                .commit();
//                        Toast.makeText(getContext(), "登陆成功", Toast.LENGTH_SHORT).show();}
//                }else
//                    Toast.makeText(getContext(), "登陆失败，请重新登录！", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getContext(), "请输入数据", Toast.LENGTH_SHORT).show();
            }
        });
        binding.btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rgintt=new Intent(getActivity(), RegisterActivity.class);
                startActivity(rgintt);
                getActivity().getSupportFragmentManager().beginTransaction().hide(frag).commit();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isFirstLoading) {
//如果不是第一次加载，刷新数据
        }
        isFirstLoading = false;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public String theTag(){
        return this.TAG;
    }
    /*userDao=new UserDao(this);
        EditText num=findViewById(R.id.edit_lgnum);
        EditText pass=findViewById(R.id.edit_lgpass);
        Button login=findViewById(R.id.bt_login);
        Button register=findViewById(R.id.bt_register);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lgnum=new String(num.getText().toString());
                lgpass=pass.getText().toString();
                Log.e("login",lgnum+"  "+lgpass);
                if(lgpass.equals(userDao.CorrectOneWhenLogin(lgnum))){
                    userDao.loginState(lgnum, LoginState.LOGININ);
                    if(userDao.IsController(lgnum)){
                        Toast.makeText(LoginActivity.this, "管理员登陆成功", Toast.LENGTH_SHORT).show();
                        Intent jumpAdd=new Intent(LoginActivity.this,AddGoodsActivity.class);
                        jumpAdd.putExtra("usernum",lgnum);
                        startActivity(jumpAdd);
                    }
                    else{
                        Intent intentL=new Intent(LoginActivity.this,SelfActivity.class);
                        intentL.putExtra("usernum",lgnum);
                        startActivity(intentL);
                        Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();}
                }else
                    Toast.makeText(LoginActivity.this, "登陆失败，请重新登录！", Toast.LENGTH_LONG).show();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rgintt=new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(rgintt);
            }
        });*/
}