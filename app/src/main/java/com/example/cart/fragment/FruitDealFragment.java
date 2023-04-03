package com.example.cart.fragment;



import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cart.Adapter.FruitDealAdapter;
import com.example.cart.Adapter.ShopAdapter;
import com.example.cart.Bean.ShopBean;
import com.example.cart.Dao.ShopDao;
import com.example.cart.Dao.UserDao;
import com.example.cart.MainActivity;
import com.example.cart.databinding.FragmentFruitdealBinding;
import com.example.cart.R;
import com.example.cart.util.EditNameChangedListener;

import java.util.ArrayList;

public class FruitDealFragment extends Fragment implements FruitDealAdapter.ListItemClickHelp {
    private FragmentFruitdealBinding binding;
    private View root;
    private ShopDao shopDao;
    private UserDao userDao;
    private static FruitDealAdapter fruitDealAdapter;
    private static ArrayList<ShopBean> shopbeanlist;
    private static final String TAG = "fruitfragment";

    public static void frashView() {
        fruitDealAdapter.notifyDataSetChanged();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding=FragmentFruitdealBinding.inflate(inflater,container,false);
        root=binding.getRoot();
        initView();
        initData();
        initOnClick();
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
            if(MainActivity.goodsNeedChange){
                initData();
                MainActivity.goodsNeedChange=false;
            }
        }
    }

    public void initData() {
        shopbeanlist=HomeFragment.shopbeanlist;
        fruitDealAdapter=new FruitDealAdapter(getContext(),shopbeanlist,this);
        binding.LvControlShopping.setAdapter(fruitDealAdapter);
    }
    private void initOnClick() {
        binding.LvControlShopping.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.textView.requestFocus();
                for(ShopBean item:shopbeanlist){
                    shopDao.updateGoods(item);
                }
                HomeFragment.frashView();
                fruitDealAdapter.notifyDataSetChanged();
            }
        });
        binding.editText.addTextChangedListener(new EditNameChangedListener(binding,this));
    }
    public void inTimeSearch(){
        shopbeanlist=shopDao.SearchTheFruitName(binding.editText.getText().toString());
        if(shopbeanlist!=null){
            FruitDealAdapter adapter1=new FruitDealAdapter(getContext(),shopbeanlist,FruitDealFragment.this);
            binding.LvControlShopping.setAdapter(adapter1);
        }
        else binding.LvControlShopping.setAdapter(null);
    }

    private void initView() {
        shopDao=new ShopDao(getContext());
        userDao=new UserDao(getContext());
        shopbeanlist=new ArrayList<>();

    }

    public static void saveEditData(int position, String str) {
        if(!str.equals(""))
            fruitDealAdapter.arrayList.get(position).setPro_shopPrice(Integer.parseInt(str));
        else
            fruitDealAdapter.arrayList.get(position).setPro_shopPrice(0);
        Log.e("FruitDealFragment",position+"|"+str);
    }
    public String theTag(){
        return this.TAG;
    }

    @Override
    public void onClick(View item, View widget, int position, int which) {
        TextView textSelect=item.findViewById(R.id.pro_count);
        TextView textResult=item.findViewById(R.id.pro_result);
        switch (which){
            case  R.id.btn_delete:
                shopDao.DeletGoods(fruitDealAdapter.arrayList.get(position).getPro_name());
                fruitDealAdapter.arrayList.remove(position);
                Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                fruitDealAdapter.notifyDataSetChanged();
                break;
            case R.id.pro_add:
                    textSelect.setText(String.valueOf(Integer.parseInt(String.valueOf(textSelect.getText())) +1));
                    fruitDealAdapter.arrayList.get(position).setPro_count(fruitDealAdapter.arrayList.get(position).getPro_count()+1);
                break;
            case R.id.pro_reduce:
                if(Integer.parseInt(textResult.getText().toString())+Integer.parseInt(textSelect.getText().toString())>0) {
                    textSelect.setText(String.valueOf(Integer.parseInt(String.valueOf(textSelect.getText())) - 1));
                    fruitDealAdapter.arrayList.get(position).setPro_count(fruitDealAdapter.arrayList.get(position).getPro_count() - 1);
                }
                else {
                    Toast.makeText(getContext(), "货已清空", Toast.LENGTH_SHORT).show();
                    shopbeanlist.get(position).setPro_count(0);
                }
                break;
            default:
                break;
        }

    }
}
