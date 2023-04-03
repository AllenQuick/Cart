package com.example.cart.DataBase;

import android.util.Log;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MySQLConnection {
    static final String URL = "jdbc:mysql://8.130.80.195:3306/Fruit?useUnicode=true&characterEncoding=utf-8&useSSL=false";//?characterEncoding=utf8&useSSL=false
    static final String USER = "root";
    static final String PASSWORD = "520MYMya";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            DriverManager.setLoginTimeout(10);
            conn = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Log.e("MySQLConnection", "连接数据库成功");
        return conn;
    }


//    public static Connection createDBC() {
//        java.sql.Connection conn = null;
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            Log.v("111", "加载JDBC驱动成功");
//            // 2.设置好IP/端口/数据库名/用户名/密码等必要的连接信息
//            String ip = "8.130.80.195";
//            int port = 3306;
//            String dbName = "Fruit";
//            String url = "jdbc:mysql://8.130.80.195:3306/Fruit?serverTimezone=UTC&?useUnicode=true&characterEncoding=utf8&useSSL=false";
//            // 构建连接mysql的字符串
//            String user = "root";
//            String password = "520MYMya";
//            // 3.连接JDBC
//            DriverManager.setLoginTimeout(10);
//            conn = DriverManager.getConnection(url, user, password);
//            Log.e("111", "createDBC: " + conn);
//            Log.d("111", "数据库连接成功");
//
//        } catch (SQLException e) {
//            Log.e("111", e.getMessage());
//        } catch (ClassNotFoundException ex) {
//            throw new RuntimeException(ex);
//        }
//        return conn;
//
//    }
}
