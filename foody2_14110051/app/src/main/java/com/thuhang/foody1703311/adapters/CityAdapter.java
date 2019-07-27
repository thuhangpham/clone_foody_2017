package com.thuhang.foody1703311.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thuhang.foody1703311.R;
import com.thuhang.foody1703311.models.City;
import com.thuhang.foody1703311.util.Utils;
import com.thuhang.foody1703311.viewholder.CityViewHolder;
import com.thuhang.foody1703311.viewholder.ViewHolder;

import java.util.List;

/**
 * Created by ThuHang on 4/12/2017.
 */

public class CityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Object> items;
    private int posCurrent = -1, oldPos;
    private boolean click = false;
    private Context mContext;
    private Utils.OnItemClickListener clickListener;
    private final int search = 0, location = 1, changeCountry = 2, city = 3;

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
    }

    public int getPosCurrent() {
        return posCurrent;
    }

    public void setPosCurrent(int posCurrent) {
        this.posCurrent = posCurrent;
    }

    public void setItemClickListener(Utils.OnItemClickListener clickListener){
        this.clickListener = clickListener;
    }
    public CityAdapter(List<Object> items, Context mContext) {
        this.items = items;
        this.mContext = mContext;
        items.add(0, new Search());
        items.add(1, new Location());
        items.add(2, new ChangeCountry());
    }

    @Override
    public int getItemViewType(int position) {
        if(items.get(position) instanceof Search)
            return search;
        if(items.get(position) instanceof Location)
            return location;
        if(items.get(position) instanceof ChangeCountry)
            return changeCountry;
        if(items.get(position) instanceof City)
            return city;
        return -1;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v;
        final RecyclerView.ViewHolder holder;
        switch (viewType){
            case search:
                 v = inflater.inflate(R.layout.layout_item_search, parent, false);
                 holder = new ViewHolder(v);
                break;
            case location:
                v = inflater.inflate(R.layout.layout_item_auto_location, parent, false);
                holder =  new ViewHolder(v);
                break;
            case changeCountry:
                v = inflater.inflate(R.layout.layout_item_change_country, parent, false);
                holder = new ViewHolder(v);
                break;
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
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    private void configureCityViewHolder(CityViewHolder vh, int position){
        vh.setIsRecyclable(false);
        City city = (City) items.get(position);
        if(city !=null){
            vh.getTextView().setText(city.getName());
            //vh.getBtnTotalRoad().setText(city.getTotalRes()+"");
        }
        if(position==oldPos){
            vh.getImgStick().setVisibility(View.VISIBLE);
            vh.getTextView().setTextColor(mContext.getResources().getColor(R.color.blue));
            if(click)
                vh.getImgStick().setVisibility(View.INVISIBLE);
        }
        if(position==posCurrent){
            vh.getImgStick().setVisibility(View.VISIBLE);
            vh.getBtnCity().setVisibility(View.VISIBLE);
        }
        if (position == posCurrent && position == oldPos)
            vh.getBtnCity().setVisibility(View.INVISIBLE);

    }
   private class Search{}
   private class Location{}
   private class ChangeCountry{}
}

