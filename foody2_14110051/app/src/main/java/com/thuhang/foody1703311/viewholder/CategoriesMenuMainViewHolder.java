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

public class CategoriesMenuMainViewHolder extends RecyclerView.ViewHolder {
    private ImageView imgButton;
    private TextView textView;

    public ImageView getImgButton() {
        return imgButton;
    }

    public void setImgButton(ImageButton img) {
        this.imgButton = img;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public CategoriesMenuMainViewHolder(final View itemView) {
        super(itemView);
        imgButton = (ImageView)itemView.findViewById(R.id.imgMenuMain);
        textView = (TextView)itemView.findViewById(R.id.txtMain);
    }

}
