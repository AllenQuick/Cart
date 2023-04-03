package com.example.cart.util;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class PostUtil {
    public static String sendPost(String url, Map<String, String> params, String encode) {
        String data = getRequestData(params, encode).toString();//获得请求体
        //System.out.print(data);
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //int code=conn.getResponseCode();
            //System.out.println(code+"!!!");
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(data);  // 向服务端输出参数
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static StringBuffer getRequestData(Map<String, String> params, String encode) {
        StringBuffer stringBuffer = new StringBuffer();        //存储封装好的请求体信息
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                stringBuffer.append(entry.getKey())
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), encode))
                        .append("&");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //删除最后的一个"&"
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer;
    }

    public static int parseLoginJsonLoginScan(String result) {
        String user_iscontrol = "";
        try {
            if (result.equals("\n登录失败！")) {
                return 0;
            } else {
                JSONObject jsonObject = new JSONObject(result).getJSONObject("list");
                String name = jsonObject.getString("user_loginNum");
                String pass = jsonObject.getString("user_password");
                user_iscontrol = jsonObject.getString("user_iscontrol");
            }
        } catch (JSONException js) {
            js.printStackTrace();
            System.out.print("Error");
        } catch (org.json.JSONException e) {
            throw new RuntimeException(e);
        }
        Log.e("PostUtil",user_iscontrol);
        if (user_iscontrol.equals("000000"))
            return 2;
        else
            return 1;
    }

    public static int parseLoginCheckJsonIsLogin(String result){
        Map<String,Map<String,String>> ms=new HashMap<>();
        if(result.equals("\n之前无用户登录"))
            return 0;
        else {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(result).getJSONObject("msg");
                String num = jsonObject.getString("user_loginNum");
                String iscontro=jsonObject.getString("user_iscontrol");
                if(!iscontro.equals("000000")) {
                    return 1;
                }else{
                    return 2;
                }
            } catch (org.json.JSONException e) {
                throw new RuntimeException(e);
            }

        }
    }
    public static String parseLoginNumJsonIsLogin(String result){
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(result).getJSONObject("msg");
                String num = jsonObject.getString("user_loginNum");
                return num;
            } catch (org.json.JSONException e) {
                throw new RuntimeException(e);
            }

        }

}
