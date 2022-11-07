package com.example.a317soft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.example.a317soft.adapter.GridViewAdapterComm;
import com.example.a317soft.adapter.LoadingDialog;
import com.example.a317soft.bean.Community;
import com.example.a317soft.message.MessageList;
import com.example.a317soft.old.CommunityDBHelper;
import com.example.a317soft.old.UserCommunityDBHelper;
import com.example.a317soft.util.CommodityDB;
import com.example.a317soft.util.CommunityDB;
import com.example.a317soft.util.UserCommunityDB;

import java.util.ArrayList;
import java.util.List;

public class CommunityActivity extends AppCompatActivity {



    private RecyclerView mList;
    private List<Community> mData;

    private TextView tv_homepage2;
    private TextView tv_community2;
    private TextView tv_collection2;
    private TextView tv_mine2;

    private TextView tv_Comm_Iin, tv_createComm, tv_findComm, tv_infomation;

    private GridViewAdapterComm adapter;
    LoadingDialog loadingDialog;
    Bundle bundle;
    int user_id;

    private Handler mhandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                loadingDialog.dismiss();
                showGrid(true, false);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        loadingDialog = new LoadingDialog(this);
        bundle = this.getIntent().getExtras();
        user_id = this.getIntent().getIntExtra("user_id",0);
        mList = (RecyclerView) this.findViewById(R.id.recycler_view_community);
        mData=new ArrayList<>();
        showGrid(true,false);
        initView();
        initData();
    }




    private void initData() {
        //创建数据集合
        loadingDialog.show();
        mData = new ArrayList<>();
        Thread th= new Thread(new Runnable() {
            @Override
            public void run() {
                List<Integer> idList = UserCommunityDB.queryByUser(user_id);
                for(int communityid : idList){
                    mData.add(CommunityDB.queryById(communityid));
                    int xx = -1;
                }
                Message message = Message.obtain();
                message.what=1;
                mhandler.sendMessage(message);
            }
        });
        th.start();

    }

        //创建模拟数据
        /*
        for(int i = 0; i < DatasComm.icons.length; i++){
            //创建数据对象
            ItemBeanComm data = new ItemBeanComm();
            data.icon = Datas.icons[i];
            data.comm_name = "第" + i + "个";
            data.comm_info = "第" + i + "个社群";
            mData.add(data);
        }*/

    public  void initView(){
        tv_homepage2 = findViewById(R.id.tv_homepage2);
        tv_community2 = findViewById(R.id.tv_community2);
        tv_collection2 = findViewById(R.id.tv_collection2);
        tv_mine2 = findViewById(R.id.tv_mine2);

        tv_Comm_Iin = findViewById(R.id.tv_Comm_Iin);//我加入的
        tv_createComm = findViewById(R.id.tv_createComm);//创建社群
        tv_findComm = findViewById(R.id.tv_findComm);//发现社群

        tv_infomation = findViewById(R.id.tv_infomation);
        Drawable drawable1=getResources().getDrawable(R.drawable.home);
        drawable1.setBounds(0,0,100,100);
        tv_homepage2 .setCompoundDrawables(null,drawable1,null,null);

        tv_homepage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CommunityActivity.this, HomePageActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        Drawable drawable2=getResources().getDrawable(R.drawable.com);
        drawable2.setBounds(0,0,100,100);
        tv_community2 .setCompoundDrawables(null,drawable2,null,null);
        /*
        tv_community2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CommunityActivity.this, ImageActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });*/

        Drawable drawable3=getResources().getDrawable(R.drawable.resource);
        drawable3.setBounds(0,0,100,100);
        tv_collection2 .setCompoundDrawables(null,drawable3,null,null);

        tv_collection2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CommunityActivity.this, ResourcePoolActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        Drawable drawable4=getResources().getDrawable(R.drawable.my);
        drawable4.setBounds(0,0,100,100);
        tv_mine2 .setCompoundDrawables(null,drawable4,null,null);
        tv_mine2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CommunityActivity.this,PersonalCenterActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
/*
        tv_Comm_Iin.setOnClickListener(new View.OnClickListener() {  //我加入的
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(,);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
*/
        tv_createComm.setOnClickListener(new View.OnClickListener() {//创建社群
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityActivity.this, CommunityReleaseActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

        tv_findComm.setOnClickListener(new View.OnClickListener() { //发现社群
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityActivity.this, CommunitySearchActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

    }

    public void initListener(){
        adapter.setmOnItemClickListener(new GridViewAdapterComm.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                bundle.putInt("community_id",mData.get(position).getId());
                bundle.putString("com_name",mData.get(position).getTitle());
                Intent intent = new Intent();
                intent.setClass(CommunityActivity.this,PostPoolActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void showGrid(boolean isVertical,boolean isReverse){
        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        layoutManager.setOrientation(isVertical? RecyclerView.VERTICAL:RecyclerView.HORIZONTAL);
        layoutManager.setReverseLayout(isReverse);
        mList.setLayoutManager(layoutManager);
        adapter = new GridViewAdapterComm(mData);
        mList.setAdapter(adapter);
        initListener();
    }


}