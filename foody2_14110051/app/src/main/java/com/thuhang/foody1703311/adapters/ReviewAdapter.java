package com.thuhang.foody1703311.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.thuhang.foody1703311.R;
import com.thuhang.foody1703311.models.Review;
import com.thuhang.foody1703311.retrofit.ApiUtils;
import com.thuhang.foody1703311.viewholder.ReviewViewHolder;

import java.util.List;

/**
 * Created by thuha on 4/19/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Object> items;
    private Context mContext;
    public ReviewAdapter(Context context, List<Object> items) {
        this.mContext = context;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_places_comment, parent, false);
        RecyclerView.ViewHolder holder =   new ReviewViewHolder(v);
        return holder;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
       ReviewViewHolder holder1 = (ReviewViewHolder)holder;
        Review review = (Review) items.get(position);
        //holder1.getImgAvatar().setImageResource();

       /* String uri = "drawable/ava"+review.getAvatar().trim().replace(".png","");

        int imgResource = mContext.getResources().getIdentifier(uri, null, mContext.getPackageName());
        try {
//            Drawable image = mContext.getResources().getDrawable(imgResource);
//            holder1.getImgAvatar().setImageDrawable(image);
        }catch (OutOfMemoryError e){
            e.printStackTrace();
        }*/
        String uri = ApiUtils.BASE_URL + "user/images/avatar/ava" + review.getAvatar()+".png";
        Glide.with(mContext).load(uri).crossFade().error(R.drawable.default_avatar).into(holder1.getImgAvatar());
        holder1.getTvComment().setText(review.getComment());
        holder1.getTvName().setText(review.getName());
        Double avg =  Double.valueOf(review.getRating()+"");
        holder1.getTvReviewAVG().setText(String.format("%.1f",avg)+"");
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
}
