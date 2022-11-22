package com.example.a317soft;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a317soft.adapter.LoadingDialog;
import com.example.a317soft.bean.UserInfo;
import com.example.a317soft.util.CommodityDB;
import com.example.a317soft.util.UserInfoDB;

import java.io.ByteArrayOutputStream;

public class ModifyInfoActivity extends AppCompatActivity {

    ImageView iv_profile,btn_fanhui;
    EditText et_name, et_qq, et_tel;
    TextView tv_save;
    Bundle bundle;
    LoadingDialog loadingDialog;
    UserInfo userInfo;

    private ActivityResultLauncher<Intent> mResultLauncher;
    private Uri picUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_info);

        loadingDialog = new LoadingDialog(this);
        iv_profile = findViewById(R.id.iv_profile);
        et_name = findViewById(R.id.edit_name);
        et_qq = findViewById(R.id.edit_qq);
        et_tel = findViewById(R.id.edit_tel);
        tv_save = findViewById(R.id.tv_save);
        btn_fanhui = findViewById(R.id.btn_fanhui9);
        bundle = this.getIntent().getExtras();
        int user_id = bundle.getInt("user_id");

        loadingDialog.show();
        Thread th= new Thread(new Runnable() {
            @Override
            public void run() {
                userInfo = UserInfoDB.findUserInfoById(user_id);
            }
        });
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        loadingDialog.dismiss();

        if(userInfo.getUsername()!=null)
            if(!userInfo.getUsername().equals(""))
                et_name.setText(userInfo.getUsername());
        if(userInfo.getQQ()!=null)
            if(!userInfo.getQQ().equals(""))
                et_qq.setText(userInfo.getQQ());
        if(userInfo.getPhone()!=null)
            if(!userInfo.getPhone().equals(""))
                et_tel.setText(userInfo.getPhone());
        if(userInfo.getProfile() != null)
            iv_profile.setImageBitmap(BitmapFactory.decodeByteArray(userInfo.getProfile(), 0, userInfo.getProfile().length));

        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到系统相册，选择图片，并返回
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                //设置内容为图片类型
                intent.setType("image/*");
                //打开系统相册，等待选择结果
                mResultLauncher.launch(intent);
            }
        });
        mResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK) {
                    Intent intent = result.getData();
                    picUri = intent.getData();
                    if(picUri != null) {
                        iv_profile.setImageURI(picUri);
                        Log.d("ning", "picUri"+picUri.toString());
                    }
                }
            }
        });

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDialog.show();
                String name = et_name.getText().toString().trim();
                String qq = et_qq.getText().toString().trim();
                String phone = et_tel.getText().toString().trim();
                ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                Bitmap pic = ((BitmapDrawable)iv_profile.getDrawable()).getBitmap();
                pic.compress(Bitmap.CompressFormat.PNG,100,byteStream);
                byte[] bs = byteStream.toByteArray();
                UserInfo newInfo = new UserInfo(user_id, name, qq, phone, bs);

                Thread th1= new Thread(new Runnable() {
                    @Override
                    public void run() {
                        UserInfoDB.deleteUserInfoById(user_id);
                        UserInfoDB.addUserInfo(newInfo);
                    }
                });
                th1.start();
                try {
                    th1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                loadingDialog.dismiss();
                Toast.makeText(ModifyInfoActivity.this,"保存成功!",Toast.LENGTH_SHORT).show();
            }
        });

        btn_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(ModifyInfoActivity.this, PersonalCenterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}