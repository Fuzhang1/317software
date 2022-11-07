package com.example.a317soft.adapter;

import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a317soft.R;
import com.example.a317soft.bean.Community;



import java.util.List;

public class CommSearchAdapter extends RecyclerView.Adapter<CommSearchAdapter.InnerHolder>{
    private final List<Community> mData;
    private final List<Boolean> hasList;
    private CommSearchAdapter.OnItemClickListener mOnItemClickListener=null;
    public CommSearchAdapter(List<Community> mData,List<Boolean> hasList) {
        this.mData = mData;
        this.hasList =hasList;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.comm_grid_view, null);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
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
        private TextView comm_name;
        private TextView comm_info;
        private TextView enter_Comm;
        private int mposition;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);

            mIcon = (ImageView) itemView.findViewById(R.id.grid_comm_icon);
            comm_name = (TextView) itemView.findViewById(R.id.Comm_name);
            comm_info = (TextView) itemView.findViewById(R.id.Comm_info);
            enter_Comm = (TextView) itemView.findViewById(R.id.enter_Comm);
            enter_Comm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mOnItemClickListener!=null) {
                        if (!hasList.get(mposition)) {
                            mOnItemClickListener.onItemClick(mposition);
                            enter_Comm.setText("已加入");
                            boolean has = !hasList.get(mposition);
                            hasList.set(mposition,has);
                        }
                    }
                }
            });

        }

        public void setData(Community community,int position) {
            mposition = position;
            mIcon.setImageBitmap(BitmapFactory.decodeByteArray(community.getPicture(),0,community.getPicture().length));
            comm_name.setText(community.getTitle());
            comm_info.setText(community.getDescription());
            enter_Comm.setText(hasList.get(mposition)?"已加入":"加社群");

        }
    }
}
