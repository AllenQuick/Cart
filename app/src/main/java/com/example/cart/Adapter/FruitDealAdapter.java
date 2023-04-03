package com.example.cart.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.cart.Bean.ShopBean;
import com.example.cart.Dao.ShopDao;
import com.example.cart.Dao.UserDao;
import com.example.cart.R;
import com.example.cart.fragment.FruitDealFragment;

import java.util.ArrayList;

public class FruitDealAdapter extends BaseAdapter {
    private Context context;
    public ArrayList<ShopBean> arrayList;
    private ListItemClickHelp callback;

    public FruitDealAdapter(Context context, ArrayList<ShopBean> list, ListItemClickHelp callback){
        this.arrayList=list;
        this.context=context;
        this.callback=callback;
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
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);//获取主界面的视图
        ViewHolder holder=null;
        if(convertView==null) {
            convertView = layoutInflater.inflate(R.layout.adapter_control, null);//绑定自己写的R.layout.drawer_list_item.xml文件
            holder=new ViewHolder(convertView,position);
            /*String str = arrayList.get(position);
            holder.text_name =  convertView.findViewById(R.id.pro_name);
            holder.text_shopPrice =  convertView.findViewById(R.id.col_shopPrice);
            holder.imageView = convertView.findViewById(R.id.pro_image);
            holder.pro_result = convertView.findViewById(R.id.pro_result);
            holder.btn_add = convertView.findViewById(R.id.pro_add);
            holder.btn_reduce = convertView.findViewById(R.id.pro_reduce);
            holder.text_shopCount = convertView.findViewById(R.id.pro_count);
            holder.btn_delete = convertView.findViewById(R.id.btn_delete);*/
            convertView.setTag(holder);
        }else
            holder= (ViewHolder) convertView.getTag();
        //imageView.getResource().getDrawable(R.drawable.bell);该方法不可用,应该用imageView.setImageResource(Int ResId)
        holder.imageView.setImageResource(arrayList.get(position).getPro_picture());
        holder.text_name.setText("水果名称:"+arrayList.get(position).getPro_name());
        holder.text_shopPrice.setHint("单价为:"+arrayList.get(position).getPro_shopPrice());
        holder.pro_result.setText(String.valueOf(arrayList.get(position).getPro_count()));
        holder.text_shopCount.setText("0");
        //Log.e("ShopAdapter",String.valueOf(arrayList.get(position).getPro_count()));
        final View view = convertView;
        final int p = position;
        final int one = holder.btn_delete.getId();
        final int two = holder.btn_add.getId();
        final int three = holder.btn_reduce.getId();
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
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
//        holder.text_shopPrice.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if(!s.equals(arrayList.get(position).getPro_shopPrice())){
//                    arrayList.get(position).setPro_shopPrice(Integer.parseInt(String.valueOf(s)));
//                }
//            }
//        });
        return convertView;
    }
    public interface ListItemClickHelp {
        void onClick(View item, View widget, int position, int which);
    }
    public class ViewHolder{
        public TextView text_name;
        public EditText text_shopPrice;
        public TextView text_shopCount;
        public TextView pro_result;
        public ImageView imageView;
        public Button btn_delete;
        public Button btn_add;
        public Button btn_reduce;
        public ViewHolder(View convertView,int pisition){
            text_name = (TextView) convertView.findViewById(R.id.pro_name);
            text_shopPrice= (EditText) convertView.findViewById(R.id.col_shopPrice);
            imageView = convertView.findViewById(R.id.pro_image);
            pro_result = convertView.findViewById(R.id.pro_result);
            btn_add = convertView.findViewById(R.id.pro_add);
            btn_reduce = convertView.findViewById(R.id.pro_reduce);
            text_shopCount = convertView.findViewById(R.id.pro_count);
            btn_delete = convertView.findViewById(R.id.btn_delete);
            text_shopPrice.setTag(pisition);//存tag值
            text_shopPrice.addTextChangedListener(new TextSwitcher(this));
        }


    }
    class TextSwitcher implements TextWatcher {
        private ViewHolder mHolder;

        public TextSwitcher(ViewHolder mHolder) {
            this.mHolder = mHolder;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int position = (int) mHolder.text_shopPrice.getTag();//取tag值
            FruitDealFragment.saveEditData(position, s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
