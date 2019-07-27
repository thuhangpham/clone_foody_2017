package com.thuhang.foody1703311.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.thuhang.foody1703311.R;
import com.thuhang.foody1703311.models.RestaurantFood;
import com.thuhang.foody1703311.viewholder.LoadingViewHolder;
import com.thuhang.foody1703311.viewholder.RestaurantFoodViewHolder;

import java.util.List;

/**
 * Created by ThuHang on 4/12/2017.
 */

public class RestaurantFoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<RestaurantFood> items;
    private Context mContext;
    private static final int
            TYPE_RESTAURANT = 0, TYPE_LOAD_MORE_RES = 1;
    public RestaurantFoodAdapter (Context context, List<RestaurantFood>  items){
        this.items = items;
        this.mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if(items.get(position) instanceof RestaurantFood)
            return TYPE_RESTAURANT;
        if(items.get(position) == null)
            return TYPE_LOAD_MORE_RES;
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_RESTAURANT:
                View view = inflater.inflate(R.layout.item_food, parent, false);
                viewHolder = new RestaurantFoodViewHolder(view);
                final RecyclerView.ViewHolder finalViewHolder8 = viewHolder;
                break;
            default:
                View view7 = inflater.inflate(R.layout.layout_loading_item,parent, false);
                viewHolder = new LoadingViewHolder(view7);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_RESTAURANT:
                RestaurantFoodViewHolder holder1 = (RestaurantFoodViewHolder) holder;
                configureRestaurantFoodViewHolder(holder1, position);
                break;
            default:
                LoadingViewHolder viewHolder7 = (LoadingViewHolder) holder;
                configureLoadingViewHolder(viewHolder7,position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void configureRestaurantFoodViewHolder(RestaurantFoodViewHolder vh, int position){
        RestaurantFood restaurant = items.get(position);
        if(restaurant != null){
            vh.getTvFoodName().setText(restaurant.getFoodName());
            String name = restaurant.getName();
            if(name.trim().length()>19)
                name = name.substring(0,19)+"...";
            vh.getTvRestaurantName().setText(name);
            String address = restaurant.getAddress();
            if(address.trim().length()>24)
                address = address.substring(0,24)+"...";
            vh.getTvAddress().setText(address);
            vh.getTvDate().setText(restaurant.getDate());
            vh.getTvUserName().setText(restaurant.getUserName());
            Glide.with(mContext).load(restaurant.getImg()).into(vh.getImgPhoto());
            Glide.with(mContext).load(restaurant.getUseravatar()).error(R.drawable.default_avatar).into(vh.getImgAvatar());
//            String uri = "drawable/fdi"+restaurant.getImg().trim().replace(".png","");
//            try {
//                int imgResource = mContext.getResources().getIdentifier(uri, null, mContext.getPackageName());
//                Drawable image = mContext.getResources().getDrawable(imgResource);
//                vh.getImgPhoto().setImageDrawable(image);
//            }catch (OutOfMemoryError e){
//                e.printStackTrace();
//            }

        }
    }
    private void configureLoadingViewHolder(LoadingViewHolder vh, int position){
        vh.getProgressBar().setIndeterminate(true);
    }
}
