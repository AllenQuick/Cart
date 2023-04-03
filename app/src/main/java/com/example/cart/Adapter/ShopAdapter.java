package com.example.cart.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cart.Bean.ShopBean;
import com.example.cart.R;


import java.util.ArrayList;

public class ShopAdapter extends BaseAdapter {
    private Context context;
    public ArrayList<ShopBean> arrayList;
    private ListItemClickHelp callback;

    public ShopAdapter(Context context, ArrayList<ShopBean> list, ListItemClickHelp callback){
        this.arrayList=list;
        this.context=context;
        this.callback=callback;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public ShopBean getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);//获取主界面的视图
        ViewHolder holder=null;
        if(convertView==null) {
            convertView = layoutInflater.inflate(R.layout.adaptor_shoppingcart_item, null);//绑定自己写的R.layout.drawer_list_item.xml文件
            holder=new ViewHolder();
            //String str = arrayList.get(position);
            holder.text_name =  convertView.findViewById(R.id.pro_name);
            holder.text_type=convertView.findViewById(R.id.pro_type);
            holder.text_shopPrice =  convertView.findViewById(R.id.pro_shopPrice);
            holder.imageView = convertView.findViewById(R.id.pro_image);
            holder.pro_result = convertView.findViewById(R.id.pro_result);
            holder.btn_add = convertView.findViewById(R.id.pro_add);
            holder.btn_reduce = convertView.findViewById(R.id.pro_reduce);
            holder.text_shopCount = convertView.findViewById(R.id.pro_count);
            holder.addToCart = convertView.findViewById(R.id.btn_addToDb);
            convertView.setTag(holder);
        }else
            holder= (ViewHolder) convertView.getTag();
        //imageView.getResource().getDrawable(R.drawable.bell);该方法不可用,应该用imageView.setImageResource(Int ResId)
        holder.imageView.setImageResource(arrayList.get(position).getPro_picture());
        holder.text_name.setText("水果名称:"+arrayList.get(position).getPro_name());
        holder.text_type.setText("水果类型:"+arrayList.get(position).getPro_type());
        holder.text_shopPrice.setText("单价为："+arrayList.get(position).getPro_shopPrice());
        holder.text_shopCount.setText("0");
        holder.pro_result.setText("剩余："+arrayList.get(position).getPro_count());
        //Log.e("ShopAdapter",String.valueOf(arrayList.get(position).getPro_count()));
        final View view = convertView;
        final int p = position;
        final int one = holder.addToCart.getId();
        final int two = holder.btn_add.getId();
        final int three = holder.btn_reduce.getId();
        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClick(view,parent,p,one);
            }
        });
        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClick(view,parent,p,two);
            }
        });
        holder.btn_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClick(view,parent,p,three);
            }
        });

        return convertView;
    }
    public void clear() {
        arrayList.clear();
        notifyDataSetChanged();
    }
    public interface ListItemClickHelp {
        void onClick(View item, View widget, int position, int which);
    }
public class ViewHolder{
    public TextView text_name;
    public TextView text_shopPrice;
    public TextView text_shopCount;
    public TextView pro_result;
    public ImageView imageView;
    public Button addToCart;
    public Button btn_add;
    public Button btn_reduce;
    public TextView text_type;

}
}

