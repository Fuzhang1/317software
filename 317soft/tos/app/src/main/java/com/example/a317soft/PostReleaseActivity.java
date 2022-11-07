package com.example.a317soft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a317soft.adapter.LoadingDialog;
import com.example.a317soft.bean.Commodity;
import com.example.a317soft.bean.Post;
import com.example.a317soft.message.Message111;
import com.example.a317soft.util.CommodityDB;
import com.example.a317soft.util.PostDB;

public class PostReleaseActivity extends AppCompatActivity {

    EditText et_intro;
    EditText et_money;
    TextView tv_commodity,tv_community,btn_submit;
    ImageView btn_tocommodity,btn_tocommunity,btn_fanhui;
    String money,intro;
    Bundle bundle;
    Commodity commodity;
    LoadingDialog loadingDialog;

    /**
     * 此页bundle携带参数
     * int: user_id  用户id
     * int: commodity_id 商品id
     * int: commodity_status 商品状态:值不为2表示未输入，为2表示已输入
     * int: community_id 社群id
     * int: community_status 社群状态:值不为2表示未输入，为2表示已输入
     * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_release);

        //控件初始化
        loadingDialog = new LoadingDialog(this);
        Message111 me = Message111.getMe();
        ConstraintLayout layout = findViewById(R.id.post_Layout);
        et_intro=findViewById(R.id.et_introduction_post);
        et_intro.setText(me.getIntro());
        et_money=findViewById(R.id.et_money);
        et_money.setText(me.getPrice());
        tv_commodity=findViewById(R.id.tv_commodity);
        btn_fanhui = findViewById(R.id.btn_fanhui2);
//        tv_community=findViewById(R.id.tv_community);
        btn_tocommodity=findViewById(R.id.btn_tocommodity);
//        btn_tocommunity=findViewById(R.id.btn_tocommunity);
        btn_submit=findViewById(R.id.btn_submit_post);

        bundle=this.getIntent().getExtras();
        int commodity_id = bundle.getInt("commodity_id");
        int community_id = this.getIntent().getIntExtra("community_id",0);

        if(bundle.getInt("commodity_status")==2){
            layout.removeViewInLayout(btn_tocommodity);
            Thread th1= new Thread(new Runnable() {
                @Override
                public void run() {
                    tv_commodity.setText(CommodityDB.findCommodity(commodity_id).getTitle());
                }
            });
            th1.start();
            try {
                th1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        tv_commodity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                me.setIntro(et_intro.getText().toString().trim());
                me.setPrice(et_money.getText().toString().trim());
                bundle.putInt("commodity_status",0);
                Intent intent = new Intent(PostReleaseActivity.this,ResourcePoolActivity2.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btn_tocommodity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                me.setIntro(et_intro.getText().toString().trim());
                me.setPrice(et_money.getText().toString().trim());
                bundle.putInt("commodity_status",0);
                Intent intent = new Intent(PostReleaseActivity.this,ResourcePoolActivity2.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //获取商品
                Thread th2= new Thread(new Runnable() {
                    @Override
                    public void run() {
                        commodity = CommodityDB.findCommodity(bundle.getInt("commodity_id"));
                    }
                });
                th2.start();
                try {
                    th2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                intro = et_intro.getText().toString().trim();
                money = et_money.getText().toString().trim();
                if(check(commodity)){
                    //加载帖子数据库
                    loadingDialog.show();
                    me.setPrice("");
                    me.setIntro("");
                    Post post = new Post(bundle.getInt("user_id"),bundle.getInt("commodity_id"),community_id,intro,money);

                    Thread th3= new Thread(new Runnable() {
                        @Override
                        public void run() {
                            PostDB.addPost(post);
                        }
                    });
                    th3.start();
                    try {
                        th3.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    loadingDialog.dismiss();
                    bundle.putInt("commodity_status",0);
                    Intent intent = new Intent(PostReleaseActivity.this,PostPoolActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btn_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putInt("commodity_status",0);
                Intent intent = new Intent(PostReleaseActivity.this,PostPoolActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }

    public boolean check(Commodity commodity){
        //检查价格输入，校正介绍内容，检查商品选择，检查社群选择
        if(money.equals("")){
            Toast.makeText(PostReleaseActivity.this,"价格未输入!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(bundle.getInt("commodity_status")!=2){
            Toast.makeText(PostReleaseActivity.this,"商品未选择!",Toast.LENGTH_SHORT).show();
            return false;
        }
//        if(bundle.getInt("community_status")!=2){
//            Toast.makeText(PostReleaseActivity.this,"社群未选择!",Toast.LENGTH_SHORT).show();
//            return false;
//        }
        if(intro.equals("")){
            intro = commodity.getDescription();
        }
        return true;
    }
}