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
import com.example.a317soft.bean.ItemBean;
import com.example.a317soft.bean.Post;

import java.util.List;

public class GridViewAdapter extends  RecyclerView.Adapter<GridViewAdapter.InnerHolder> {

    private final List<Post> mData;
    private final List<Commodity> mData2;
    private OnItemClickListener mOnItemClickListener = null;

    public GridViewAdapter(List<Post> data, List<Commodity> data2){
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
    public GridViewAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_homepost_view, null);
        return new GridViewAdapter.InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewAdapter.InnerHolder holder, int position) {
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
        private TextView mnamedisp;
        private TextView mpricedisp;

        private int id;
        private int mposition;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);

            mIcon = (ImageView) itemView.findViewById(R.id.grid_view_icon);
            mnamedisp = (TextView) itemView.findViewById(R.id.grid_view_namedisp);
            mpricedisp = (TextView) itemView.findViewById(R.id.grid_view_pricedisp);

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
            id = reBean.getId();
            mposition = position;
            mIcon.setImageBitmap(BitmapFactory.decodeByteArray(mData2.get(mposition).getPicture(),0,mData2.get(mposition).getPicture().length));
            String name = mData2.get(mposition).getTitle();
            mnamedisp.setText(name);
            mpricedisp.setText(reBean.getPrice());
        }
    }
}
