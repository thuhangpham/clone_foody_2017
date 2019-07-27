package com.thuhang.foody1703311.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thuhang.foody1703311.R;

/**
 * Created by ThuHang on 3/21/2017.
 */

public class RestaurantFoodViewHolder extends RecyclerView.ViewHolder  {

    private TextView tvRestaurantName;
    private TextView tvAddress;
    private RecyclerView recyclerViewPhoto;
    private ImageView imgPhoto;
    private ImageView imgAvatar;
    private TextView tvUserName;
    private TextView tvDate;
    private ImageView imgUser;
    private TextView tvFoodName;

    public TextView getTvFoodName() {
        return tvFoodName;
    }

    public void setTvFoodName(TextView tvFoodName) {
        this.tvFoodName = tvFoodName;
    }

    public TextView getTvAddress() {
        return tvAddress;
    }

    public void setTvAddress(TextView tvAddress) {
        this.tvAddress = tvAddress;
    }

    public TextView getTvDate() {
        return tvDate;
    }

    public void setTvDate(TextView tvDate) {
        this.tvDate = tvDate;
    }

    public ImageView getImgPhoto() {
        return imgPhoto;
    }

    public void setImgPhoto(ImageView imgPhoto) {
        this.imgPhoto = imgPhoto;
    }

    public TextView getTvRestaurantName() {
        return tvRestaurantName;
    }

    public void setTvRestaurantName(TextView tvRestaurantName) {
        this.tvRestaurantName = tvRestaurantName;
    }
    public RecyclerView getRecyclerViewPhoto() {
        return recyclerViewPhoto;
    }
    public TextView getTvUserName() {
        return tvUserName;
    }

    public void setTvUserName(TextView tvUserName) {
        this.tvUserName = tvUserName;
    }

    public ImageView getImgUser() {
        return imgUser;
    }

    public ImageView getImgAvatar() {
        return imgAvatar;
    }
    public void setImgAvatar(ImageView imgAvatar) {
        this.imgAvatar = imgAvatar;
    }
    public void setImgUser(ImageView imgUser) {
        this.imgUser = imgUser;
    }
    public void setRecyclerViewPhoto(RecyclerView recyclerViewPhoto) {
        this.recyclerViewPhoto = recyclerViewPhoto;
    }
    public RestaurantFoodViewHolder( final View itemView){
            super(itemView);
            tvRestaurantName = (TextView) itemView.findViewById(R.id.textViewFoodRestaurantName);
            tvUserName = (TextView) itemView.findViewById(R.id.textViewFoodUserName);
            //recyclerViewPhoto = (RecyclerView)itemView.findViewById(R.id.recyclerViewPhoto);
            imgPhoto = (ImageView) itemView.findViewById(R.id.imgViewFood);
            tvAddress = (TextView)itemView.findViewById(R.id.textViewFoodItemAddress);
            tvDate = (TextView)itemView.findViewById(R.id.textViewFoodDate);
            imgAvatar = (ImageView)itemView.findViewById(R.id.imgViewFoodAvatar);
         tvFoodName = (TextView)itemView.findViewById(R.id.textViewFoodName);
        }
    }

