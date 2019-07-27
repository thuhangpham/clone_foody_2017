package com.thuhang.foody1703311.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thuhang.foody1703311.R;


/**
 * Created by ThuHang on 3/31/2017.
 */

public class CategoryViewHolder
        extends RecyclerView.ViewHolder {
    private TextView tvName;
    private ImageView imgView;

    public ImageView getImgCheck() {
        return imgCheck;
    }

    public void setImgCheck(ImageView imgCheck) {
        this.imgCheck = imgCheck;
    }

    private ImageView imgCheck;

    public TextView getTvName() {
        return tvName;
    }

    public void setTvName(TextView tvName) {
        this.tvName = tvName;
    }

    public ImageView getImgView() {
        return imgView;
    }

    public void setImgView(ImageView imgView) {
        this.imgView = imgView;
    }

    public CategoryViewHolder(View itemView) {
        super(itemView);
        tvName = (TextView)itemView.findViewById(R.id.textViewNameCate);
        imgView = (ImageView)itemView.findViewById(R.id.imgCate);
    }
}
