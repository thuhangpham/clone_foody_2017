package com.thuhang.foody1703311.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by ThuHang on 3/31/2017.
 */

public class ItemImgViewRecyclerViewHolder extends RecyclerView.ViewHolder {
    private ImageView imgViewPhoto;

    public ImageView getImgViewPhoto() {
        return imgViewPhoto;
    }

    public void setImgViewPhoto(ImageView imgViewPhoto) {
        this.imgViewPhoto = imgViewPhoto;
    }

    public ItemImgViewRecyclerViewHolder(final View itemView) {
        super(itemView);
        /*imgViewPhoto = (ImageView) itemView.findViewById(R.id.imgViewPhotoRestaurant);*/
    }

}
