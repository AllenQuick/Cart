package com.example.cart.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DBNAME="SHOP.db";
    public static final String CREATE_SHOP = "create table Shop_list("+
            "id integer primary key autoincrement,"+
            "pro_name text,"+
            "pro_type text,"+
            "pro_shopPrice integer,"+
            "pro_count integer"+
            ")";

    public static final String CREATE_USER="create table User_list("+
            "id integer primary key autoincrement,"+
            "user_name text,"+
            "user_loginNum text unique,"+
            "user_password text," +
            "user_iscontrol text," +//管理员
            "user_state integer default 0)";//登陆状态
    public static final String CREATE_USER_CART="create table User_cart("+
            "user_loginNum text primary key," +
            "user_fruit text default 'empty' )";
    public MyDatabaseHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SHOP);db.execSQL(CREATE_USER);
        db.execSQL(CREATE_USER_CART);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
