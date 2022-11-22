package com.example.a317soft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.a317soft.adapter.LoadingDialog;
import com.example.a317soft.bean.User;
import com.example.a317soft.util.DB_Pool;
import com.example.a317soft.util.UserDB;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    TextView btn_toReg,btn_login;
    EditText et_username,et_password;
    String username,password;
    List<User> users = new ArrayList<>();
    int id;
    LoadingDialog loadingDialog;
    /**
     * 此页bundle需要携带跳转参数
     * int: user_id  用户id
     * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //获取控件
        DB_Pool.init();
        btn_toReg = findViewById(R.id.toReg);
        btn_login=findViewById(R.id.toLogin);
        et_username=findViewById(R.id.username);
        et_password=findViewById(R.id.password);
        loadingDialog = new LoadingDialog(this);
        btn_toReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username=et_username.getText().toString();
                password=et_password.getText().toString();
                boolean flag = false;
                if(checkInput()) {
                    loadingDialog.show();
                    Thread th= new Thread(new Runnable() {
                        @Override
                        public void run() {
                            users = UserDB.readUsers();
                        }
                    });
                    th.start();
                    try {
                        th.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    loadingDialog.dismiss();
                    for(User user : users) {
                        //如果可以找到,则输出登录成功,并跳转到主界面
                        if(user.getUsername().equals(username.trim()) && user.getPassword().equals(password.trim())) {
                            flag = true;
                            Toast.makeText(LoginActivity.this,"恭喜你登录成功!",Toast.LENGTH_SHORT).show();
                            Thread th1= new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    id = UserDB.findId(username);
                                }
                            });
                            th1.start();
                            try {
                                th1.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(LoginActivity.this,HomePageActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("user_id",id);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }
                    }
                    //否则提示登录失败,需要重新输入
                    if (!flag) {
                        Toast.makeText(LoginActivity.this,"学号或密码输入错误!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public boolean checkInput(){
        if(username.trim().equals("")){
            Toast.makeText(LoginActivity.this,"账号不能为空！",Toast.LENGTH_SHORT);
            return false;
        }
        if(password.trim().equals("")){
            Toast.makeText(LoginActivity.this,"密码不能为空！",Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

}