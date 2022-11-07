package com.example.a317soft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a317soft.bean.User;
import com.example.a317soft.old.UserDBHelper;
import com.example.a317soft.util.UserDB;

public class RegisterActivity extends AppCompatActivity {

    EditText et_username,et_password,et_conpassword;
    TextView btn_no,btn_yes;
    String username,password,conpassword;
    Boolean repeat = false,yes=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //获取控件
        btn_no = findViewById(R.id.btn_no);
        btn_yes = findViewById(R.id.btn_yes);
        et_username = findViewById(R.id.r_username);
        et_password = findViewById(R.id.r_password);
        et_conpassword = findViewById(R.id.con_password);

        //暂不注册(btn_no)跳转
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //注册点击:检查输入，输入合法则注册，不合法则提示
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = et_username.getText().toString();
                password = et_password.getText().toString();
                conpassword = et_conpassword.getText().toString();
                if (checkInput()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            User user = new User(username, password);
                            UserDB userDbHelper = new UserDB();
                            userDbHelper.addUser(user);
                        }
                    }).start();
                    Toast.makeText(RegisterActivity.this, "恭喜你注册成功!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    public boolean checkInput(){
        if(username.trim().equals("")){
            Toast.makeText(RegisterActivity.this,"账号不能为空!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.trim().equals("")){
            Toast.makeText(RegisterActivity.this,"密码不能为空!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(conpassword.trim().equals("")){
            Toast.makeText(RegisterActivity.this,"确认密码不能为空!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!password.trim().equals(conpassword.trim())){
            Toast.makeText(RegisterActivity.this,"密码与确认密码不同!",Toast.LENGTH_SHORT).show();
            return false;
        }
        Thread th= new Thread(new Runnable() {
            @Override
            public void run() {
                if(!UserDB.ifNotExist(username)){
                    repeat = true;
                }
            }
        });
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(repeat){
            Toast.makeText(RegisterActivity.this,"账号已经被注册!！",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}