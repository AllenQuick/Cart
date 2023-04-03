package com.example.cart;


import static com.example.cart.moudle.LoginState.LOGINOUT;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.alipay.sdk.app.EnvUtils;
import com.example.cart.Dao.ShopDao;
import com.example.cart.Dao.UserDao;
import com.example.cart.databinding.FragmentLoginBinding;
import com.example.cart.fragment.FruitDealFragment;
import com.example.cart.fragment.LoginFragment;
import com.example.cart.fragment.ControllerFragment;
import com.example.cart.fragment.HomeFragment;
import com.example.cart.fragment.ShopCartFragment;
import com.example.cart.fragment.UserFragment;
import com.example.cart.util.PostUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.cart.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static boolean querryUserNickName;
    private ActivityMainBinding binding;
    private FragmentLoginBinding bindingLog;
    private HomeFragment homeFragment;
    private ShopCartFragment shopCartFragment;
    private LoginFragment loginFragment;
    private UserFragment userFragment;
    private ControllerFragment controllerFragment;
    private FruitDealFragment fruitDealFragment;
    private List<Fragment> fragments=new ArrayList<>();
    private Map<Fragment,String> fragTAG=new HashMap<>();
    public static BottomNavigationView navView;
    private UserDao userDao;
    private FragmentTransaction ft;
    public static String userNum;
    public static Boolean userNumNeedChange=false;
    public static Boolean goodsNeedChange=false;
    public static Boolean userCartNeedChange=false;
    public static Boolean buyfreshTag=false;
    public static Boolean controller=false;
    private SharedPreferences userNumShare;

    private SharedPreferences.Editor editor;
    private String[] fg=new String[]{"homeFragment","shopCartFragment","loginFragment","controllerFragment","userFragment"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Thread thread=new Thread(){
            @Override
            public void run() {
                UserDao.connect();
                ShopDao.connectMySql();
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        initData();
        initView();
        initOnClick();
       /* StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());*/
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new Thread(){
            @Override
            public void run() {
                ShopDao.closeMySql();
                UserDao.close();
            }
        }.start();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController controller = Navigation.findNavController(this,R.id.nav_host_fragment_activity_main);
        return controller.navigateUp();
    }
    private void setFragemnt(int pos) {
        ft = getSupportFragmentManager().beginTransaction();
        for (Fragment fragment : fragments) {
            if (!fragment.isAdded()) {
                ft.add(R.id.nav_host_fragment_activity_main,fragment,fragTAG.get(fragment));
            }
            ft.hide(fragment);
        }
        Log.e("MainActivity", fragTAG.get(fragments.get(pos)));
        ft.show(fragments.get(pos));
        ft.commitAllowingStateLoss();
    }

    private void initView(){
//        ft=getSupportFragmentManager().beginTransaction();
       /* ft.add(R.id.nav_host_fragment_activity_main,fragments.get(0),"home")
                .add(R.id.nav_host_fragment_activity_main,fragments.get(1),"shopcart")
                .add(R.id.nav_host_fragment_activity_main,fragments.get(2),"login")
                .add(R.id.nav_host_fragment_activity_main,fragments.get(3),"controller")
                .add(R.id.nav_host_fragment_activity_main,fragments.get(4),"user").commit();*/
        if(userNum.equals(""))
            setFragemnt(0);
        else {
            if (controller)
                setFragemnt(5);
            else
                setFragemnt(0);
        }
    }
    private void initData() {
        userNumShare = getSharedPreferences("VALUE", 0);
        userNum=userNumShare.getString("user_loginNum","");
        userDao=new UserDao(this);
        userNum=userDao.IsLogin(userNum);
        if(!userNum.equals("")) {
            controller = userDao.IsController(userNum);
            querryUserNickName=true;
        }else {
            querryUserNickName=false;
        }
        homeFragment=new HomeFragment();
        shopCartFragment = new ShopCartFragment();
        loginFragment = new LoginFragment();
        userFragment = new UserFragment();
        controllerFragment = new ControllerFragment();
        fruitDealFragment = new FruitDealFragment();
        bindingLog=FragmentLoginBinding.inflate(getLayoutInflater());
        fragments.add(homeFragment);
        fragments.add(shopCartFragment);
        fragments.add(loginFragment);
        fragments.add(controllerFragment);
        fragments.add(userFragment);
        fragments.add(fruitDealFragment);
        fragTAG.put(homeFragment, homeFragment.theTag());
        fragTAG.put(shopCartFragment,shopCartFragment.theTag());//1
        fragTAG.put(loginFragment,loginFragment.theTag());//2
        fragTAG.put(controllerFragment,controllerFragment.theTag());//3
        fragTAG.put(userFragment, userFragment.theTag());//4
        fragTAG.put(fruitDealFragment,fruitDealFragment.theTag());//5
    }

    private void initOnClick(){
        navView = findViewById(R.id.nav_view);
        navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.d("loginNUM", "onNavigationItemSelected: "+userNum);
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        if(userNum.equals("")) {
                            setFragemnt(0);
                        }else {
                            if (controller)
                                setFragemnt(5);
                            else
                                setFragemnt(0);
                        }
                        break;
                    case R.id.navigation_dashboard:
                        ShopCartFragment.frashView();//必须刷新，否则购买事件之后会有显示bug
                        setFragemnt(1);
                        break;
                    case R.id.navigation_notifications:
//                        new Thread(){
//                            @Override
//                            public void run() {
//                                Map<String,String> map=new HashMap<>();
//                                map.put("user_loginNum",userNum);
//                                String response=PostUtil.sendPost("http://8.130.80.195/msg/islogin.php",
//                                map,"utf-8");
//                                switch (PostUtil.parseLoginCheckJsonIsLogin(response)){
//                                    case 0:
//                                        setFragemnt(2);
//                                        break;
//                                    case 1:
//                                        setFragemnt(4);
//                                        break;
//                                    case 2:
//                                        setFragemnt(3);
//                                        break;
//                                    default:break;
//                                }
//                            }
//                        }.start();
//                        usernum=userDao.IsLogin();
                        if(userNum.equals("")){
                            setFragemnt(2);
                        }
                        else {
                            if (controller) {
                                setFragemnt(3);
                            }
                            else{
                                setFragemnt(4);
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }
    @SuppressLint("MissingSuperCall")
    public void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
       // super.onSaveInstanceState(outState);   //将这一行注释掉，阻止activity保存fragment的状态
    }
    @Override
    protected void onResume() {
        // 获取从其他Activity发送过来跳转Fragment的标志fragment_flag(名称随意)
        super.onResume();
        int fragmentFlag = this.getIntent().getIntExtra("fragment_flag", 0);
        if(fragmentFlag>0)
            userNum = "";
        switch (fragmentFlag) {
            case 1:
                // 控制跳转到底部导航项(navigation_home为该Fragment的对应控件的id值)
                setFragemnt(0);
                break;
            case 2:
                setFragemnt(1);
                break;
            case 3:
                setFragemnt(2);
                navView.setSelectedItemId(R.id.navigation_notifications);
                break;
        }
    }

}