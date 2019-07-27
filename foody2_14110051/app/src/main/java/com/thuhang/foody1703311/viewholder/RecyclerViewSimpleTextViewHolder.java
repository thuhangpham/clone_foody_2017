package com.thuhang.foody1703311.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thuhang.foody1703311.R;

/**
 * Created by ThuHang on 3/31/2017.
 */

public class RecyclerViewSimpleTextViewHolder extends RecyclerView.ViewHolder{
    private TextView textView;
    private ImageView imgCheck;

    public RecyclerViewSimpleTextViewHolder(View itemView) {
        super(itemView);
        this.textView = (TextView)itemView.findViewById(R.id.textViewItemTypeCate);
        this.imgCheck = (ImageView)itemView.findViewById(R.id.imgSimpleChecked);
    }

    public TextView getTextView() {
        return textView;
    }

    public ImageView getImgCheck() {
        return imgCheck;
    }

    public void setImgCheck(ImageView imgCheck) {
        this.imgCheck = imgCheck;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}
