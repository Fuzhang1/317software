package com.example.a317soft.adapter;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a317soft.R;
import com.example.a317soft.bean.Commodity;
import com.example.a317soft.bean.ResourceBean;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ReViewAdapter extends RecyclerView.Adapter<ReViewAdapter.InnerHolder>{
    private final List<Commodity> mData;
    private List<Boolean> cbList = new ArrayList<>();
    public ReViewAdapter(List<Commodity> data){
        this.mData = data;
        initChecked();
    }

    public List<Boolean> getCbList() {
        return cbList;
    }

    @NonNull
    @Override
    public ReViewAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_resource_view, null);
        return new ReViewAdapter.InnerHolder(view);
    }


    public void initChecked(){
        for(int i=0;i<mData.size();i++){
            cbList.add(false);
        }
    }

    public void allcheck(boolean tf){
        for(int i=0;i<mData.size();i++){
            cbList.set(i,tf);
        }
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ReViewAdapter.InnerHolder holder, int position) {
        holder.setData(mData.get(position));
        int pos =position;
        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                cbList.set(pos,b);
            }
        });

        holder.cb.setChecked(cbList.get(pos));
    }

    @Override
    public int getItemCount() {
        if(mData != null){
            return mData.size();
        }
        return 0;
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private ImageView mIcon;
        private TextView mTitle;
        private TextView introduction;
        private CheckBox cb;
        private int id;
        public InnerHolder(@NonNull View itemView) {
            super(itemView);

            cb=(CheckBox)itemView.findViewById(R.id.resource_view_check);
            mIcon = (ImageView) itemView.findViewById(R.id.resource_view_icon);
            mTitle = (TextView) itemView.findViewById(R.id.resource_view_title);
            introduction=(TextView) itemView.findViewById(R.id.resource_view_introduction);

        }

        public void setData(Commodity reBean) {

            id=reBean.getId();
            mIcon.setImageBitmap(BitmapFactory.decodeByteArray(reBean.getPicture(),0,reBean.getPicture().length));
            mTitle.setText(reBean.getTitle());
            introduction.setText(reBean.getDescription());
        }
    }
}
