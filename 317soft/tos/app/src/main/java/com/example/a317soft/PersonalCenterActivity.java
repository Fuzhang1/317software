package com.example.a317soft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PersonalCenterActivity extends AppCompatActivity {

    Bundle bundle;
    TextView myInfo, myPost;
    TextView quit;

    TextView tv_homepage1;
    TextView tv_community1;
    TextView tv_collection1;
    TextView tv_mine1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);

        bundle = this.getIntent().getExtras();
        int user_id = bundle.getInt("user_id");

        myInfo = findViewById(R.id.btn_my_info);
        myPost = findViewById(R.id.btn_my_post);
        quit = findViewById(R.id.btn_quit);

        tv_homepage1 = findViewById(R.id.tv_homepage5);
        tv_community1 = findViewById(R.id.tv_community5);
        tv_collection1 = findViewById(R.id.tv_collection5);
        tv_mine1 = findViewById(R.id.tv_mine5);

        myInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(PersonalCenterActivity.this, ModifyInfoActivity.class);
                startActivity(intent);
            }
        });

        myPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(PersonalCenterActivity.this, MyPostActivity.class);
                startActivity(intent);
            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(PersonalCenterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Drawable drawable1=getResources().getDrawable(R.drawable.home);
        drawable1.setBounds(0,0,100,100);
        tv_homepage1 .setCompoundDrawables(null,drawable1,null,null);

        tv_homepage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(PersonalCenterActivity.this, HomePageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Drawable drawable2=getResources().getDrawable(R.drawable.com);
        drawable2.setBounds(0,0,100,100);
        tv_community1 .setCompoundDrawables(null,drawable2,null,null);

        tv_community1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(PersonalCenterActivity.this, CommunityActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Drawable drawable3=getResources().getDrawable(R.drawable.resource);
        drawable3.setBounds(0,0,100,100);
        tv_collection1 .setCompoundDrawables(null,drawable3,null,null);

        tv_collection1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PersonalCenterActivity.this, ResourcePoolActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

        Drawable drawable4=getResources().getDrawable(R.drawable.my);
        drawable4.setBounds(0,0,100,100);
        tv_mine1 .setCompoundDrawables(null,drawable4,null,null);
    }
}