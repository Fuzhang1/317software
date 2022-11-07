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
import com.example.a317soft.bean.Commodity;
import com.example.a317soft.util.CommodityDB;

import java.io.ByteArrayOutputStream;

public class CommodityReleaseActivity extends AppCompatActivity {
    ImageView btn_fanhui;
    TextView btn_submit;
    EditText et_commodity,et_introduction;
    String name_commodity="",introduction="";
    int user_id = 0;
    private int picture=R.mipmap.pic_01;
    private ActivityResultLauncher<Intent> mResultLauncher;
    private ImageView btn_img;
    private Uri picUri;
    LoadingDialog loadingDialog;
    /**
     * 此页bundle携带参数
     * int: user_id  用户id
     * int: commodity_id 商品id
     * int: commodity_status 商品状态:值为1表示未输入，为2表示已输入
     * int: community_id 社群id
     * int: community_status 社群状态:值为1表示未输入，为2表示已输入
     * int: release_status 发布状态:值为1表示从帖子发布时的商品选择进入，值为2表示从商品管理处进路 通过该值标记了发布的出口
     * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_release);
        loadingDialog = new LoadingDialog(this);
        btn_fanhui=findViewById(R.id.btn_fanhui);
        btn_submit=findViewById(R.id.btn_submit);
        et_commodity=findViewById(R.id.et_community);
        et_introduction=findViewById(R.id.et_introduction);
        final Bundle bundle = this.getIntent().getExtras();
        user_id = this.getIntent().getIntExtra("user_id",0);
        btn_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_commodity=et_commodity.getText().toString().trim();
                introduction=et_introduction.getText().toString().trim();
                if(checkInput()){
                    loadingDialog.show();
                    ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                    Bitmap pic = ((BitmapDrawable)btn_img.getDrawable()).getBitmap();
                    pic.compress(Bitmap.CompressFormat.PNG,100,byteStream);
                    byte[] bs = byteStream.toByteArray();

                    Commodity commodity =new Commodity(user_id,name_commodity,introduction,bs);
                    Thread th1 =new Thread(new Runnable() {
                        @Override
                        public void run() {
                            CommodityDB.addCommodity(commodity);
                        }
                    });
                    th1.start();
                    try {
                        th1.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    loadingDialog.dismiss();
                    Toast.makeText(CommodityReleaseActivity.this,"恭喜你发布资源成功!",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent();
                    if(bundle.getInt("release_status")==1){
                        intent.setClass(CommodityReleaseActivity.this,ResourcePoolActivity.class);
                    }
                    else {
                        intent.setClass(CommodityReleaseActivity.this,ResourcePoolActivity2.class);
                    }

                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            }

        });
        btn_img = findViewById(R.id.btn_img);
        btn_img.setOnClickListener(new View.OnClickListener() {
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
                        btn_img.setImageURI(picUri);
                        Log.d("ning", "picUri"+picUri.toString());
                    }
                }
            }
        });
    }

    public boolean checkInput(){
        if(name_commodity.equals("")){
            Toast.makeText(CommodityReleaseActivity.this,"商品名不能为空！",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(introduction.equals("")){
            introduction="卖家没有给出商品介绍";
        }

        return true;
    }


}