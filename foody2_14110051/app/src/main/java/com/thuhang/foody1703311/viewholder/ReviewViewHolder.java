package com.thuhang.foody1703311.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thuhang.foody1703311.R;

/**
 * Created by thuha on 4/19/2017.
 */

public class ReviewViewHolder extends RecyclerView.ViewHolder {
    private ImageView imgAvatar;
    private TextView tvName;
    private TextView tvComment;
    private TextView tvReviewAVG;

    public ImageView getImgAvatar() {
        return imgAvatar;
    }

    public void setImgAvatar(ImageView imgAvatar) {
        this.imgAvatar = imgAvatar;
    }

    public TextView getTvName() {
        return tvName;
    }

    public void setTvName(TextView tvName) {
        this.tvName = tvName;
    }

    public TextView getTvComment() {
        return tvComment;
    }

    public void setTvComment(TextView tvComment) {
        this.tvComment = tvComment;
    }

    public TextView getTvReviewAVG() {
        return tvReviewAVG;
    }

    public void setTvReviewAVG(TextView tvReviewAVG) {
        this.tvReviewAVG = tvReviewAVG;
    }

    public ReviewViewHolder(View itemView) {
        super(itemView);
        imgAvatar = (ImageView)itemView.findViewById(R.id.avatar);
        tvName = (TextView)itemView.findViewById(R.id.textViewUserName);
        tvComment = (TextView)itemView.findViewById(R.id.textViewComment);
        tvReviewAVG = (TextView)itemView.findViewById(R.id.textViewReview);
    }
}
