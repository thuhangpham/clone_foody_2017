package com.thuhang.foody1703311.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thuhang.foody1703311.R;

/**
 * Created by ThuHang on 4/16/2017.
 */

public class CategoryTypeViewHolder extends RecyclerView.ViewHolder  {
    private TextView tvName;
    private ImageView img;

    public TextView getTvName() {
        return tvName;
    }

    public void setTvName(TextView tvName) {
        this.tvName = tvName;
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    public CategoryTypeViewHolder(View itemView) {
        super(itemView);
        img = (ImageView)itemView.findViewById(R.id.imgCatetogoryType);
        tvName = (TextView)itemView.findViewById(R.id.textViewCateTypeName);
    }
}
