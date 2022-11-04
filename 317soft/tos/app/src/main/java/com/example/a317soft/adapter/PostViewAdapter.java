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
import com.example.a317soft.bean.Post;

import java.util.List;

public class PostViewAdapter extends RecyclerView.Adapter<PostViewAdapter.InnerHolder>{
    //此类为不含checkbox的版本
    private final List<Post> mData;
    private final List<Commodity> mData2;
    private OnItemClickListener mOnItemClickListener=null;
    public PostViewAdapter(List<Post> data, List<Commodity> data2){
        this.mData = data;
        this.mData2 = data2;
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public PostViewAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_resource_view2, null);
        return new PostViewAdapter.InnerHolder(view);
    }





    @Override
    public void onBindViewHolder(@NonNull PostViewAdapter.InnerHolder holder, int position) {
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

        public void setData(Post reBean, int position) {
            id=reBean.getId();
            mposition=position;
            mIcon.setImageBitmap(BitmapFactory.decodeByteArray(mData2.get(mposition).getPicture(),0,mData2.get(mposition).getPicture().length));
            mTitle.setText(mData2.get(mposition).getTitle());
            introduction.setText(reBean.getDescription());
        }
    }
}
