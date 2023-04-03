package com.example.cart.fragment;

import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cart.Adapter.CartAdapter;
import com.example.cart.Adapter.ShopAdapter;
import com.example.cart.Bean.CartBean;
import com.example.cart.Bean.ShopBean;
import com.example.cart.Dao.ShopDao;
import com.example.cart.Dao.UserDao;
import com.example.cart.DataBase.MyDatabaseHelper;
import com.example.cart.MainActivity;
import com.example.cart.R;
import com.example.cart.databinding.FragmentHomeBinding;
import com.example.cart.util.EditTypeChangedListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements ShopAdapter.ListItemClickHelp{
    private static final String TAG = "home";
    private View root;
    private static ShopAdapter adapter;
    private ShopDao shopDao;
    private UserDao userDao;
    public static ArrayList<ShopBean> shopbeanlist;
    public static String usname;
    private TextView textSelect;
    private CheckBox ckb;

    private FragmentHomeBinding binding;
    private Boolean isfirst=true;

    public static void frashView() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        initData();
        initOnClick();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!isfirst) {
            initData();
            initOnClick();
        }
        isfirst=false;
    }

    private void initOnClick() {
        binding.LvCartShopping.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        binding.spinSelectType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(shopbeanlist!=null){
                    String type=binding.spinSelectType.getSelectedItem().toString();
                    if(type.equals("全部")) {
                        if(!isfirst)
                            binding.LvCartShopping.setAdapter(adapter);
                    }
                    else
                        setSelect(type);
                }
                else binding.LvCartShopping.setAdapter(null);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
       // binding.textInput.addTextChangedListener(new EditTypeChangedListener(binding,this));
    }
    public void setSelect(String type){
        ArrayList<ShopBean> list=new ArrayList<>();
        list.clear();
        for (ShopBean shopBean : shopbeanlist)
            if(shopBean.getPro_type().equals(type))
                list.add(shopBean);
        ShopAdapter adapter1=new ShopAdapter(getContext(),list,HomeFragment.this);
        binding.LvCartShopping.setAdapter(adapter1);

    }
//    public void inTimeSearch(){
//        shopbeanlist=shopDao.SearchTheFruitType(binding.textInput.getText().toString());
//        if(shopbeanlist!=null){
//            ShopAdapter adapter1=new ShopAdapter(getContext(),shopbeanlist,HomeFragment.this);
//            binding.LvCartShopping.setAdapter(adapter1);
//        }
//        else binding.LvCartShopping.setAdapter(null);
//    }
    private void initView() {
        usname = MainActivity.userNum;
        shopDao = new ShopDao(getActivity());
        userDao = new UserDao(getActivity());
        shopbeanlist = shopDao.querryGoods();
    }
    public void initData() {
        usname = MainActivity.userNum;
        root = binding.getRoot();
        adapter = new ShopAdapter(getContext(),shopbeanlist,this);
        Log.e("HomeFragment",shopbeanlist.toString());
        binding.LvCartShopping.setAdapter(adapter);
//        adapter = new ShopAdapter(getContext(),shopbeanlist,this);
//        binding.LvCartShopping.setAdapter(adapter);

    }
    @Override
    public void onClick(View item, View widget, int position, int which) {
        textSelect=item.findViewById(R.id.pro_count);
        ckb=item.findViewById(R.id.pro_checkbox);
        switch (which) {
            case R.id.btn_addToDb:
                if (!usname.equals("")){
                    String name = adapter.arrayList.get(position).getPro_name();
                    Log.e("HomeFragment", name);
                    int count=Integer.parseInt(textSelect.getText().toString());
                    if(count<=0) {
                        Toast.makeText(getContext(), "所选水果数量为0，不能添加", Toast.LENGTH_SHORT).show();
                        break;
                    }else {
                        int price = adapter.arrayList.get(position).getPro_shopPrice();
                        final String cart = name + ";" + count + ";" + price + ";";
                        Log.e("HomeFragment Insert", cart);
                        shopDao.insertUser_fruitToCart(cart, usname);
                        shopDao.UpdateFruitCount(name,adapter.arrayList.get(position).getPro_count()-count);
                        adapter.arrayList.get(position).setPro_count(adapter.arrayList.get(position).getPro_count()-count);
                        Boolean tag=false;
                        if(ShopCartFragment.ArrayCart!=null)
                            for (CartBean cart1:ShopCartFragment.ArrayCart) {
                                if(cart1.getCart_name().equals(name)) {
                                    cart1.setCart_count(cart1.getCart_count() + count);
                                    tag=true;
                                }
                            }
                        else{
                            ShopCartFragment.ArrayCart=new ArrayList<>();
                        }
                        if(!tag){
                            CartBean cart2=new CartBean();
                            cart2.setCart_name(name);
                            cart2.setCart_shopPrice(price);
                            cart2.setCart_count(count);
                            cart2.setItem_sum(count);
                            cart2.setItem_check(false);
                            cart2.setCart_picture(adapter.arrayList.get(position).getPro_picture());
                            ShopCartFragment.ArrayCart.add(cart2);
                        }
                        Log.e(TAG, "onClick: {{{"+ShopCartFragment.ArrayCart.size());
                        frashView();
                        Toast.makeText(getActivity(), "加入购物车成功", Toast.LENGTH_LONG).show();
                        if(ShopCartFragment.ArrayCart.size()>1)
                            ShopCartFragment.frashView();
                        else
                            MainActivity.userCartNeedChange=true;
                    }
                }else
                    Toast.makeText(getActivity(), "没有用户登录，请先登录", Toast.LENGTH_LONG).show();
                break;
            case R.id.pro_add:
                int goodCount=adapter.arrayList.get(position).getPro_count();
                if(Integer.parseInt(String.valueOf(textSelect.getText()))<goodCount)
                    textSelect.setText(String.valueOf(Integer.parseInt(String.valueOf(textSelect.getText())) +1));
                else
                    Toast.makeText(getActivity(),"商品已售罄！",Toast.LENGTH_LONG);
                break;
            case R.id.pro_reduce:
                if(Integer.parseInt(textSelect.getText().toString())>0)
                    textSelect.setText(String.valueOf(Integer.parseInt(String.valueOf(textSelect.getText()))-1));
                else
                    Toast.makeText(getActivity(),"数量已到0",Toast.LENGTH_LONG);
                break;
            default:
                break;
        }
    }
    public String theTag(){
        return this.TAG;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){

        }
        else {
            if(!isfirst) {
                if (MainActivity.userNumNeedChange) {
                    usname = MainActivity.userNum;
                    MainActivity.userNumNeedChange = false;
                }
                if (MainActivity.goodsNeedChange) {
                    shopbeanlist = shopDao.querryGoods();
                    Log.e("HomeFragment", shopbeanlist.toString());
                    adapter = new ShopAdapter(getContext(), shopbeanlist, this);
                    binding.LvCartShopping.setAdapter(adapter);
                    MainActivity.goodsNeedChange = false;
                }
            }
        }
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
}