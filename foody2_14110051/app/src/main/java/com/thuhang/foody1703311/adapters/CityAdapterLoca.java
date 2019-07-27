package com.thuhang.foody1703311.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thuhang.foody1703311.R;
import com.thuhang.foody1703311.models.City;
import com.thuhang.foody1703311.models.District;
import com.thuhang.foody1703311.util.Utils;
import com.thuhang.foody1703311.viewholder.CityViewHolder;

import java.util.List;

/**
 * Created by thuha on 5/13/2017.
 */

public class CityAdapterLoca extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Object> items;
    private int posCurrent = -1, oldPos=-1;
    private boolean click = false;
    private Context mContext;
    private Utils.OnItemClickListener clickListener;
    private final int city = 0, district = 1;

    public int getOldPos() {
        return oldPos;
    }

    public boolean isClick() {
        return click;
    }

    public void setClick(boolean click) {
        this.click = click;
    }

    public void setOldPos(int oldPos) {
        this.oldPos = oldPos;
        notifyDataSetChanged();
    }

    public int getPosCurrent() {
        return posCurrent;
    }

    public void setPosCurrent(int posCurrent) {
        this.posCurrent = posCurrent;
        notifyDataSetChanged();
    }

    public void setItemClickListener(Utils.OnItemClickListener clickListener){
        this.clickListener = clickListener;
    }
    public CityAdapterLoca(List<Object> items, Context mContext) {
        this.items = items;
        this.mContext = mContext;
    }

    @Override
    public int getItemViewType(int position) {
        if(items.get(position) instanceof City)
            return city;
        if(items.get(position) instanceof District)
            return district;
        return -1;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v;
        final RecyclerView.ViewHolder holder;
        switch (viewType){

            default:
                v = inflater.inflate(R.layout.item_city, parent, false);
                holder = new CityViewHolder(v,mContext);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(clickListener!=null)
                            clickListener.onItemClick(holder.itemView,holder.getLayoutPosition());
                    }
                });
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        if(getItemViewType(position) == city) {
            CityViewHolder holder1 = (CityViewHolder) holder;
            configureCityViewHolder(holder1, position);
        }
        else
            if(getItemViewType(position)==district) {
                CityViewHolder holder1 = (CityViewHolder)holder;
                configureDistrictViewHolder(holder1, position);;
            }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    private void configureCityViewHolder(CityViewHolder vh, int position){
        vh.setIsRecyclable(false);

        vh.setIsRecyclable(false);
        City city = (City) items.get(position);
        if(city !=null){
            vh.getTextView().setText(city.getName());
            //vh.getBtnTotalRoad().setText(city.getTotalRes()+"");
            if(oldPos==position) {
                vh.getCheck().setVisibility(View.VISIBLE);
                vh.getTextView().setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            }
        }
    }
    private void configureDistrictViewHolder(CityViewHolder vh, int position){
        vh.setIsRecyclable(false);

        vh.setIsRecyclable(false);
        District district = (District) items.get(position);
        if(district !=null){
            vh.getTextView().setText(district.getDistrictName());
            //vh.getBtnTotalRoad().setText(city.getTotalRes()+"");
            if(posCurrent==position) {
                vh.getCheck().setVisibility(View.VISIBLE);
                vh.getTextView().setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            }
        }
    }
}
