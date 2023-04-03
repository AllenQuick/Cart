package com.example.cart.fragment;

import android.annotation.SuppressLint;

import android.content.Context;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alipay.sdk.app.EnvUtils;
import com.example.cart.Adapter.CartAdapter;
import com.example.cart.Alipay.OrderInfoUtil2_0;
import com.example.cart.Alipay.PayEasy;
import com.example.cart.Bean.BuyBean;
import com.example.cart.Bean.CartBean;
import com.example.cart.Bean.ShopBean;
import com.example.cart.Dao.ShopDao;
import com.example.cart.Dao.UserDao;
import com.example.cart.MainActivity;
import com.example.cart.R;
import com.example.cart.databinding.FragmentShopcartBinding;

import java.util.ArrayList;
import java.util.List;


public class ShopCartFragment extends Fragment implements CartAdapter.ListItemClickHelp{
    private static final String TAG="cart";
    private int id=1010642;
    private static FragmentShopcartBinding binding;
    private static ShopDao shopDao;
    private static UserDao userDao;
    private static String usernum;
    public static ArrayList<CartBean> ArrayCart;
    private static CartAdapter cartAdapter;
    private CheckBox ckb;
    private TextView cart_count;
    private TextView item_total;
    private int item_price;
    private int turn_mode=0;
    private static int sum_price=0;
    private View root;
    private PayEasy payEasy;
    private boolean isSelectedAll = true;//用来控制点击全选，全选和全不选相互切换
    private boolean isFirst=true;
    public static List<CartBean> cartfork=new ArrayList<>();
    public static Thread thread;
    private static Context context;
    @SuppressLint("SuspiciousIndentation")
    public static void frashView() {
        if(cartAdapter!=null)
            cartAdapter.notifyDataSetChanged();
        Log.e(TAG, "frashView: is frash");
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shopDao=new ShopDao(getContext());
        userDao=new UserDao(getContext());
        payEasy=new PayEasy(getContext(),getActivity());
        initView();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        if(isFirst){
            binding = FragmentShopcartBinding.inflate(inflater, container, false);
            Log.e(TAG, "init");
            initData();
            initOnclick();
            root = binding.getRoot();
        }
        context=getContext();
        return root;
    }

    private static void DealDelete() {
        thread=new Thread(){
            @Override
            public void run() {
                for (CartBean cartBean : cartfork){
                    Log.e(TAG, "onClick: after"+cartBean.getCart_name());
                            shopDao.deleteOneCart(cartBean.getCart_name(), MainActivity.userNum, cartBean.getItem_sum() - cartBean.getCart_count());
                    shopDao.upDateHasBuy(MainActivity.userNum,cartBean.getCart_name()+";"+cartBean.getCart_count()+";"+cartBean.getCart_shopPrice()*cartBean.getCart_count());
                    Log.e(TAG, "Refresh: success1");
                    for(ShopBean shopBean:HomeFragment.shopbeanlist)
                        if(shopBean.getPro_name().equals(cartBean.getCart_name())) {
                            shopBean.setPro_hasBuyCount(shopBean.getPro_hasBuyCount() + 1);
                            shopDao.updateGoodBuyCount(shopBean.getPro_name(),shopBean.getPro_hasBuyCount());
                            break;
                        }
                }
                cartfork.clear();
            }
        };
        thread.start();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }



    @Override
    public void onResume() {
        super.onResume();
//        if(!isFirst){
//            initView();
//            initData();
//            initOnclick();
//        }
        isFirst=false;
    }

    private void initView(){
        //cart_count=root.findViewById(R.id.cart_count);
        usernum= MainActivity.userNum;
        if(!usernum.equals("")) {
            ArrayCart = shopDao.querryCart(usernum, false);
            if(ArrayCart.get(0).getCart_name().equals(""))
                ArrayCart.clear();
            Log.e(TAG, "initView: "+usernum);
        }
        else
            ArrayCart=new ArrayList<>();
    }
    private void initData(){
        usernum= MainActivity.userNum;
        cartAdapter = new CartAdapter(getContext(), ArrayCart, this,showSatae());
        if(!MainActivity.controller)
        binding.cartShoppingListview.setAdapter(cartAdapter);
        else
            binding.cartShoppingListview.setAdapter(null);
        Log.e(TAG, "initData: +start");
      for (CartBean cartBean : ArrayCart) {
          Log.e(TAG, "initData: "+cartBean.getCart_name() );
      }
//        if(!usernum.equals(""))
//            if(!(shopDao.SelectUserCart(usernum))[0].equals("empty")){
//
//
//            }else {
//                binding.cartShoppingListview.setAdapter(null);
//            }
//        else
//            binding.cartShoppingListview.setAdapter(null);
    }
    private void initOnclick(){
        binding.selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: selectall"+binding);
                if(ArrayCart!=null &&  ArrayCart.size()>0 ){
                    if(binding.selectAll.isChecked()){
                        sumPrice();
                        setStateCheckedMap(true);
                        cartAdapter.notifyDataSetChanged();
                        binding.textSum.setText("合计："+sum_price);
                    }
                    else {
                        sum_price=0;
                        setStateCheckedMap(false);
                        cartAdapter.notifyDataSetChanged();
                        binding.textSum.setText("合计：0");
                    }
                }else
                    Toast.makeText(getContext(), "无物品", Toast.LENGTH_SHORT).show();
            }
        });
        binding.textEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ArrayCart!=null && ArrayCart.size()>0) {
                    if (turn_mode++ == 0)
                        cartAdapter.deleteState(true);
                    else {
                        cartAdapter.deleteState(false);
                        turn_mode = 0;
                    }
                    cartAdapter.notifyDataSetChanged();
                }
                else
                    Toast.makeText(getContext(), "无数据", Toast.LENGTH_SHORT).show();

            }
        });
        binding.jiesuanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sum_price>0) {
                    OrderInfoUtil2_0.setGoodsDetail(ArrayCart);
                    OrderInfoUtil2_0.setPrice(sum_price);
                    payEasy.payV2(v);
                    for (CartBean cartBean : ArrayCart) {
                        if(cartBean.isItem_check()){
                            cartfork.add(cartBean);
                            Log.e(TAG, "onClick: "+cartBean.getCart_name());
                        }
                    }
                    for (CartBean cartBean1 : cartfork) {
                        Log.e(TAG, "jiesuan："+cartBean1.getCart_name());
                        if(UserFragment.arrayList==null)
                            UserFragment.arrayList=new ArrayList<>();
                        BuyBean buyBean=new BuyBean();
                        buyBean.setId(id);
                        cartBean1.setId(id++);
                        buyBean.setPro_name(cartBean1.getCart_name());
                        buyBean.setPro_count(cartBean1.getCart_count());
                        buyBean.setPro_cost(cartBean1.getCart_count()*cartBean1.getCart_shopPrice());
                        UserFragment.arrayList.add(buyBean);
                        ArrayCart.remove(cartBean1);
                    }
                }
                else
                    Toast.makeText(getContext(),"请选择商品再进行结算",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public static void Refresh(Boolean isSuccess){
        Toast.makeText(context, "refresh", Toast.LENGTH_SHORT).show();
        if(isSuccess) {
            Log.e(TAG, "Refresh: success");
            DealDelete();
        }
        else {
            Log.e(TAG, "Refresh: fail");
            //shopDao.recoverHasBuy(MainActivity.userNum);
            for (CartBean cartBean : cartfork) {
                cartBean.setItem_check(false);
                ArrayCart.add(cartBean);
                ArrayList<BuyBean> ls=new ArrayList<>();
                for (BuyBean buyBean : UserFragment.arrayList) {
                    if(buyBean.getId()==cartBean.getId()){
                        ls.add(buyBean);
                        Log.e(TAG, "Refresh: "+buyBean.getPro_name());
                    }
                }
                if(ls.size()>0) {
                    UserFragment.arrayList.removeAll(ls);
                    Toast.makeText(context, "糟糕", Toast.LENGTH_SHORT).show();
                }
                ls.clear();
            }
            cartfork.clear();
        }
        sum_price=0;
        cartAdapter.setSum(0);
        binding.textSum.setText("合计：0");
        binding.selectAll.setChecked(false);
        UserFragment.freshView();
        MainActivity.navView.setSelectedItemId(R.id.navigation_home);
        Log.e(TAG, "Refresh: 线程+刷新结束");
    }
    public Boolean showSatae(){
        if(turn_mode==0)
            return false;
        else {
            return true;
        }
    }
    public void sumPrice(){
        sum_price=0;
        for (CartBean cartBean : ArrayCart) {
            sum_price+=cartBean.getCart_count()*cartBean.getCart_shopPrice();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
       // super.onSaveInstanceState(outState);
    }
    @Override
    public void onClick(View item, View widget, int position, int which) {
        cart_count=item.findViewById(R.id.cart_count);
        ckb = item.findViewById(R.id.pro_checkbox);
        item_total = item.findViewById(R.id.text_totalPrice);
        item_price=cartAdapter.arrayList.get(position).getCart_shopPrice();
        TextView shop_name=item.findViewById(R.id.cart_name);
        switch (which){
            case R.id.pro_add:
                if(cartAdapter.arrayList.get(position).getItem_sum()>Integer.parseInt(cart_count.getText().toString())){
                    cart_count.setText(String.valueOf(Integer.parseInt(String.valueOf(cart_count.getText()))+1));
                    cartAdapter.arrayList.get(position).setCart_count(cartAdapter.arrayList.get(position).getCart_count()+1);
                    if(ckb.isChecked()) {
                        item_total.setText(String.valueOf(Integer.parseInt((String) cart_count.getText()) * item_price));
                        sum_price+=item_price;
                        binding.textSum.setText("合计："+sum_price);
                    }
                }
                else
                    Toast.makeText(getContext(), "请到首页去添加水果到购物车", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pro_reduce:
                if(Integer.parseInt(cart_count.getText().toString())>0) {
                    cart_count.setText(String.valueOf(Integer.parseInt(String.valueOf(cart_count.getText())) - 1));
                    cartAdapter.arrayList.get(position).setCart_count(cartAdapter.arrayList.get(position).getCart_count()-1);
                    if(ckb.isChecked()) {
                        item_total.setText(String.valueOf(Integer.parseInt((String) cart_count.getText()) * item_price));
                        sum_price-=item_price;
                        binding.textSum.setText("合计："+sum_price);
                    }
                }
                else
                    Toast.makeText(getContext(), "水果已清空", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pro_checkbox:
                if(ckb.isChecked()){
                    item_total.setText(String.valueOf(Integer.parseInt((String) cart_count.getText())*item_price));
                    sum_price += Integer.parseInt((String) cart_count.getText())*item_price;
                    cartAdapter.arrayList.get(position).setItem_check(true);
                    binding.textSum.setText("合计："+sum_price);
                }
                else {
                    item_total.setText("0");
                    cartAdapter.arrayList.get(position).setItem_check(false);
                    sum_price -=Integer.parseInt((String) cart_count.getText())*item_price;
                    binding.textSum.setText("合计："+sum_price);
                }
                break;
            case R.id.text_delete:
                shopDao.deleteOneCart(String.valueOf(shop_name.getText()).split("：")[1],
                        MainActivity.userNum,
                        cartAdapter.arrayList.get(position).getItem_sum());
                if(cartAdapter.arrayList.get(position).isItem_check())
                    sum_price-=(cartAdapter.arrayList.get(position).getCart_count()*cartAdapter.arrayList.get(position).getCart_shopPrice());
                Log.e(TAG, "onClick: sum_pr"+sum_price);
                if(cartAdapter.arrayList.size()==1) {
                    binding.cartShoppingListview.setAdapter(null);
                    sum_price=0;
                }
                for (ShopBean shopBean : HomeFragment.shopbeanlist) {
                    if(shopBean.getPro_name().equals(cartAdapter.arrayList.get(position).getCart_name())){
                        Log.e(TAG, "sum: "+cartAdapter.arrayList.get(position).getItem_sum());
                        shopBean.setPro_count(shopBean.getPro_count()+cartAdapter.arrayList.get(position).getItem_sum());
                        break;
                    }
                }
                cartAdapter.arrayList.remove(position);
                Log.e(TAG, "cartAdapter.arrayList: "+cartAdapter.arrayList.size()+"@@@" );
                frashView();
                binding.textSum.setText("合计："+sum_price);
                HomeFragment.frashView();
                break;
            default:
                break;
        }
    }
//    private void selectAll() {
//        mCheckedData.clear();//清空之前选中数据
//        if (isSelectedAll) {
//            setStateCheckedMap(true);//将CheckBox的所有选中状态变成选中
//            isSelectedAll = false;
//            mCheckedData.addAll(ArrayCart);//把所有的数据添加到选中列表中
//        } else {
//            setStateCheckedMap(false);//将CheckBox的所有选中状态变成未选中
//            isSelectedAll = true;
//        }
//        cartAdapter.notifyDataSetChanged();
//    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){}
        else {
            if(MainActivity.userNumNeedChange) {
                if(ArrayCart!=null)
                    ArrayCart.clear();
                initView();
                initData();
                frashView();
                MainActivity.userNumNeedChange=false;
            }
            if(MainActivity.userCartNeedChange){
                if(ArrayCart!=null)
                    ArrayCart.clear();
                initView();
                initData();
                frashView();
                MainActivity.userCartNeedChange=false;
            }
        }
    }

    private void setStateCheckedMap(boolean isSelectedAll) {
        for (int i = 0; i < ArrayCart.size(); i++) {
            ArrayCart.get(i).setItem_check(isSelectedAll);
        }
    }
    public String theTag(){
        return this.TAG;
    }
}