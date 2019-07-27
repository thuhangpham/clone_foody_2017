package com.thuhang.foody1703311.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.thuhang.foody1703311.R;


/**
 * Created by ThuHang on 3/31/2017.
 */

public class TypeCateViewHolder
        extends RecyclerView.ViewHolder {
    private TextView tvName;
    private ImageView imgView;
    private ImageView imgCheck;

    public ImageView getImgCheck() {
        return imgCheck;
    }

    public void setImgCheck(ImageButton imgCheck) {
        this.imgCheck = imgCheck;
    }

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

    public TypeCateViewHolder(View itemView) {
        super(itemView);
        tvName = (TextView)itemView.findViewById(R.id.textViewNameTypeCate);
        imgView = (ImageView)itemView.findViewById(R.id.imgTypeCate);
        imgCheck = (ImageView) itemView.findViewById(R.id.imgTypeCateChecked);
    }
}
