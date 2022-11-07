package com.example.a317soft;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a317soft.adapter.LoadingDialog;
import com.example.a317soft.bean.Community;
import com.example.a317soft.old.CommunityDBHelper;
import com.example.a317soft.old.UserCommunityDBHelper;
import com.example.a317soft.util.CommodityDB;
import com.example.a317soft.util.CommunityDB;
import com.example.a317soft.util.UserCommunityDB;

import java.io.ByteArrayOutputStream;

public class CommunityReleaseActivity extends AppCompatActivity {

    ImageView iv_return,iv_pic;
    TextView tv_summit;
    EditText et_community,et_introduction;
    String name_community="",introduction="";
    int user_id;
    private int picture=R.mipmap.pic_01;
    private ActivityResultLauncher<Intent> mResultLauncher;
    private Uri picUri;
    int id;
    LoadingDialog loadingDialog;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_release);
        loadingDialog = new LoadingDialog(this);
        bundle = this.getIntent().getExtras();
        iv_return = findViewById(R.id.btn_commfanhui);
        tv_summit = findViewById(R.id.btn_commsubmit);
        et_community = findViewById(R.id.et_commcommunity);
        et_introduction = findViewById(R.id.et_commintroduction);
        user_id = this.getIntent().getIntExtra("user_id",0);
        iv_pic = findViewById(R.id.commimageView3);


        iv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(CommunityReleaseActivity.this, CommunityActivity.class);
                startActivity(intent);
                finish();
            }
        });
        tv_summit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_community = et_community.getText().toString().trim();
                introduction = et_introduction.getText().toString().trim();
                if(checkInput()){
                    loadingDialog.show();
                    ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                    Bitmap pic = ((BitmapDrawable)iv_pic.getDrawable()).getBitmap();
                    pic.compress(Bitmap.CompressFormat.PNG,100,byteStream);
                    byte[] bs = byteStream.toByteArray();

                    Community community = new Community(name_community, introduction, bs);
                    user_id = bundle.getInt("user_id",0);
                    Thread th33=new Thread(new Runnable() {
                        @Override
                        public void run() {
                            CommunityDB.addCommunity(community);
                            UserCommunityDB.addUserCommunity(user_id, CommunityDB.searchByTitle(name_community));
                        }
                    });
                    th33.start();
                    try {
                        th33.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    loadingDialog.dismiss();

                    Toast.makeText(CommunityReleaseActivity.this, "恭喜你社群创建成功", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    intent.setClass(CommunityReleaseActivity.this, CommunityActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        iv_pic.setOnClickListener(new View.OnClickListener() {
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
                        iv_pic.setImageURI(picUri);
                        Log.d("ning", "picUri"+picUri.toString());
                    }
                }
            }
        });
    }

    public boolean checkInput(){
        if(name_community.equals("")){
            Toast.makeText(CommunityReleaseActivity.this,"社群名不能为空！",Toast.LENGTH_SHORT).show();
            return false;
        }
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                id = CommunityDB.searchByTitle(name_community);
            }
        });
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(id != -1){
            Toast.makeText(CommunityReleaseActivity.this,"社群已经被创建！",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(introduction.equals("")){
            introduction="暂时没有给出社群介绍";
        }
        return true;
    }
}