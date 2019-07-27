package com.thuhang.foody1703311.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thuhang.foody1703311.R;
import com.thuhang.foody1703311.models.CategoryType;
import com.thuhang.foody1703311.util.Utils;
import com.thuhang.foody1703311.viewholder.CategoryTypeViewHolder;

import java.util.List;

/**
 * Created by ThuHang on 4/16/2017.
 */

public class CateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    public static int pos = 0;
    private int[] iconList;
    private List<Object> categoryTypeList;
    private Utils.OnItemClickListener clickListener;
    public void setClickListener(Utils.OnItemClickListener clickListener){
        this.clickListener = clickListener;
    }
    public CateAdapter(Context mContext, int[] iconList, List<Object> categoryTypeList) {
        this.mContext = mContext;
        this.iconList = iconList;
        this.categoryTypeList = categoryTypeList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_type,parent,false);
        final RecyclerView.ViewHolder holder = new CategoryTypeViewHolder(v);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener!=null)
                    clickListener.onItemClick(holder.itemView, holder.getLayoutPosition());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        CategoryTypeViewHolder holder1 = (CategoryTypeViewHolder)holder;
        CategoryType categoryType = (CategoryType) categoryTypeList.get(position);
        holder1.getTvName().setText(categoryType.getName());
        try {
            holder1.getImg().setImageResource(iconList[position]);
        }catch (OutOfMemoryError e){
            e.printStackTrace();
        }
        if(position == pos)
            holder1.getTvName().setTypeface(null, Typeface.BOLD);
    }
    @Override
    public int getItemCount() {
        return categoryTypeList.size();
    }
}
