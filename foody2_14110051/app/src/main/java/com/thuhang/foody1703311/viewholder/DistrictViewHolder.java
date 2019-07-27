package com.thuhang.foody1703311.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.thuhang.foody1703311.R;

/**
 * Created by ThuHang on 3/21/2017.
 */

public class DistrictViewHolder extends RecyclerView.ViewHolder {
    private TextView textView;
    private TextView btnTotalRoad;
    private ImageButton imgTitle;
    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public TextView getBtnTotalRoad() {
        return btnTotalRoad;
    }

    public void setBtnTotalRoad(Button btnTotalRoad) {
        this.btnTotalRoad = btnTotalRoad;
    }

    public ImageButton getImgTitle() {
        return imgTitle;
    }

    public void setImgTitle(ImageButton imgTitle) {
        this.imgTitle = imgTitle;
    }

    public DistrictViewHolder(final View itemView) {
        super(itemView);
        textView = (TextView)itemView.findViewById(R.id.textViewDistrictName);
        btnTotalRoad = (TextView)itemView.findViewById(R.id.textViewTotalRoads);
    }

}
