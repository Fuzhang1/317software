package com.example.a317soft.adapter;

import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a317soft.R;
import com.example.a317soft.bean.Commodity;

import java.util.List;

public class ReViewAdapter2 extends RecyclerView.Adapter<ReViewAdapter2.InnerHolder>{
    //此类为不含checkbox的版本
    private final List<Commodity> mData;
    private OnItemClickListener mOnItemClickListener=null;
    public ReViewAdapter2(List<Commodity> data){
        this.mData = data;
    }


    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public ReViewAdapter2.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_resource_view2, null);
        return new ReViewAdapter2.InnerHolder(view);
    }





    @Override
    public void onBindViewHolder(@NonNull ReViewAdapter2.InnerHolder holder, int position) {
        holder.setData(mData.get(position),position);
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
        private int mposition;
        public InnerHolder(@NonNull View itemView) {
            super(itemView);

            cb=(CheckBox)itemView.findViewById(R.id.resource_view_check);
            mIcon = (ImageView) itemView.findViewById(R.id.resource_view_icon);
            mTitle = (TextView) itemView.findViewById(R.id.resource_view_title);
            introduction=(TextView) itemView.findViewById(R.id.resource_view_introduction);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mOnItemClickListener!=null){
                        mOnItemClickListener.onItemClick(mposition);
                    }
                }
            });
        }

        public void setData(Commodity reBean, int position) {

            id=reBean.getId();
            mposition=position;
            mIcon.setImageBitmap(BitmapFactory.decodeByteArray(reBean.getPicture(),0,reBean.getPicture().length));
            mTitle.setText(reBean.getTitle());
            introduction.setText(reBean.getDescription());
        }
    }
}
