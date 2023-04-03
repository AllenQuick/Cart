package com.example.cart.Dao;

import android.content.Context;
import android.util.Log;

import com.example.cart.Bean.BuyBean;
import com.example.cart.DataBase.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class UserDao {
   // private SQLiteDatabase sqLiteDatabase;
    private static Connection conn=null;
    private PreparedStatement ps= null;
    private Thread thread;
    private Context context;
    public UserDao(Context context) {
       // MyDatabaseHelper userDb= new MyDatabaseHelper(context);
       // sqLiteDatabase=userDb.getWritableDatabase();
        this.context=context;
        Log.e("user","连接到用户数据库");
    }
    public static void connect(){
        conn=MySQLConnection.getConnection();
        Log.e("UserDao", "close: 打开连接");
    }
    public static void close(){
        try {
            conn.close();
            Log.e("UserDao", "close: 关闭连接");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int InsertIntoUserDb(String user_name,String user_loginNum,String user_password,String user_iscontrol){
        final int[] item = {0};
        thread=new Thread(){
            @Override
            public void run() {
                try {
                    ps=conn.prepareStatement("insert into User_list(user_name,user_loginNum,user_password,user_iscontrol,user_state,user_hasBuy) values(?,?,?,?,0,'nobuy')");
                    ps.setString(1,user_name);
                    ps.setString(2,user_loginNum);
                    ps.setString(3,user_password);
                    ps.setString(4,user_iscontrol);
                    item[0] =ps.executeUpdate();
                    ps=conn.prepareStatement("insert into User_cart(user_loginNum) values(?)");
                    ps.setString(1,user_loginNum);
                    item[0] = item[0] *ps.executeUpdate();
                } catch (SQLException e){
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
        return item[0];

//        ContentValues values=new ContentValues();
//        ContentValues v=new ContentValues();
//        values.put("user_name",user_name);
//        values.put("user_loginNum",user_loginNum);
//        values.put("user_password",user_password);
//        values.put("user_iscontrol",user_iscontrol);
//        values.put("user_state",0);
//        long u_table=sqLiteDatabase.insert("User_list",null,values);
//        v.put("user_loginNum",user_loginNum);
//        long uC_table=sqLiteDatabase.insert("User_cart",null,v);
//        Log.d("usrdb","insert");
//        return u_table*uC_table;
    }
//    public String CorrectOneWhenLogin(String user_num){
//        String[] user=new String[]{user_num};
//        Cursor cursor=sqLiteDatabase.query("User_list",new String[]{"user_password"},"user_loginNum = ?",user,null,null,null);
//        cursor.moveToFirst();
//        if(cursor.getCount()>0)
//            return String.valueOf(cursor.getLong(0));
//        return (String) null;
//
//    }
    public boolean IsController(String user_num){
        final Boolean[] iscon = {false};
        thread=new Thread(){
            @Override
            public void run() {
                try {
                    ps=conn.prepareStatement("select user_iscontrol from User_list where user_loginNum=?");
                    ps.setString(1,user_num);
                    ResultSet rs=ps.executeQuery();
                    rs.next();
                    Log.e("iscontroller", "run: "+rs.getRow());
                    if(rs.getString("user_iscontrol").equals("000000"))
                        iscon[0] =true;
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

        return iscon[0];

//        String[] user=new String[]{user_num};
//        Cursor cursor=sqLiteDatabase.query("User_list",new String[]{"user_iscontrol"},"user_loginNum = ?",user,null,null,null);
//        cursor.moveToFirst();
//        Log.e("login",cursor.getString(0));
//        if(cursor.getString(0).equals("000000"))
//            return true;
//        else
//            return false;
    }
    public String IsLogin(String usernum){
        final String[] finalString = {""};
        thread=new Thread(){
            @Override
            public void run() {
                try {
                    ps=conn.prepareStatement("select user_loginNum from User_list where user_state = 1 and user_loginNum=?");
                    ps.setString(1,usernum);
                    ResultSet rs=ps.executeQuery();
                    rs.next();
                    if(rs.getRow()>0)
                        finalString[0] =rs.getString("user_loginNum");
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
        Log.e("isLogin:",finalString[0]);
        return finalString[0];
//        Cursor cursor=sqLiteDatabase.query("User_list",new String[]{"user_loginNum"},"user_state = 1",null,null,null,null);
//        cursor.moveToFirst();
//        if(cursor.getCount()>0) {
//            Log.e("shopcart", cursor.getString(0));
//            return cursor.getString(0);
//
//        }else{
//            Log.e("shopcart","没有用户登录");
//            return "";
//        }
    }

    public String querryNickName(String userNum) {
        final String[] NickName = {""};
        thread=new Thread(){
            @Override
            public void run() {
                try {
                    ps=conn.prepareStatement("select user_name from User_list where user_loginNum=?");
                    ps.setString(1,userNum);
                    ResultSet rs=ps.executeQuery();
                    rs.next();
                    if(rs.getRow()>0)
                        NickName[0] =rs.getString("user_name");
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
        return NickName[0];
    }

    public ArrayList<BuyBean> getUserHasBuy(String userNum) {
        ArrayList<BuyBean> arrayList=new ArrayList<>();
        thread=new Thread(){
            @Override
            public void run() {
                try {
                    ps=conn.prepareStatement("select user_hasBuy from User_list where user_loginNum=?");
                    ps.setString(1,userNum);
                    ResultSet rs=ps.executeQuery();
                    rs.next();
                    if(rs.getRow()>0) {
                        String[] strarr = rs.getString("user_hasBuy").split(";");
                        if (strarr[0].equals("nobuy")) {
                            BuyBean buyBean = new BuyBean();
                            buyBean.setPro_name("nobuy");
                            buyBean.setPro_count(0);
                            buyBean.setPro_cost(0);
                            arrayList.add(buyBean);
                        } else
                            for (int i = 0; i < strarr.length; i++) {
                                BuyBean buyBean = new BuyBean();
                                buyBean.setPro_name(strarr[i++]);
                                buyBean.setPro_count(Integer.parseInt(strarr[i++]));
                                buyBean.setPro_cost(Integer.parseInt(strarr[i]));
                                arrayList.add(buyBean);
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
        return arrayList;
    }

//    public boolean loginState(String usrnum,int state) {
//        thread=new Thread(){
//            @Override
//            public void run() {
//                conn= MySQLConnection.getConnection();
//                try {
//                    ps=conn.prepareStatement("");
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        };
//        thread.start();
//        try {
//            thread.join();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//
//
//
//        ContentValues cv=new ContentValues();
//        cv.put("user_state",state);
//        if(sqLiteDatabase.update("User_list",cv,"user_loginNum = ?",new String[]{usrnum})!=0)
//            return true;
//        else
//            return false;
//    }
}
