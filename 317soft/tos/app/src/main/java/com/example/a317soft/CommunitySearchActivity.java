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
import android.widget.EditText;
import android.widget.ImageView;

import com.example.a317soft.adapter.CommSearchAdapter;
import com.example.a317soft.adapter.LoadingDialog;
import com.example.a317soft.bean.Community;
import com.example.a317soft.message.MessageList;
import com.example.a317soft.old.CommunityDBHelper;
import com.example.a317soft.old.UserCommunityDBHelper;
import com.example.a317soft.util.CommunityDB;
import com.example.a317soft.util.UserCommunityDB;

import java.util.ArrayList;
import java.util.List;

public class CommunitySearchActivity extends AppCompatActivity {
    private RecyclerView mList;
    private List<Community> mData = new ArrayList<>();
    private List<Boolean> hasList = null;
    ImageView iv_search,btn_fanhui;
    EditText et_community_name;
    String community_name = "";
    Bundle bundle;
    private int user_id,community_id;
    private CommSearchAdapter adapter;
    LoadingDialog loadingDialog;

    private Handler mhandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                loadingDialog.dismiss();
                showGrid(true, false);
            }
            else if(msg.what == 2){
                loadingDialog.dismiss();
                showGrid(true, false);
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_search);

        loadingDialog = new LoadingDialog(this);
        mList = (RecyclerView) this.findViewById(R.id.recycler_view_communitySearch);
        iv_search = findViewById(R.id.iv_searchcomm);
        et_community_name = findViewById(R.id.et_communitySearch);
        btn_fanhui = findViewById(R.id.btn_fanhui8);
        bundle = this.getIntent().getExtras();
        user_id = this.getIntent().getIntExtra("user_id",0);

        initData(community_name);
        showGrid(true,false);


        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                community_name = et_community_name.getText().toString().trim();
                initData(community_name);
            }
        });

        btn_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(CommunitySearchActivity.this, CommunityActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    public void initListener(){
        adapter.setmOnItemClickListener(new CommSearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                loadingDialog.show();
                community_id = mData.get(position).getId();
                new Thread(new Runnable() {
                   @Override
                   public void run() {
                       UserCommunityDB.addUserCommunity(user_id,community_id);
                       CommunityDB.updateMemberCount(community_id);
                       Message message = Message.obtain();
                       message.what=1;
                       mhandler.sendMessage(message);
                   }
                }).start();


            }
        });
    }

    void initData(String community_name){
        loadingDialog.show();
        hasList = new ArrayList<>();
        Thread th1 = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Community> communityList = CommunityDB.queryByTitle(community_name);
                List userComList= UserCommunityDB.queryByUser(user_id);
                for(Community c:communityList){
                    hasList.add(userComList.contains(c.getId()));
                }
                mData = communityList;
                Message message= Message.obtain();
                message.what = 2;
                mhandler.sendMessage(message);
            }
        });
        th1.start();

    }

    private void showGrid(boolean isVertical,boolean isReverse){
        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        layoutManager.setOrientation(isVertical? RecyclerView.VERTICAL:RecyclerView.HORIZONTAL);
        layoutManager.setReverseLayout(isReverse);
        mList.setLayoutManager(layoutManager);
        adapter = new CommSearchAdapter(mData,hasList);
        mList.setAdapter(adapter);
        initListener();
    }

}