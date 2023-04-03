package com.example.cart.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.cart.Bean.CartBean;
import com.example.cart.Bean.ShopBean;
import com.example.cart.DataBase.MySQLConnection;
import com.example.cart.R;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShopDao {
    private SQLiteDatabase sqLiteDatabase;
    private static Connection mySQLiteHelper=null;

    private  Context context;
    private Thread thread;
    private Thread insert2Cart;
    private Thread delete4Goods;
    public ShopDao(Context context) {
        this.context=context;
    }
    public static void connectMySql(){
        mySQLiteHelper = MySQLConnection.getConnection();
    }
    public static void closeMySql(){
        try {
            mySQLiteHelper.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static int setPicture(String type, String good_name){
        switch (type) {
            case "梨果"://苹果、沙果、海棠、野樱莓、枇杷、欧楂、山楂、梨（香梨、雪梨）、温柏、 蔷薇果、花楸。
                if (good_name.contains("苹果"))
                    return R.drawable.apple;
                if (good_name.contains("沙果"))
                    return R.drawable.chinesepearleaved;
                if (good_name.contains("海棠"))
                    return R.drawable.begonia;
                if (good_name.contains("野樱莓"))
                    return R.drawable.aronia;
                if (good_name.contains("枇杷"))
                    return R.drawable.loquat;
                if (good_name.contains("欧楂"))
                    return R.drawable.europhawthorn;
                if (good_name.contains("山楂"))
                    return R.drawable.hawthorn;
                if (good_name.contains("梨"))
                    return R.drawable.pear;
                if (good_name.contains("温柏"))
                    return R.drawable.wenbai;
                if (good_name.contains("蔷薇果"))
                    return R.drawable.rosefruit;
                if (good_name.contains("花楸"))
                    return R.drawable.sorbus;
            case "核果"://核果：杏、樱桃、桃（水蜜桃、油桃、蟠桃等）、李子、梅子（青梅）、西梅、白玉樱桃。
                if (good_name.contains("杏"))
                    return R.drawable.apricot;
                if (good_name.contains("白玉樱桃"))
                    return R.drawable.whitejade;
                if (good_name.contains("樱桃"))
                    return R.drawable.cherry;
                if (good_name.contains("水蜜桃"))
                    return R.drawable.honeypeach;
                if (good_name.contains("油桃"))
                    return R.drawable.nectarine;
                if (good_name.contains("蟠桃"))
                    return R.drawable.flatpeach;
                if (good_name.contains("李子"))
                    return R.drawable.plum;
                if (good_name.contains("梅子") || good_name.contains("青梅"))
                    return R.drawable.greenplum;
                if (good_name.contains("西梅"))
                    return R.drawable.prune;
            case "聚合核果"://黑莓、覆盆子、云莓、罗甘莓、白里叶莓
                if (good_name.contains("黑莓"))
                    return R.drawable.blackberries;
                if (good_name.contains("覆盆子"))
                    return R.drawable.raspberries;
                if (good_name.contains("云莓"))
                    return R.drawable.cloudberries;
                if (good_name.contains("罗甘莓"))
                    return R.drawable.roganberries;
                if (good_name.contains("白里叶莓"))
                    return R.drawable.baileyberries;
            case "瘦果"://草莓，菠萝莓
                if (good_name.contains("草莓"))
                    return R.drawable.strawberry;
                if (good_name.contains("菠萝莓"))
                    return R.drawable.pineappleberries;
            case "柑果"://橘子、砂糖桔、橙子、柠檬、青柠、柚子、金桔、葡萄柚、香橼、佛手、指橙、黄皮果
                if (good_name.contains("橘"))
                    return R.drawable.orange;
                if (good_name.contains("砂糖桔"))
                    return R.drawable.sugarorange;
                if (good_name.contains("橙子"))
                    return R.drawable.bigorange;
                if (good_name.contains("柠檬"))
                    return R.drawable.lemon;
                if (good_name.contains("青柠"))
                    return R.drawable.lime;
                if (good_name.contains("柚子"))
                    return R.drawable.grapefruit;
                if (good_name.contains("金桔"))
                    return R.drawable.kumquat;
                if (good_name.contains("葡萄柚"))
                    return R.drawable.grapesfruit;
                if (good_name.contains("香橼"))
                    return R.drawable.citron;
                if (good_name.contains("佛手"))
                    return R.drawable.bergamot;
                if (good_name.contains("指橙"))
                    return R.drawable.fingerorange;
                if (good_name.contains("黄皮果"))
                    return R.drawable.yellowpeelfruit;
            case "瓠果"://哈密瓜、香瓜、白兰瓜、刺角瓜、金铃子（癞葡萄）、香蕉、大蕉、南洋红香蕉、西瓜
                if (good_name.contains("哈密瓜"))
                    return R.drawable.hamimelon;
                if (good_name.contains("香瓜"))
                    return R.drawable.cantaloupe;
                if (good_name.contains("白兰瓜"))
                    return R.drawable.whiteorchidmelon;
                if (good_name.contains("刺角瓜"))
                    return R.drawable.kiwano;
                if (good_name.contains("金铃子") || good_name.contains("癞葡萄"))
                    return R.drawable.goldenbellmelon;
                if (good_name.contains("南洋红香蕉"))
                    return R.drawable.nanyangredbanana;
                if (good_name.contains("香蕉"))
                    return R.drawable.banana;
                if (good_name.contains("大蕉"))
                    return R.drawable.plantain;
                if (good_name.contains("西瓜"))
                    return R.drawable.watermelon;
            case "浆果"://葡萄、提子、醋栗、黑醋栗、红醋栗、蓝莓、蔓越莓、越橘、乌饭果、猕猴桃（奇异果）、黄心猕猴桃、软枣猕猴桃（奇异莓）、红心猕猴桃
                if (good_name.contains("葡萄"))
                    return R.drawable.grapes;
                if (good_name.contains("提子"))
                    return R.drawable.raisins;
                if (good_name.contains("黑醋栗"))
                    return R.drawable.blackcurrants;
                if (good_name.contains("红醋栗"))
                    return R.drawable.redcurrants;
                if (good_name.contains("醋栗"))
                    return R.drawable.gooseberries;
                if (good_name.contains("蓝莓"))
                    return R.drawable.blueberries;
                if (good_name.contains("蔓越莓"))
                    return R.drawable.cranberries;
                if (good_name.contains("越橘"))
                    return R.drawable.huckleberry;
                if (good_name.contains("乌饭果"))
                    return R.drawable.blackricefruits;
                if (good_name.contains("黄心猕猴桃"))
                    return R.drawable.yellowheartkiwifruit;
                if (good_name.contains("红心猕猴桃"))
                    return R.drawable.redheartkiwifruit;
                if (good_name.contains("软枣猕猴桃") || good_name.contains("奇异莓"))
                    return R.drawable.softjujubekiwifruit;
                if (good_name.contains("猕猴桃") || good_name.contains("奇异果"))
                    return R.drawable.kiwifruit;
            case "凤梨科"://菠萝、凤梨
                if (good_name.contains("菠萝"))
                    return R.drawable.pineapple;
                if (good_name.contains("凤梨"))
                    return R.drawable.fengli;
            case "柿科"://柿子、黑枣（君迁子）、黑柿
                if (good_name.contains("柿子"))
                    return R.drawable.persimmons;
                if (good_name.contains("黑枣") || good_name.contains("君迁子"))
                    return R.drawable.blackdates;
                if (good_name.contains("黑柿"))
                    return R.drawable.blackpersimmons;
            case "仙人掌科"://红心火龙果，白心火龙果，火龙果
                if (good_name.contains("红心火龙果"))
                    return R.drawable.redheartpitaya;
                if (good_name.contains("白心火龙果"))
                    return R.drawable.whiteheartpitaya;
                if (good_name.contains("火龙果"))
                    return R.drawable.pitaya;
            default:
                return R.drawable.light;
        }
    }
    @SuppressLint("Range")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public ArrayList querryGoods(){
        ArrayList<ShopBean> list;
        list=new ArrayList<ShopBean>();
        thread=new Thread(){
            @Override
            public void run() {
                PreparedStatement ps= null;
                try {
                    ps = mySQLiteHelper.prepareStatement("select * from Shop_list");
                    ResultSet rs = ps.executeQuery();
                    while(rs.next()){
                        ShopBean shopBean=new ShopBean();
                        shopBean.setId(rs.getInt("id"));
                        shopBean.setPro_name(rs.getString("pro_name"));
                        shopBean.setPro_count(rs.getInt("pro_count"));
                        shopBean.setPro_shopPrice(rs.getInt("pro_shopPrice"));
                        shopBean.setPro_type(rs.getString("pro_type"));
                        shopBean.setPro_hasBuyCount(rs.getInt("pro_hasBuyCount"));
                        String type=rs.getString("pro_type");
                        String gname=rs.getString("pro_name");
                        shopBean.setPro_picture(setPicture(type,gname));
                       /* switch (type){
                            case "梨果"://苹果、沙果、海棠、野樱莓、枇杷、欧楂、山楂、梨（香梨、雪梨）、温柏、 蔷薇果、花楸。
                                if(rs.getString("pro_name").contains("苹果")){
                                    shopBean.setPro_picture(R.drawable.apple);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("沙果")) {
                                    shopBean.setPro_picture(R.drawable.chinesepearleaved);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("海棠")) {
                                    shopBean.setPro_picture(R.drawable.begonia);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("野樱莓")) {
//                                    Looper.prepare();
//                                    Toast.makeText(context, "happy", Toast.LENGTH_SHORT).show();
//                                    Looper.loop();
                                    shopBean.setPro_picture(R.drawable.aronia);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("枇杷")) {

                                    shopBean.setPro_picture(R.drawable.loquat);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("欧楂")) {
                                    shopBean.setPro_picture(R.drawable.europhawthorn);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("山楂")) {
                                    shopBean.setPro_picture(R.drawable.hawthorn);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("梨")) {
//                                    Looper.prepare();
//                                    Toast.makeText(context, "happy", Toast.LENGTH_SHORT).show();
//                                    Looper.loop();
                                    shopBean.setPro_picture(R.drawable.pear);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("温柏")) {
                                    shopBean.setPro_picture(R.drawable.wenbai);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("蔷薇果")) {
                                    shopBean.setPro_picture(R.drawable.rosefruit);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("花楸")) {
                                    shopBean.setPro_picture(R.drawable.apple);
                                    break;
                                }else{
                                    shopBean.setPro_picture(R.drawable.sorbus);
                                    break;
                                }
                            case "核果"://核果：杏、樱桃、桃（水蜜桃、油桃、蟠桃等）、李子、梅子（青梅）、西梅、白玉樱桃。
                                if(rs.getString("pro_name").contains("杏")){
                                    shopBean.setPro_picture(R.drawable.apricot);
                                    break;}
                                else if(rs.getString("pro_name").contains("白玉樱桃")){
                                    shopBean.setPro_picture(R.drawable.whitejade);
                                    break;}
                                else if(rs.getString("pro_name").contains("樱桃")){
                                        shopBean.setPro_picture(R.drawable.cherry);
                                        break;}
                                else if(rs.getString("pro_name").contains("水蜜桃")){
                                        shopBean.setPro_picture(R.drawable.honeypeach);
                                        break;}
                                else if(rs.getString("pro_name").contains("油桃")){
                                    shopBean.setPro_picture(R.drawable.nectarine);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("蟠桃")){
                                    shopBean.setPro_picture(R.drawable.flatpeach);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("李子")){
                                    shopBean.setPro_picture(R.drawable.plum);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("梅子") || rs.getString("pro_name").contains("青梅") ){
                                    shopBean.setPro_picture(R.drawable.greenplum);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("西梅")){
                                    shopBean.setPro_picture(R.drawable.prune);
                                    break;
                                }else
                                    break;
                            case "聚合核果"://黑莓、覆盆子、云莓、罗甘莓、白里叶莓
                                if(rs.getString("pro_name").contains("黑莓")){
                                    shopBean.setPro_picture(R.drawable.blackberries);
                                    break;}
                                else if(rs.getString("pro_name").contains("覆盆子")){
                                    shopBean.setPro_picture(R.drawable.raspberries);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("云莓")){
                                    shopBean.setPro_picture(R.drawable.cloudberries);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("罗甘莓")){
                                    shopBean.setPro_picture(R.drawable.roganberries);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("白里叶莓")){
                                    shopBean.setPro_picture(R.drawable.baileyberries);
                                    break;
                                }else
                                    break;
                            case "瘦果"://草莓，菠萝莓
                                if(rs.getString("pro_name").contains("草莓")){
                                    shopBean.setPro_picture(R.drawable.strawberry);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("菠萝莓")){
                                    shopBean.setPro_picture(R.drawable.pineappleberries);
                                    break;
                                }
                                else
                                    break;
                            case "柑果"://橘子、砂糖桔、橙子、柠檬、青柠、柚子、金桔、葡萄柚、香橼、佛手、指橙、黄皮果
                                if(rs.getString("pro_name").contains("橘")){
                                    shopBean.setPro_picture(R.drawable.orange);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("砂糖桔")){
                                    shopBean.setPro_picture(R.drawable.sugarorange);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("橙子")){
                                    shopBean.setPro_picture(R.drawable.bigorange);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("柠檬")){
                                    shopBean.setPro_picture(R.drawable.lemon);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("青柠")){
                                    shopBean.setPro_picture(R.drawable.lime);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("柚子")){
                                    shopBean.setPro_picture(R.drawable.grapefruit);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("金桔")){
                                    shopBean.setPro_picture(R.drawable.kumquat);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("葡萄柚")){
                                    shopBean.setPro_picture(R.drawable.grapesfruit);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("香橼")){
                                    shopBean.setPro_picture(R.drawable.citron);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("佛手")){
                                    shopBean.setPro_picture(R.drawable.bergamot);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("指橙")){
                                    shopBean.setPro_picture(R.drawable.fingerorange);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("黄皮果")){
                                    shopBean.setPro_picture(R.drawable.yellowpeelfruit);
                                    break;
                                }
                                else
                                    break;
                            case "瓠果"://哈密瓜、香瓜、白兰瓜、刺角瓜、金铃子（癞葡萄）、香蕉、大蕉、南洋红香蕉、西瓜
                                if(rs.getString("pro_name").contains("哈密瓜")){
                                    shopBean.setPro_picture(R.drawable.hamimelon);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("香瓜")){
                                    shopBean.setPro_picture(R.drawable.cantaloupe);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("白兰瓜")){
                                    shopBean.setPro_picture(R.drawable.whiteorchidmelon);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("刺角瓜")){
                                    shopBean.setPro_picture(R.drawable.kiwano);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("金铃子") || rs.getString("pro_name").contains("癞葡萄")){
                                    shopBean.setPro_picture(R.drawable.goldenbellmelon);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("南洋红香蕉")){
                                    shopBean.setPro_picture(R.drawable.nanyangredbanana);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("香蕉")){
                                    shopBean.setPro_picture(R.drawable.banana);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("大蕉")){
                                    shopBean.setPro_picture(R.drawable.plantain);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("西瓜")){
                                    shopBean.setPro_picture(R.drawable.watermelon);
                                    break;
                                }else
                                    break;
                            case "浆果"://葡萄、提子、醋栗、黑醋栗、红醋栗、蓝莓、蔓越莓、越橘、乌饭果、猕猴桃（奇异果）、黄心猕猴桃、软枣猕猴桃（奇异莓）、红心猕猴桃
                                if(rs.getString("pro_name").contains("葡萄")){
                                    shopBean.setPro_picture(R.drawable.grapes);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("提子")){
                                    shopBean.setPro_picture(R.drawable.raisins);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("黑醋栗")){
                                    shopBean.setPro_picture(R.drawable.blackcurrants);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("红醋栗")){
                                    shopBean.setPro_picture(R.drawable.redcurrants);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("醋栗")){
                                    shopBean.setPro_picture(R.drawable.gooseberries);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("蓝莓")){
                                    shopBean.setPro_picture(R.drawable.blueberries);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("蔓越莓")){
                                    shopBean.setPro_picture(R.drawable.cranberries);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("越橘")){
                                    shopBean.setPro_picture(R.drawable.huckleberry);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("乌饭果")){
                                    shopBean.setPro_picture(R.drawable.blackricefruits);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("黄心猕猴桃")){
                                    shopBean.setPro_picture(R.drawable.yellowheartkiwifruit);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("红心猕猴桃")){
                                    shopBean.setPro_picture(R.drawable.redheartkiwifruit);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("软枣猕猴桃") || rs.getString("pro_name").contains("奇异莓")){
                                    shopBean.setPro_picture(R.drawable.softjujubekiwifruit);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("猕猴桃") || rs.getString("pro_name").contains("奇异果")){
                                    shopBean.setPro_picture(R.drawable.kiwifruit);
                                    break;
                                }
                                else break;
                            case "凤梨科"://菠萝、凤梨
                                if(rs.getString("pro_name").contains("菠萝")){
                                    shopBean.setPro_picture(R.drawable.pineapple);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("凤梨")){
                                    shopBean.setPro_picture(R.drawable.fengli);
                                    break;
                                }
                                else
                                    break;
                            case "柿科"://柿子、黑枣（君迁子）、黑柿
                                if(rs.getString("pro_name").contains("柿子")){
                                    shopBean.setPro_picture(R.drawable.persimmons);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("黑枣")||rs.getString("pro_name").contains("君迁子")){
                                    shopBean.setPro_picture(R.drawable.blackdates);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("黑柿")){
                                    shopBean.setPro_picture(R.drawable.blackpersimmons);
                                    break;
                                }
                                else
                                    break;
                            case "仙人掌科"://红心火龙果，白心火龙果，火龙果
                                if(rs.getString("pro_name").contains("红心火龙果")){
                                    shopBean.setPro_picture(R.drawable.redheartpitaya);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("白心火龙果")){
                                    shopBean.setPro_picture(R.drawable.whiteheartpitaya);
                                    break;
                                }
                                else if(rs.getString("pro_name").contains("火龙果")){
                                    shopBean.setPro_picture(R.drawable.pitaya);
                                    break;
                                }
                                else
                                    break;
                            default:
                                shopBean.setPro_picture(R.drawable.light);
                                break;
                        }*/
                        Log.e("test",shopBean.getId()+"|"+shopBean.getPro_name()+"|"+shopBean.getPro_type()+"|"+shopBean.getPro_count()+"|"+shopBean.getPro_shopPrice()+"|"+shopBean.getPro_hasBuyCount());
                        list.add(shopBean);
                    }
                    Log.e("ShopDao","没有数据了");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return list;
//        Cursor cursor=sqLiteDatabase.rawQuery("select * from Shop_list",null,null);
//        list=new ArrayList<ShopBean>();
//        Log.e("test","即将进入循环"+cursor.getCount());
//        while(cursor.moveToNext()){
//            ShopBean shopBean=new ShopBean();
//            shopBean.setId(cursor.getInt(cursor.getColumnIndex("id")));
//            shopBean.setPro_name(cursor.getString(cursor.getColumnIndex("pro_name")));
//            shopBean.setPro_count(cursor.getInt(cursor.getColumnIndex("pro_count")));
//            shopBean.setPro_shopPrice(cursor.getInt(cursor.getColumnIndex("pro_shopPrice")));
//            shopBean.setPro_type(cursor.getString(cursor.getColumnIndex("pro_type")));
//            Log.e("test",shopBean.getId()+"|"+shopBean.getPro_name()+"|"+shopBean.getPro_type()+"|"+shopBean.getPro_count()+"|"+shopBean.getPro_shopPrice());
//            list.add(shopBean);
//        }
//        return list;
    }
    public String getFruitType(String goodName){
        final String[] str = {""};
        thread=new Thread(){
            @Override
            public void run() {
                PreparedStatement ps= null;
                try {
                    ps=mySQLiteHelper.prepareStatement("select pro_type from Shop_list where pro_name = ?");
                    ps.setString(1,goodName);
                    ResultSet rs=ps.executeQuery();
                    Log.e("ShopDao", "run: "+ps);
                    rs.next();
                    str[0] =rs.getString("pro_type");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return str[0];
    }
    public ArrayList<CartBean> querryCart(String usname,Boolean tag){
        ArrayList<CartBean> carlist=new ArrayList<>();
        carlist.clear();
        thread=new Thread(){
            @Override
            public void run() {
                PreparedStatement ps= null;
                String sql="select * from User_cart where user_loginNum = "+usname;
                Log.e("querryCart", "run: "+usname);
                try {
                    ps=mySQLiteHelper.prepareStatement(sql);
                    ResultSet rs=ps.executeQuery();
                    rs.next();
                    if(rs.getRow()>0) {
                        String[] fruitCart = rs.getString("user_fruit").split(";");
                        if (fruitCart[0].equals("empty")) {
                            CartBean cartBean = new CartBean();
                            cartBean.setCart_name("");
                            carlist.add(cartBean);
                        } else {
                             Log.e("ShopDao", String.valueOf(fruitCart.length/3));
                            for (int i = 0; i < fruitCart.length; i++) {
                                CartBean cartBean = new CartBean();
                                cartBean.setCart_name(fruitCart[i++]);
                                cartBean.setCart_count(Integer.parseInt(fruitCart[i++]));
                                cartBean.setCart_shopPrice(Integer.parseInt(fruitCart[i]));
                                cartBean.setItem_sum(cartBean.getCart_count());
                                cartBean.setItem_check(tag);
                                cartBean.setCart_picture(setPicture(getFruitType(cartBean.getCart_name()),cartBean.getCart_name()));
                                //     Log.e("split", cartBean.getCart_name());
                                carlist.add(cartBean);
                            }
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return carlist;
//        Cursor cursor=sqLiteDatabase.
//                rawQuery("select * from User_cart where user_loginNum = ?",new String[]{usname},null);
//       // Log.e("ShopDao", String.valueOf(cursor.getCount()));
//        carlist=new ArrayList<>();
//        cursor.moveToFirst();
//        @SuppressLint("Range") String[] fruitCart=cursor.getString(cursor.getColumnIndex("user_fruit")).split(";");
//        if(fruitCart[0].equals("empty")){
//            CartBean cartBean=new CartBean();
//            cartBean.setCart_name("");
//            carlist.add(cartBean);
//            return carlist;}
//        else {
//            // Log.e("ShopDao",fruitCart[0]);
//            for(int i=0;i<fruitCart.length;i++){
//                CartBean cartBean = new CartBean();
//                cartBean.setCart_name(fruitCart[i++]);
//                cartBean.setCart_count(Integer.parseInt(fruitCart[i++]));
//                cartBean.setCart_shopPrice(Integer.parseInt(fruitCart[i]));
//                cartBean.setItem_sum(cartBean.getCart_count());
//                cartBean.setItem_check(tag);
//                //     Log.e("split", cartBean.getCart_name());
//                carlist.add(cartBean);
//            }
//            return carlist;
//        }
    }
    public void UpdateFruitCount(String name, int count) {
        thread=new Thread(){
            @Override
            public void run() {
                PreparedStatement ps= null;
                String sql="update Shop_list set pro_count = ? where pro_name = ?";
                try {
                    ps=mySQLiteHelper.prepareStatement(sql);
                    ps.setInt(1,count);
                    ps.setString(2,name);
                    ps.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        if(insert2Cart==null)
            thread.start();
        else {
            new Thread(){
                @Override
                public void run() {
                    while(insert2Cart.isAlive()){
                    }
                    thread.start();
                }
            }.start();
        }
//        ContentValues cv=new ContentValues();
//        cv.put("pro_count",count);
//        return sqLiteDatabase.update("Shop_list",cv,"pro_name = ?",new String[]{name});

    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public int sizeOfGoods(){//可能有bug，rs.next（）
        final int[] i = {0};
        thread=new Thread(){
            @Override
            public void run() {
                PreparedStatement ps=null;
                try {
                    ps=mySQLiteHelper.prepareStatement("select * from Shop_list");
                    ResultSet rs=ps.executeQuery();
                    i[0] =rs.getRow();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return i[0];
    }
    public void InsertGoods(String name,String type, String price,String num){
        thread=new Thread(){
            @Override
            public void run() {
                PreparedStatement ps=null;
                try {
                    ps=mySQLiteHelper.prepareStatement("insert into Shop_list(pro_name,pro_type,pro_count,pro_shopPrice) values(?,?,?,?)");
                    ps.setString(1,name);
                    ps.setString(2,type);
                    ps.setString(3,num);
                    ps.setString(4,price);
                    ps.executeUpdate();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        thread.start();
//        ContentValues contentValues=new ContentValues();
//        contentValues.put("pro_name",name);
//        contentValues.put("pro_type",type);
//        contentValues.put("pro_count",num);
//        contentValues.put("pro_shopPrice",price);
//        if(sqLiteDatabase.insert("Shop_list",null,contentValues)>-1)
//            return true;
//        return false;
    }
    public String[] SelectUserCart(String name){
        final String[][] cart = new String[1][1];
        thread=new Thread(){
            @Override
            public void run() {
                PreparedStatement ps=null;
                try {
                    ps=mySQLiteHelper.prepareStatement("select user_fruit from User_cart where user_loginNum=?");
                    ps.setString(1,name);
                    ResultSet rs=ps.executeQuery();
                    rs.next();
                    cart[0] =rs.getString("user_fruit").split(";");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return cart[0];
//        Cursor cursor=sqLiteDatabase.query("User_cart",new String[]{"user_fruit"},"user_loginNum = ?",new String[]{name},null,null,null);
//        cursor.moveToFirst();
//        if(cursor.getCount()>0) {
//            String[] cart=cursor.getString(0).split(";");
//            Log.e("SHOPDAO",cart[0]);
//            return cart;
//        }else
//            return null;
    }

    public void insertUser_fruitToCart(String cart,String usernum){
        Boolean tag=false;
        ArrayList<CartBean> carlist = new ArrayList<>();
        String sql="";
        carlist=querryCart(usernum,false);
        String cartNew="";
        if(carlist.get(0).getCart_name().equals(""))
            sql="update User_cart set user_fruit=\""+cart+"\" where user_loginNum="+usernum;
        else {
            for (CartBean cartBean : carlist) {
                if (cartBean.getCart_name().equals(cart.split(";")[0])) {
                    int count=cartBean.getCart_count()+Integer.parseInt(cart.split(";")[1]);
                    cartNew=cartNew+cartBean.getCart_name()+";"+count+";"+cartBean.getCart_shopPrice()+";";
                    tag=true;
                    continue;
                }else
                {
                    cartNew=cartNew+cartBean.getCart_name()+";"+cartBean.getCart_count()+";"+cartBean.getCart_shopPrice()+";";
                }
            }
            if(!tag)
                cartNew=cartNew+cart;
            Log.e("ShopDao",cartNew+"new");
            sql="update User_cart set user_fruit=\""+cartNew+"\" where user_loginNum="+usernum;
        }
        String finalSql = sql;
        insert2Cart=new Thread(){
            @Override
            public void run() {
                PreparedStatement ps=null;
                try {
                    Log.e("insertSql:",finalSql);
                    ps=mySQLiteHelper.prepareStatement(finalSql);
                    ps.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        insert2Cart.start();
//        Boolean tag=false;
//        ContentValues cv=new ContentValues();
//        ArrayList<CartBean> carlist = new ArrayList<>();
//        carlist=querryCart(usernum,false);
//        String cartNew="";
//        if(carlist.get(0).getCart_name().equals(""))
//            cv.put("user_fruit",cart);
//        else{
//            for (CartBean cartBean : carlist) {
//                if (cartBean.getCart_name().equals(cart.split(";")[0])) {
//                    int count=cartBean.getCart_count()+Integer.parseInt(cart.split(";")[1]);
//                    cartNew=cartNew+cartBean.getCart_name()+";"+count+";"+cartBean.getCart_shopPrice()+";";
//                    tag=true;
//                    continue;
//                }else
//                {
//                    cartNew=cartNew+cartBean.getCart_name()+";"+cartBean.getCart_count()+";"+cartBean.getCart_shopPrice()+";";
//                }
//            }
//            if(!tag)
//                cartNew=cartNew+cart;
//            Log.e("ShopDao",cartNew+"new");
//            cv.put("user_fruit",cartNew);
//        }
//
//        if(sqLiteDatabase.update("User_cart",cv,"user_loginNum = ?",new String[]{usernum})>0)
//            return true;
//        return false;
    }

    @SuppressLint("Range")
    public void deleteOneCart(String cart_name, String usnum, int count) {
        delete4Goods=new Thread(){
            @Override
            public void run() {
                PreparedStatement ps=null;
                try {
                    ps=mySQLiteHelper.prepareStatement("select pro_count from Shop_list where pro_name=?");
                    ps.setString(1,cart_name);
                    ResultSet rs=ps.executeQuery();
                    Log.d("123123123", "run: "+ps);
                    rs.next();
                    ps=mySQLiteHelper.prepareStatement("update Shop_list set pro_count=? where pro_name=?");
                    ps.setInt(1,count+rs.getInt("pro_count"));
                    ps.setString(2,cart_name);
                    ps.executeUpdate();
                    Log.d("123123123", "run: "+ps);
                    ArrayList<CartBean> carlist = new ArrayList<>();
                    String cart="";
                    carlist=querryCart(usnum,false);
                    if(carlist.size()>1) {
                        for (CartBean cartBean : carlist) {
                            if (cartBean.getCart_name().equals(cart_name)) {
                                Log.e("ShopDao", cart_name);
                                continue;
                            }
                            else{
                                cart=cart+cartBean.getCart_name()+";"+cartBean.getCart_count()+";"+cartBean.getCart_shopPrice()+";";
                            }
                        }
                    }else
                        cart="empty";
                    ps=mySQLiteHelper.prepareStatement("update User_cart set user_fruit=? where user_loginNum=?");
                    ps.setString(1,cart);
                    ps.setString(2,usnum);
                    ps.executeUpdate();
                    Log.e("ShopDao!!!", cart);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        delete4Goods.start();

//        Cursor cursor=sqLiteDatabase.rawQuery("select pro_count from Shop_list where pro_name=?",new String[]{cart_name});
//        cursor.moveToFirst();
//        ContentValues cvs=new ContentValues();
//        cvs.put("pro_count",count+cursor.getInt(cursor.getColumnIndex("pro_count")));
//        sqLiteDatabase.update("Shop_list",cvs,"pro_name=?",new String[]{cart_name});
//        ArrayList<CartBean> carlist = new ArrayList<>();
//        String cart="";
//        carlist=querryCart(usnum,false);
//        if(carlist.size()>1) {
//            for (CartBean cartBean : carlist) {
//                if (cartBean.getCart_name().equals(cart_name)) {
//                    Log.e("ShopDao", cart_name);
//                    continue;
//                }
//                else{
//                    cart=cart+cartBean.getCart_name()+";"+cartBean.getCart_count()+";"+cartBean.getCart_shopPrice()+";";
//                }
//            }
//        }else
//            cart="empty";
//        ContentValues cv=new ContentValues();
//        cv.put("user_fruit",cart);
//        Log.e("ShopDao!!!", cart);
//        sqLiteDatabase.update("User_cart",cv,"user_loginNum=?",new String[]{usnum});
//        return true;
    }
    @SuppressLint("Range")
//    public ArrayList<ShopBean> SearchTheFruitType(String FruitType){
//        String finalFruitType = FruitType;
//        if(!FruitType.equals("全部")) {
//            ArrayList<ShopBean> list;
//            list=new ArrayList<ShopBean>();
//            thread = new Thread() {
//                @Override
//                public void run() {
//                    PreparedStatement ps = null;
//                    try {
//                        ps = mySQLiteHelper.prepareStatement("select * from Shop_list where pro_type like ?");
//                        ps.setString(1, "%" + finalFruitType + "%");
//                        ResultSet rs = ps.executeQuery();
//                        while (rs.next()) {
//                            ShopBean shopBean = new ShopBean();
//                            shopBean.setId(rs.getInt("id"));
//                            shopBean.setPro_name(rs.getString("pro_name"));
//                            shopBean.setPro_type(rs.getString("pro_type"));
//                            shopBean.setPro_count(rs.getInt("pro_count"));
//                            shopBean.setPro_shopPrice(rs.getInt("pro_shopPrice"));
//                            Log.e("test", shopBean.getId() + "|" + shopBean.getPro_name() + "|" + shopBean.getPro_type() + "|" + shopBean.getPro_count() + "|" + shopBean.getPro_shopPrice());
//                            list.add(shopBean);
//                        }
//                    } catch (SQLException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            };
//            thread.start();
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            return list;
//        }
//        else
//            return querryGoods();
////        ArrayList<ShopBean> list;
////        String sql="select * from Shop_list where pro_type like '%"+FruitType+"%'";
////        Cursor cursor=sqLiteDatabase.rawQuery(sql,null);
////        if(cursor.getCount()>0){
////            list=new ArrayList<ShopBean>();
////            Log.e("test","即将进入循环"+cursor.getCount());
////            while(cursor.moveToNext()){
////                ShopBean shopBean=new ShopBean();
////                shopBean.setId(cursor.getInt(cursor.getColumnIndex("id")));
////                shopBean.setPro_name(cursor.getString(cursor.getColumnIndex("pro_name")));
////                shopBean.setPro_type(cursor.getString(cursor.getColumnIndex("pro_type")));
////                shopBean.setPro_count(cursor.getInt(cursor.getColumnIndex("pro_count")));
////                shopBean.setPro_shopPrice(cursor.getInt(cursor.getColumnIndex("pro_shopPrice")));
////                Log.e("test",shopBean.getId()+"|"+shopBean.getPro_name()+"|"+shopBean.getPro_type()+"|"+shopBean.getPro_count()+"|"+shopBean.getPro_shopPrice());
////                list.add(shopBean);
////            }
////            return list;
////        }
////        return null;
//    }
    public ArrayList<ShopBean> SearchTheFruitName(String FruitName){
        ArrayList<ShopBean> list;
        list=new ArrayList<ShopBean>();
        String sql="select * from Shop_list where pro_name like '%"+FruitName+"%'";
        thread=new Thread(){
            @Override
            public void run() {
                PreparedStatement ps=null;
                try {
                    ps=mySQLiteHelper.prepareStatement(sql);
                    ResultSet rs=ps.executeQuery();
                    while (rs.next()){
                        ShopBean shopBean=new ShopBean();
                        shopBean.setId(rs.getInt("id"));
                        shopBean.setPro_name(rs.getString("pro_name"));
                        shopBean.setPro_type(rs.getString("pro_type"));
                        shopBean.setPro_count(rs.getInt("pro_count"));
                        shopBean.setPro_shopPrice(rs.getInt("pro_shopPrice"));
                        Log.e("test",shopBean.getId()+"|"+shopBean.getPro_name()+"|"+shopBean.getPro_type()+"|"+shopBean.getPro_count()+"|"+shopBean.getPro_shopPrice());
                        list.add(shopBean);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return list;
//        Cursor cursor=sqLiteDatabase.rawQuery(sql,null);
//        if(cursor.getCount()>0){
//            Log.e("test","即将进入循环"+cursor.getCount());
//            while(cursor.moveToNext()){
//                ShopBean shopBean=new ShopBean();
//                shopBean.setId(cursor.getInt(cursor.getColumnIndex("id")));
//                shopBean.setPro_name(cursor.getString(cursor.getColumnIndex("pro_name")));
//                shopBean.setPro_type(cursor.getString(cursor.getColumnIndex("pro_type")));
//                shopBean.setPro_count(cursor.getInt(cursor.getColumnIndex("pro_count")));
//                shopBean.setPro_shopPrice(cursor.getInt(cursor.getColumnIndex("pro_shopPrice")));
//                Log.e("test",shopBean.getId()+"|"+shopBean.getPro_name()+"|"+shopBean.getPro_type()+"|"+shopBean.getPro_count()+"|"+shopBean.getPro_shopPrice());
//                list.add(shopBean);
//            }
//            return list;
//        }
//        return null;
    }

    public void updateGoods(ShopBean item) {
        thread=new Thread(){
            @Override
            public void run() {
                PreparedStatement ps=null;
                try {
                    ps=mySQLiteHelper.prepareStatement("update Shop_list set pro_shopPrice=?,pro_count=? where pro_name=? and pro_type=?");
                    ps.setInt(1,item.getPro_shopPrice());
                    ps.setInt(2,item.getPro_count());
                    ps.setString(3,item.getPro_name());
                    ps.setString(4,item.getPro_type());
                    ps.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        thread.start();
//        ContentValues cv=new ContentValues();
//        cv.put("pro_shopPrice",item.getPro_shopPrice());
//        cv.put("pro_count",item.getPro_count());
//        sqLiteDatabase.update("Shop_list",cv,"pro_name=? and pro_type=?",new String[]{item.getPro_name(),item.getPro_type()});
    }

    @SuppressLint("Range")
    public void DeletGoods(String pro_name){
        thread=new Thread(){
            @Override
            public void run() {
                PreparedStatement ps=null;
                String str ="";
                try {
                    ps=mySQLiteHelper.prepareStatement("select * from User_cart");
                    ResultSet rs = ps.executeQuery();
                    while(rs.next()){
                        if (rs.getString("user_fruit").contains(pro_name)){
                            str = rs.getString("user_fruit").split(pro_name + ";")[1];
                            Log.e("ShopDao", "delet:" + str);
                            str = str.split(";")[0];
                            String finalStr = str;
                            Thread thread1=new Thread(){
                                @Override
                                public void run() {
                                    try {
                                        deleteOneCart(pro_name, rs.getString("user_loginNum"), Integer.parseInt(finalStr));
                                    } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            };
                            thread1.start();
                            thread1.join();
                        }
                    }
                    ps=mySQLiteHelper.prepareStatement("delete from Shop_list where pro_name = ?");
                    ps.setString(1,pro_name);
                    ps.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        thread.start();



//        Cursor cursor=sqLiteDatabase.rawQuery("select * from User_cart",null,null);
//        String str ="";
//        cursor.moveToFirst();
//        while(cursor.moveToNext()){
//            if (cursor.getString(cursor.getColumnIndex("user_fruit")).contains(pro_name))
//                str= cursor.getString(cursor.getColumnIndex("user_fruit")).split(pro_name+";")[1];
//            Log.e("ShopDao","delet:"+str);
//                str=str.split(";")[0];
//                deleteOneCart(pro_name,cursor.getString(cursor.getColumnIndex("user_loginNum")), Integer.parseInt(str));
//        }
//        if(sqLiteDatabase.delete("Shop_list","pro_name = ?",new String[]{pro_name})>0)
//            return true;
//        else
//            return false;
    }

    public void upDateHasBuy(String useNum, String deal) {//购买记录
        thread=new Thread(){
            @Override
            public void run() {
                PreparedStatement ps=null;
                String str ="";
                try {
                    ps=mySQLiteHelper.prepareStatement("select user_hasBuy from User_list where user_loginNum=?");
                    ps.setString(1,useNum);
                    ResultSet rs=ps.executeQuery();
                    rs.next();
                    String buyList=rs.getString("user_hasBuy");
                    if(buyList.equals("nobuy"))
                        buyList=deal;
                    else
                        buyList=buyList+";"+deal;
                    ps=mySQLiteHelper.prepareStatement("update User_list set user_hasBuy=? where user_loginNum=?");
                    ps.setString(1,buyList);
                    ps.setString(2,useNum);
                    ps.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        thread.start();
    }

    public void recoverHasBuy(String userNum) {//移除购买记录
        thread=new Thread(){
            @Override
            public void run() {
                PreparedStatement ps=null;
                try {
                    ps=mySQLiteHelper.prepareStatement("select user_hasBuy from User_list where user_loginNum=?");
                    ps.setString(1,userNum);
                    ResultSet rs=ps.executeQuery();
                    rs.next();
                    String[] buyList=rs.getString("user_hasBuy").split(";");
                    String str="";
                    for(int i=0;i<buyList.length-3;i++){
                        if(i==0)
                            str += buyList[i];
                        else
                            str += ";"+buyList[i];
                    }
                    ps=mySQLiteHelper.prepareStatement("update User_list set user_hasBuy=? where user_loginNum=?");
                    ps.setString(1,str);
                    ps.setString(2,userNum);
                    ps.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    public void updateGoodBuyCount(String pro_name, int pro_hasBuyCount) {//更新水果购买次数，以便推荐算法的实现
        new Thread(){
            @Override
            public void run() {
                PreparedStatement ps=null;
                try {
                    ps=mySQLiteHelper.prepareStatement("update Shop_list set pro_hasBuyCount=? where pro_name=?");
                    ps.setInt(1,pro_hasBuyCount);
                    ps.setString(2,pro_name);
                    ps.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }.start();
    }
}
