package com.example.a317soft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a317soft.adapter.LoadingDialog;
import com.example.a317soft.adapter.ReViewAdapter;
import com.example.a317soft.adapter.ReViewAdapter2;
import com.example.a317soft.bean.Commodity;
import com.example.a317soft.message.MessageList;
import com.example.a317soft.util.CommodityDB;
import com.example.a317soft.util.UserDB;

import java.util.ArrayList;
import java.util.List;

public class ResourcePoolActivity2 extends AppCompatActivity {

    private RecyclerView mList;
    private List<Commodity> mData;
    private TextView btn_submit_re;
    private ReViewAdapter2 adapter;
    private ImageView btn_fanhui;
    private int user_id;
    Bundle bundle;
    LoadingDialog loadingDialog;

    private Handler mhandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                loadingDialog.dismiss();
                showGrid(true, false);
            }
            else if(msg.what ==2){
                showGrid(true,false);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_pool2);

        loadingDialog = new LoadingDialog(this);
        bundle = this.getIntent().getExtras();
        user_id = this.getIntent().getIntExtra("user_id",0);
        mList = (RecyclerView) this.findViewById(R.id.recycler_re_view2);
        btn_submit_re = findViewById(R.id.btn_submit_resource2);
        btn_fanhui=findViewById(R.id.btn_fanhui2);
        mData = new ArrayList<>();
        showGrid(true, false);
        initData();

        btn_submit_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ResourcePoolActivity2.this,CommodityReleaseActivity.class);
                bundle.putInt("release_status",2);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btn_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void initListener(){
        adapter.setmOnItemClickListener(new ReViewAdapter2.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                bundle.putInt("commodity_id",mData.get(position).getId());
                bundle.putInt("commodity_status",2);
                Intent intent = new Intent();
                intent.setClass(ResourcePoolActivity2.this,PostReleaseActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }

    //用于模拟数据
    private void initData() {
        //创建数据集合
        loadingDialog.show();
        mData = new ArrayList<>();
        int user_id = this.getIntent().getIntExtra("user_id",0);

        Thread th= new Thread(new Runnable() {
            @Override
            public void run() {
                mData = CommodityDB.readMyCommodity(user_id);
                Message message = Message.obtain();
                message.what=1;
                mhandler.sendMessage(message);
            }
        });
        th.start();


        //创建模拟数据
//        for (int i = 0; i < Datas.icons.length; i++) {
//            //创建数据对象
//            ResourceBean data = new ResourceBean();
//            data.icon = Datas.icons[i];
//            data.title = "第" + i + "个";
//            data.introduction="好烦";
//            mData.add(data);
//        }
    }

    public void initView() {
//        tv_homepage = findViewById(R.id.tv_homepage);
//        tv_community = findViewById(R.id.tv_community);
//        tv_collection = findViewById(R.id.tv_collection);
//        tv_mine = findViewById(R.id.tv_mine);
        /*
        tv_homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(,);
                startActivity(intent);

            }
        });

        tv_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(,);
                startActivity(intent);

            }
        });

        tv_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(,);
                startActivity(intent);

            }
        });

        tv_mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(,);
                startActivity(intent);

            }
        });
        */

    }


    private void showGrid(boolean isVertical, boolean isReverse) {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        layoutManager.setOrientation(isVertical ? RecyclerView.VERTICAL : RecyclerView.HORIZONTAL);
        layoutManager.setReverseLayout(isReverse);
        mList.setLayoutManager(layoutManager);
        adapter = new ReViewAdapter2(mData);
        mList.setAdapter(adapter);
        initListener();
    }
}