package com.thuhang.foody1703311.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thuhang.foody1703311.R;
import com.thuhang.foody1703311.models.TypeCate;
import com.thuhang.foody1703311.util.Utils;
import com.thuhang.foody1703311.viewholder.CityViewHolder;

import java.util.List;

/**
 * Created by ThuHang on 4/12/2017.
 */

public class TypeAdapterLoca extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Object> items;
    private Context mContext;
    private int posCurrentTab2=-1;
    private final int string =0, typeCate = 1;
    public void setPosCurrentTab2(int posCurrentTab2){
        this.posCurrentTab2 = posCurrentTab2;
        notifyDataSetChanged();
    }

    private Utils.OnItemClickListener clickListener;
    public void setOnItemClickListener(Utils.OnItemClickListener clickListener){
        this.clickListener = clickListener;
    }

    public int getPosCurrentTab2() {
        return posCurrentTab2;
    }

    @Override
    public int getItemViewType(int position) {
        if(items.get(position) instanceof TypeCate)
            return typeCate;
        return -1;
    }

    public TypeAdapterLoca(List<Object> items, Context mContext) {
        this.items = items;
        this.mContext = mContext;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == typeCate) {
            View view = inflater.inflate(R.layout.item_city, parent, false);
            viewHolder = new CityViewHolder(view, mContext);
            final RecyclerView.ViewHolder finalViewHolder = viewHolder;
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null)
                        clickListener.onItemClick(finalViewHolder.itemView, finalViewHolder.getLayoutPosition());
                }
            });
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == typeCate) {
            CityViewHolder holder1 = (CityViewHolder) holder;
            configureCategoriesViewHolder(holder1, position);
        }
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
    private void configureCategoriesViewHolder(CityViewHolder vh, int position){
        vh.setIsRecyclable(false);
        TypeCate category = (TypeCate) items.get(position);
        if(category !=null){
            vh.getTextView().setText(category.getName());
            /* Dat lai mau cho dong duoc chon truoc do*/
            if(position==posCurrentTab2) {
                vh.getTextView().setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                vh.getImgStick().setVisibility(View.VISIBLE);
            }
            else{
                vh.getTextView().setTextColor(mContext.getResources().getColor(R.color.text_color));
                vh.getImgStick().setVisibility(View.INVISIBLE);
            }
        }
    }

}
