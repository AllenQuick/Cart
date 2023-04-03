package com.example.cart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cart.Bean.BuyBean;
import com.example.cart.Bean.CartBean;
import com.example.cart.MainActivity;
import com.example.cart.R;
import com.example.cart.fragment.UserFragment;

import java.util.ArrayList;

public class BuyAdapter extends BaseAdapter {
    public ArrayList<BuyBean> arrayList;
    private Context context;

    public BuyAdapter(Context context,ArrayList<BuyBean> list){
        this.arrayList=list;
        this.context=context;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(this.context);
        ViewHolder viewHolder=null;
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.adaptor_buy_item,null);
            viewHolder=new ViewHolder();
            viewHolder.pro_name=convertView.findViewById(R.id.text_proName);
            viewHolder.pro_count=convertView.findViewById(R.id.text_proCount);
            viewHolder.pro_cost=convertView.findViewById(R.id.text_proCost);
            convertView.setTag(viewHolder);
        }else
            viewHolder= (ViewHolder) convertView.getTag();
        viewHolder.pro_name.setText(arrayList.get(position).getPro_name());
        viewHolder.pro_count.setText(String.valueOf(arrayList.get(position).getPro_count()));
        viewHolder.pro_cost.setText(String.valueOf(arrayList.get(position).getPro_cost()));
        return convertView;
    }
    public class ViewHolder{
        public TextView pro_name;
        public TextView pro_cost;
        public TextView pro_count;
    }
}
