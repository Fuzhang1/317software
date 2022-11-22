package com.example.a317soft;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a317soft.adapter.LoadingDialog;
import com.example.a317soft.bean.Commodity;
import com.example.a317soft.bean.Post;
import com.example.a317soft.bean.UserInfo;
import com.example.a317soft.util.CommodityDB;
import com.example.a317soft.util.PostDB;
import com.example.a317soft.util.UserInfoDB;

public class PostPageActivity extends AppCompatActivity {
    Bundle bundle;
    TextView tv_username,tv_price,tv_commodityName,tv_info,tv_tel;
    ImageView iv_userImg,iv_commodityImg,btn_fanhui;
    Post m_post;
    UserInfo userInfo;
    Commodity commodity;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_page);

        loadingDialog = new LoadingDialog(this);
        bundle = this.getIntent().getExtras();
        int post_id = this.getIntent().getIntExtra("post_id",0);
        tv_username=findViewById(R.id.tv_post_user);
        iv_userImg = findViewById(R.id.iv_user_image);
        iv_commodityImg=findViewById(R.id.iv_post_image);
        tv_price = findViewById(R.id.tv_post_price);
        tv_commodityName = findViewById(R.id.tv_post_commodityname);
        tv_info = findViewById(R.id.tv_post_intro);
        tv_tel = findViewById(R.id.tv_post_tel);
        btn_fanhui = findViewById(R.id.btn_fanhui3);

        loadingDialog.show();
        Thread th1= new Thread(new Runnable() {
            @Override
            public void run() {
                m_post = PostDB.findPostById(post_id);
            }
        });
        th1.start();
        try {
            th1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int post_user_id = m_post.getUser_id();

        Thread th2= new Thread(new Runnable() {
            @Override
            public void run() {
                userInfo = UserInfoDB.findUserInfoById(post_user_id);
            }
        });
        th2.start();
        try {
            th2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        tv_username.setText(userInfo.getUsername());
        String tel = "tel:" + userInfo.getPhone() + "  qq:" + userInfo.getQQ();
        tv_tel.setText(tel);
        if(userInfo.getProfile() != null)
            iv_userImg.setImageBitmap(BitmapFactory.decodeByteArray(userInfo.getProfile(),0,userInfo.getProfile().length));
        tv_info.setText(m_post.getDescription());
        tv_price.setText("￥"+m_post.getPrice());
        int community_id = m_post.getCommodity_id();

        Thread th3= new Thread(new Runnable() {
            @Override
            public void run() {
                commodity = CommodityDB.findCommodity(community_id);
            }
        });
        th3.start();
        try {
            th3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        loadingDialog.dismiss();

        iv_commodityImg.setImageBitmap(BitmapFactory.decodeByteArray(commodity.getPicture(),0,commodity.getPicture().length));
        tv_commodityName.setText(commodity.getTitle());

        btn_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}