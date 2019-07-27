package com.thuhang.foody1703311.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thuhang.foody1703311.R;

/**
 * Created by ThuHang on 3/21/2017.
 */

public class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
    private TextView tvAvgReview;
    private TextView tvRestaurantName;
    private TextView tvAdress;
    private ImageView imgViewMore;
    private ImageView imgPhoto;
    private TextView tvTotalImage;
    private TextView tvTotalReviews;
    private RecyclerView RCComment;
    public ImageView getImgPhoto() {
        return imgPhoto;
    }

    public void setImgPhoto(ImageView imgPhoto) {
        this.imgPhoto = imgPhoto;
    }

    public TextView getTvAvgReview() {
        return tvAvgReview;
    }

    public void setTvAvgReview(TextView tvAvgReview) {
        this.tvAvgReview = tvAvgReview;
    }

    public TextView getTvRestaurantName() {
        return tvRestaurantName;
    }

    public void setTvRestaurantName(TextView tvRestaurantName) {
        this.tvRestaurantName = tvRestaurantName;
    }
    public TextView getTvAdress() {
        return tvAdress;
    }

    public void setTvAdress(TextView tvAdress) {
        this.tvAdress = tvAdress;
    }


    public TextView getTvTotalImage() {
        return tvTotalImage;
    }

    public void setTvTotalImage(TextView tvTotalImage) {
        this.tvTotalImage = tvTotalImage;
    }

    public TextView getTvTotalReviews() {
        return tvTotalReviews;
    }

    public void setTvTotalReviews(TextView tvTotalReviews) {
        this.tvTotalReviews = tvTotalReviews;
    }


    public ImageView getImgViewMore() {
        return imgViewMore;
    }

    public void setImgViewMore(ImageView imgViewMore) {
        this.imgViewMore = imgViewMore;
    }

    public RecyclerView getRCComment() {
        return RCComment;
    }

    public void setRCComment(RecyclerView RCComment) {
        this.RCComment = RCComment;
    }

    public RestaurantViewHolder(final View itemView) {
        super(itemView);
        tvAvgReview = (TextView)itemView.findViewById(R.id.textViewAvgReview);
        tvRestaurantName = (TextView)itemView.findViewById(R.id.textViewRestaurantName);
        tvAdress = (TextView)itemView.findViewById(R.id.textViewAddress);
        imgViewMore = (ImageView)itemView.findViewById(R.id.imgViewActionMore);
        tvTotalImage = (TextView)itemView.findViewById(R.id.textViewTotalPhoto);
        tvTotalReviews = (TextView)itemView.findViewById(R.id.textViewTotalComment);
        //recyclerViewPhoto = (RecyclerView)itemView.findViewById(R.id.recyclerViewPhoto);
        imgPhoto = (ImageView)itemView.findViewById(R.id.imgViewRestaurant);
        RCComment = (RecyclerView)itemView.findViewById(R.id.recyclerViewCommentPlaces);
        imgViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.showContextMenu();
            }
        });
        imgViewMore.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, v.getId(), 0, "Lưu lại");//groupId, itemId, order, title
        menu.add(0, v.getId(), 0, "Viết bình luận");
        menu.add(0, v.getId(), 0, "Chia sẻ");
        menu.add(0, v.getId(), 0, "Báo lỗi");
        menu.add(0, v.getId(), 0, "Ẩn địa điểm");
        menu.add(0, v.getId(), 0, "Hủy");
    }


}
