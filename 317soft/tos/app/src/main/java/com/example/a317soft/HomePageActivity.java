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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a317soft.adapter.GridViewAdapter;
import com.example.a317soft.adapter.LoadingDialog;
import com.example.a317soft.bean.Commodity;
import com.example.a317soft.bean.Post;
import com.example.a317soft.message.Message111;
import com.example.a317soft.message.MessageList;
import com.example.a317soft.old.PostDBHelper;
import com.example.a317soft.util.CommodityDB;
import com.example.a317soft.util.PostDB;
import com.example.a317soft.util.UserCommunityDB;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {

    private RecyclerView mList;
    private List<Post> mData;
    private List<Commodity> mData2;
    private ImageView iv;
    private TextView tv_homepage1;
    private TextView tv_community1;
    private TextView tv_collection1;
    private TextView tv_mine1;
    private int user_id;
    View homepage;
    Bundle bundle;
    GridViewAdapter adapter;
    LoadingDialog loading;

    private Handler mhandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                loading.dismiss();
                showGrid(true, false);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        loading = new LoadingDialog(this);
        bundle = this.getIntent().getExtras();
        user_id=this.getIntent().getIntExtra("user_id",0);
        iv = this.findViewById(R.id.tv_image1);
        MessageList.commoditiesMap.put(user_id,new ArrayList<>());
        mList = (RecyclerView) this.findViewById(R.id.recycler_view);
        initView();
        showGrid(true,false);
        initData();
    }

    //用于模拟数据
    private void initData() {
        //创建数据集合
        loading.show();
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                List communities = UserCommunityDB.queryByUser(user_id);
                mData = PostDB.postsByDate(communities);
                mData2 = new ArrayList<>();
                for(Post post : mData){
                    int communityId = post.getCommodity_id();
                    mData2.add(CommodityDB.findCommodity(communityId));
                }
                Message message = Message.obtain();
                message.what = 1;
                mhandler.sendMessage(message);
            }
        });
        th.start();
    }

    public  void initView(){
        tv_homepage1 = findViewById(R.id.tv_homepage1);
        tv_community1 = findViewById(R.id.tv_community1);
        tv_collection1 = findViewById(R.id.tv_collection1);
        tv_mine1 = findViewById(R.id.tv_mine1);

        Drawable drawable1=getResources().getDrawable(R.drawable.home);
        drawable1.setBounds(0,0,100,100);
        tv_homepage1 .setCompoundDrawables(null,drawable1,null,null);
        /*
        tv_homepage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(,);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
*/
        Drawable drawable2=getResources().getDrawable(R.drawable.com);
        drawable2.setBounds(0,0,100,100);
        tv_community1 .setCompoundDrawables(null,drawable2,null,null);

        tv_community1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(HomePageActivity.this, CommunityActivity.class);
                startActivity(intent);
            }
        });

        Drawable drawable3=getResources().getDrawable(R.drawable.resource);
        drawable3.setBounds(0,0,100,100);
        tv_collection1 .setCompoundDrawables(null,drawable3,null,null);
        tv_collection1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(HomePageActivity.this, ResourcePoolActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        Drawable drawable4=getResources().getDrawable(R.drawable.my);
        drawable4.setBounds(0,0,100,100);
        tv_mine1 .setCompoundDrawables(null,drawable4,null,null);
        tv_mine1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomePageActivity.this,PersonalCenterActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }


    private void showGrid(boolean isVertical,boolean isReverse){
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        layoutManager.setOrientation(isVertical? RecyclerView.VERTICAL:RecyclerView.HORIZONTAL);
        layoutManager.setReverseLayout(isReverse);
        mList.setLayoutManager(layoutManager);
        adapter = new GridViewAdapter(mData, mData2);
        mList.setAdapter(adapter);
        initListener();
    }

    public void initListener(){
        adapter.setmOnItemClickListener(new GridViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                bundle.putInt("post_id", mData.get(position).getId());
                Intent intent = new Intent();
                intent.setClass(HomePageActivity.this, PostPageActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}