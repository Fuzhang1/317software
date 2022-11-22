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
import android.widget.TextView;

import com.example.a317soft.adapter.LoadingDialog;
import com.example.a317soft.adapter.PostViewAdapter;
import com.example.a317soft.adapter.ReViewAdapter2;
import com.example.a317soft.bean.Commodity;
import com.example.a317soft.bean.Post;
import com.example.a317soft.message.MessageList;
import com.example.a317soft.util.CommodityDB;
import com.example.a317soft.util.CommunityDB;
import com.example.a317soft.util.PostDB;
import com.example.a317soft.util.UserCommunityDB;
import com.example.a317soft.util.UserDB;

import java.util.ArrayList;
import java.util.List;

public class PostPoolActivity extends AppCompatActivity {

    private RecyclerView mList;
    private List<Post> mData;
    private List<Commodity> mData2;
    private TextView btn_submit_re;
    private PostViewAdapter adapter;
    private ImageView btn_fanhui;
    private TextView tv_title;
    private TextView tv_quit;
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
        setContentView(R.layout.activity_post_pool);

        loadingDialog = new LoadingDialog(this);
        bundle = this.getIntent().getExtras();
        user_id = this.getIntent().getIntExtra("user_id",0);
        mList = (RecyclerView) this.findViewById(R.id.recycler_re_view3);
        btn_submit_re = findViewById(R.id.btn_submit_post);
        btn_fanhui=findViewById(R.id.btn_fanhui3);
        tv_title = findViewById(R.id.tv_comm_title);
        tv_quit = findViewById(R.id.tv_quitCom);
        String com_name = this.getIntent().getStringExtra("com_name");

        initData();
        showGrid(true,false);
        tv_title.setText(this.getIntent().getStringExtra("com_name"));
        btn_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(PostPoolActivity.this,CommunityActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

        btn_submit_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(PostPoolActivity.this,PostReleaseActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        tv_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDialog.show();
                Thread th= new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int comm_id = CommunityDB.searchByTitle(com_name);
                        PostDB.deletePostByQuit(user_id, comm_id);
                        UserCommunityDB.deleteUserCommunity(user_id, comm_id);
                    }
                });
                th.start();
                try {
                    th.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                loadingDialog.dismiss();

                Intent intent = new Intent();
                intent.setClass(PostPoolActivity.this,CommunityActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });


    }

    //用于模拟数据
    private void initData() {
        //创建数据集合
        loadingDialog.show();
        mData = new ArrayList<>();
        int community_id = this.getIntent().getIntExtra("community_id",0);
        mData2 = new ArrayList<>();

        Thread th1= new Thread(new Runnable() {
            @Override
            public void run() {
                mData = PostDB.postsByCommunity(community_id);
                for(Post x : mData)
                {
                    mData2.add(CommodityDB.findCommodity(x.getCommodity_id()));
                }
                Message message =Message.obtain();
                message.what=1;
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
                intent.setClass(PostPoolActivity.this,PostPageActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }






}