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
import android.widget.ImageView;

import com.example.a317soft.adapter.LoadingDialog;
import com.example.a317soft.adapter.PostViewAdapter;
import com.example.a317soft.bean.Commodity;
import com.example.a317soft.bean.Post;
import com.example.a317soft.util.CommodityDB;
import com.example.a317soft.util.PostDB;
import com.example.a317soft.util.UserInfoDB;

import java.util.ArrayList;
import java.util.List;

public class MyPostActivity extends AppCompatActivity {

    private RecyclerView mList;
    private static List<Post> mData=new ArrayList<>();
    private static List<Commodity> mData2 = new ArrayList<>();
    private PostViewAdapter adapter;
    private ImageView btn_fanhui;
    Bundle bundle;
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
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);

        loadingDialog = new LoadingDialog(this);
        btn_fanhui = findViewById(R.id.btn_fanhui10);
        bundle = this.getIntent().getExtras();
        user_id = bundle.getInt("user_id");
        mList = findViewById(R.id.recycler_my_post);

        btn_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(MyPostActivity.this, PersonalCenterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        showGrid(true,false);
        initData();

    }
    //用于模拟数据
    private void initData() {
        //创建数据集合
        loadingDialog.show();
        mData = new ArrayList<>();
        mData2 = new ArrayList<>();

        Thread th1= new Thread(new Runnable() {
            @Override
            public void run() {
                mData = PostDB.readMyPosts(user_id);
                for(Post x : mData)
                {
                    mData2.add(CommodityDB.findCommodity(x.getCommodity_id()));
                }
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
        adapter = new PostViewAdapter(mData,mData2);
        mList.setAdapter(adapter);
        initListener();
    }

    public void initListener(){
        adapter.setmOnItemClickListener(new PostViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                bundle.putInt("post_id",mData.get(position).getId());
                Intent intent = new Intent();
                intent.setClass(MyPostActivity.this,PostPageActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}