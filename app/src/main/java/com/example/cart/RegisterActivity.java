package com.example.cart;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTabHost;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.cart.Dao.ShopDao;
import com.example.cart.Dao.UserDao;
import com.example.cart.databinding.ActivityRegisterBinding;
import com.example.cart.util.MD5Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private String name=null;
    private String num=null;
    private String pass="";
    private String conTag=null;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        binding=ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Button backLogin=findViewById(R.id.bt_bc_login);
        Button registe=findViewById(R.id.bt_register);
        EditText getName=findViewById(R.id.edit_rgName);
        EditText getNum=findViewById(R.id.edit_rgNum);
        EditText getPass=findViewById(R.id.edit_rgPas);
        EditText getCon=findViewById(R.id.edit_iscontrl);
        UserDao userDao=new UserDao(this);
        backLogin.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                intent.putExtra("fragment_flag",3);
                startActivity(intent);
            }
        });
        registe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDao userDao=new UserDao(RegisterActivity.this);
                name=getName.getText().toString();
                num=getNum.getText().toString();
                pass=getPass.getText().toString();
                if(getCon.getText().toString().equals(""))
                    conTag="0";
                else
                    conTag=getCon.getText().toString();
                if(!name.equals("") && !num.equals("") && !pass.equals("")){
                    if(conTag.equals("000000")){
                        if(userDao.InsertIntoUserDb(name, num, MD5Util.stringMD5(pass),conTag)>0){
                            Toast.makeText(RegisterActivity.this, "管理员注册成功", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                            intent.putExtra("fragment_flag",3);
                            startActivity(intent);}
                        else
                            Toast.makeText(RegisterActivity.this, "人员登陆账号重复", Toast.LENGTH_SHORT).show();
                    }
                    else if(userDao.InsertIntoUserDb(name, num, MD5Util.stringMD5(pass),conTag)>0){
                        Toast.makeText(RegisterActivity.this, "用户注册成功", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                        intent.putExtra("fragment_flag",3);
                        startActivity(intent);}
                    else
                        Toast.makeText(RegisterActivity.this, "注册失败，请重新输入！", Toast.LENGTH_SHORT).show();
                    }
                else
                    Toast.makeText(RegisterActivity.this, "输入有误，请重新输入！", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
