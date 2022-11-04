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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a317soft.adapter.LoadingDialog;
import com.example.a317soft.adapter.ReViewAdapter;
import com.example.a317soft.bean.Commodity;
import com.example.a317soft.bean.Post;
import com.example.a317soft.message.Message111;
import com.example.a317soft.message.MessageList;
import com.example.a317soft.util.CommodityDB;
import com.example.a317soft.util.PostDB;

import java.util.ArrayList;
import java.util.List;

public class ResourcePoolActivity extends AppCompatActivity {

    private RecyclerView mList;
    private List<Commodity> mData;
    private TextView btn_submit_re;
    private ImageView btn_delete;
    private ReViewAdapter adapter;
    private CheckBox cb_all;
    private Bundle bundle;
    int user_id;
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
        setContentView(R.layout.activity_resource_pool);

        loadingDialog = new LoadingDialog(this);
        bundle = this.getIntent().getExtras();
        user_id = this.getIntent().getIntExtra("user_id",0);
        mList = (RecyclerView) this.findViewById(R.id.recycler_re_view);
        btn_submit_re = findViewById(R.id.btn_submit_resource);
        btn_delete = findViewById(R.id.resource_delete);
        cb_all = findViewById(R.id.cb_resource_all);
        mData = new ArrayList<>();

        initView();
        showGrid(true, false);
        initData();
        btn_submit_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ResourcePoolActivity.this,CommodityReleaseActivity.class);
                bundle.putInt("release_status",1);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Boolean> cblist = adapter.getCbList();
                Thread th2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<Commodity> newData =new ArrayList<>();
                        List<Integer> idList =new ArrayList<>();
                        for(int i=0;i<cblist.size();i++){
                            if(!cblist.get(i)){
                                int commodity_id =mData.get(i).getId();
                                newData.add(mData.get(i));
                            }
                            else{
                                int commodity_id =mData.get(i).getId();
                                idList.add(commodity_id);
                            }
                        }
                        mData = newData;
                        Message message = Message.obtain();
                        message.what =2;
                        mhandler.sendMessage(message);

                        for(int i=0;i<idList.size();i++){
                            CommodityDB.deleteMyCommodity(idList.get(i));
                            PostDB.deleteMyPost(idList.get(i));
                        }
                    }
                });
                th2.start();

                cb_all.setChecked(false);
            }
        });

        cb_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                adapter.allcheck(b);
            }
        });

    }

    //用于模拟数据
    private void initData() {
        //创建数据集合
        loadingDialog.show();
        mData = new ArrayList<>();
        int user_id = this.getIntent().getIntExtra("user_id",0);
        //载入商品数据
        Thread th1 = new Thread(new Runnable() {
            @Override
            public void run() {
                mData = CommodityDB.readMyCommodity(user_id);
                Message message = Message.obtain();
                message.what =1;
                mhandler.sendMessage(message);
            }
        });
        th1.start();

    }


    private void showGrid(boolean isVertical, boolean isReverse) {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        layoutManager.setOrientation(isVertical ? RecyclerView.VERTICAL : RecyclerView.HORIZONTAL);
        layoutManager.setReverseLayout(isReverse);
        mList.setLayoutManager(layoutManager);
        adapter = new ReViewAdapter(mData);
        mList.setAdapter(adapter);
    }

    public  void initView(){
        TextView tv_homepage,tv_community,tv_collection,tv_mine;
        tv_homepage = findViewById(R.id.tv_homepage5);
        tv_community = findViewById(R.id.tv_community5);
        tv_collection = findViewById(R.id.tv_collection5);
        tv_mine = findViewById(R.id.tv_mine5);

        Drawable drawable1=getResources().getDrawable(R.drawable.home);
        drawable1.setBounds(0,0,100,100);
        tv_homepage .setCompoundDrawables(null,drawable1,null,null);

        tv_homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResourcePoolActivity.this, HomePageActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        Drawable drawable2=getResources().getDrawable(R.drawable.com);
        drawable2.setBounds(0,0,100,100);
        tv_community .setCompoundDrawables(null,drawable2,null,null);

        tv_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ResourcePoolActivity.this, CommunityActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        Drawable drawable3=getResources().getDrawable(R.drawable.resource);
        drawable3.setBounds(0,0,100,100);
        tv_collection .setCompoundDrawables(null,drawable3,null,null);
/*
        tv_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResourcePoolActivity.this, ResourcePoolActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
*/

        Drawable drawable4=getResources().getDrawable(R.drawable.my);
        drawable4.setBounds(0,0,100,100);
        tv_mine .setCompoundDrawables(null,drawable4,null,null);
        tv_mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResourcePoolActivity.this, PersonalCenterActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}