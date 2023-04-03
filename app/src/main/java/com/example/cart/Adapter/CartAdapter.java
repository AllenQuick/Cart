package com.example.cart.Adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cart.Bean.CartBean;
import com.example.cart.R;


import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {
    private Context context;
    public ArrayList<CartBean> arrayList;
    private ListItemClickHelp callback;
    private int sum=0;
    private Boolean selectAll=false;//用来存放CheckBox的选中状态，true为选中,false为没有选中
    private Boolean isShowDelete;
    public CartAdapter(Context context, ArrayList<CartBean> list, ListItemClickHelp callback,Boolean isShowDelete){
        this.arrayList=list;
        this.context=context;
        this.callback=callback;
        this.isShowDelete=isShowDelete;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater=LayoutInflater.from(this.context);
        ViewHolder holder =null;
        if(view==null) {
            view= layoutInflater.inflate(R.layout.adapter_cart_item, null);
            holder = new ViewHolder();
            holder.text_name = view.findViewById(R.id.cart_name);
            holder.text_shopPrice = view.findViewById(R.id.cart_shopPrice);
            holder.imageView = view.findViewById(R.id.cart_image);
            holder.text_Count = view.findViewById(R.id.cart_count);
            holder.pro_add=view.findViewById(R.id.pro_add);
            holder.pro_reduce=view.findViewById(R.id.pro_reduce);
            holder.ckb = view.findViewById(R.id.pro_checkbox);
            holder.text_total=view.findViewById(R.id.text_totalPrice);
            holder.text_delete=view.findViewById(R.id.text_delete);
            view.setTag(holder);
        }else
            holder= (ViewHolder) view.getTag();
        if(isShowDelete)
            holder.text_delete.setVisibility(view.VISIBLE);
        else
            holder.text_delete.setVisibility(view.GONE);
        holder.imageView.setImageResource(arrayList.get(position).getCart_picture());
        holder.text_name.setText("物品名称为：" + arrayList.get(position).getCart_name());
        holder.text_shopPrice.setText("单价为：" + arrayList.get(position).getCart_shopPrice());
        holder.text_Count.setText(String.valueOf(arrayList.get(position).getCart_count()));
        holder.ckb.setChecked(arrayList.get(position).isItem_check());
        holder.text_total.setText(String.valueOf(sum));
        final View v = view;
        final int p = position;
        final int one = holder.pro_add.getId();
        final int two = holder.pro_reduce.getId();
        final int three = holder.ckb.getId();
        final int four = holder.text_delete.getId();
        holder.pro_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vr) {
                callback.onClick(v,viewGroup,p,one);
            }
        });
        holder.pro_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vr) {
                callback.onClick(v,viewGroup,p,two);
            }
        });
        holder.ckb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vr) {
                callback.onClick(v,viewGroup,p,three);
            }
        });
        holder.text_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vr) {
                callback.onClick(v,viewGroup,p,four);
            }
        });
        return view;
    }
    public void setSum(int sum){
        this.sum=sum;
    }
    public void deleteState(Boolean fg){
        this.isShowDelete=fg;
    }
    public class ViewHolder{
        public TextView text_name;
        public TextView text_shopPrice;
        public TextView text_Count;
        public ImageView imageView;
        public TextView text_total;
        public Button pro_reduce;
        public Button pro_add;
        public TextView text_delete;
        public CheckBox ckb;
    }
    public interface ListItemClickHelp {
        void onClick(View item, View widget, int position, int which);
    }

}
