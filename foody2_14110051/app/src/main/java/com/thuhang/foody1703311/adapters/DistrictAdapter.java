package com.thuhang.foody1703311.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thuhang.foody1703311.R;
import com.thuhang.foody1703311.models.District;
import com.thuhang.foody1703311.util.Utils;
import com.thuhang.foody1703311.viewholder.DistrictViewHolder;

import java.util.List;

/**
 * Created by ThuHang on 4/12/2017.
 */

public class DistrictAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Object> items;
    private int posCurrentTab3=-1;
    private Context mContext;

    public DistrictAdapter(List<Object> items, Context mContext) {
        this.items = items;
        this.mContext = mContext;
    }

    public void setPosCurrentTab3(int posCurrent){
        this.posCurrentTab3 = posCurrent;
        notifyDataSetChanged();
    }
    private Utils.OnItemClickListener clickListener;

    public int getPosCurrentTab3() {
        return posCurrentTab3;
    }

    public void setOnItemClickListener(Utils.OnItemClickListener clickListener){
        this.clickListener = clickListener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view5 = inflater.inflate(R.layout.item_address_info,parent, false);
        viewHolder = new DistrictViewHolder(view5);
        final RecyclerView.ViewHolder finalViewHolder5 = viewHolder;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener!=null)
                    clickListener.onItemClick( finalViewHolder5.itemView, finalViewHolder5.getLayoutPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DistrictViewHolder holder1 = (DistrictViewHolder)holder;
        configureDistrictViewHolder(holder1, position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    private void configureDistrictViewHolder(DistrictViewHolder vh, int position){
        vh.setIsRecyclable(false);
        District district = (District) items.get(position);
        if(district !=null){
            vh.getTextView().setText(district.getDistrictName());
            if(position==posCurrentTab3) {
                vh.getTextView().setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            }
        }
    }
}
