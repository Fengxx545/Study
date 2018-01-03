package com.zry.study.test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zry.study.R;

import java.util.List;

/**
 * Created by Hasee on 2017/12/11.
 */

class SortAdapter extends RecyclerView.Adapter<SortAdapter.ViewHolder> {

    private Context mContext;
    private List<CountryDataModel> mTList;

    public SortAdapter(Context context, List<CountryDataModel> list) {
        mContext = context;
        mTList = list;
    }

    @Override
    public SortAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.name = (TextView) view.findViewById(R.id.name);
        holder.tagText = (TextView) view.findViewById(R.id.tag);
        return holder;
    }

    @Override
    public void onBindViewHolder(SortAdapter.ViewHolder holder, final int position) {
        //获取当前position的首字母值
        char first = getCurrentPositionChar(position);
        //获取当前首字母第一次出现的位置
        int firstPosition = getFirstPosition(first);
        //第一次出现就显示
        if (position == firstPosition) {
            holder.tagText.setVisibility(View.VISIBLE);
            holder.tagText.setText(mTList.get(position).getTag());
        } else {
            holder.tagText.setVisibility(View.GONE);
        }
        holder.name.setText(mTList.get(position).getCountry());
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, mTList.get(position).getCountry(),Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return mTList.size();
    }

    public void updateView(List<CountryDataModel> list) {
        mTList = list;
        notifyDataSetChanged();
    }

    private char getCurrentPositionChar(int position) {
        return mTList.get(position).getTag().toUpperCase().charAt(0);
    }

    public int getFirstPosition(char first) {
        for (int i = 0; i < mTList.size(); i++) {
            char now = mTList.get(i).getTag().toUpperCase().charAt(0);
            if (first == now) {
                return i;
            }
        }
        return -1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, tagText;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
