package com.thuhang.foody1703311.adapters;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.thuhang.foody1703311.R;
import com.thuhang.foody1703311.database.ReviewHandler;
import com.thuhang.foody1703311.models.Restaurant;
import com.thuhang.foody1703311.presenters.PresenterReview;
import com.thuhang.foody1703311.retrofit.ApiUtils;
import com.thuhang.foody1703311.util.Utils;
import com.thuhang.foody1703311.viewholder.LoadingViewHolder;
import com.thuhang.foody1703311.viewholder.MenuMainViewHolder;
import com.thuhang.foody1703311.viewholder.RestaurantViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.thuhang.foody1703311.util.Constant.DATABASE_NAME;
import static com.thuhang.foody1703311.util.Constant.VERSION;
import static com.thuhang.foody1703311.util.Utils.fortmartStr;

/**
 * Created by ThuHang on 4/12/2017.
 */

public class RestaurantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Restaurant> items;
    private Context mContext;
    private Utils.OnItemClickListener itemClickListener;
    private List<Object> reviewList;
    private PresenterReview mPresenterReview;
    private ReviewHandler reviewHandler;
    private static final int
            TYPE_RESTAURANT = 0, TYPE_LOAD_MORE_RES = 1, HEADER = 2;

    public void setItemClickListener(Utils.OnItemClickListener clickListener) {
        this.itemClickListener = clickListener;
    }

    public RestaurantAdapter(List<Restaurant> items, Context mContent) {
        this.items = items;
        this.mContext = mContent;
        reviewList = new ArrayList<>();
        reviewHandler = new ReviewHandler(mContext, DATABASE_NAME, null, VERSION);
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override
    public int getItemViewType(int position) {
//        if(isPositionHeader(position))
//            return HEADER;
        if (getItem(position) instanceof Restaurant)
            return TYPE_RESTAURANT;
        if (getItem(position) == null)
            return TYPE_LOAD_MORE_RES;
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
//            case HEADER:
//                View view6 = inflater.inflate(R.layout.layout_header, parent, false);
//                viewHolder = new MenuMainViewHolder(view6);
//                break;
            case TYPE_RESTAURANT:
                View view2 = inflater.inflate(R.layout.item_places, parent, false);
                viewHolder = new RestaurantViewHolder(view2);
                final RecyclerView.ViewHolder finalViewHolder6 = viewHolder;
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (itemClickListener != null)
                            itemClickListener.onItemClick(finalViewHolder6.itemView, finalViewHolder6.getLayoutPosition());
                    }
                });
                break;
            default:
                View view7 = inflater.inflate(R.layout.layout_loading_item, parent, false);
                viewHolder = new LoadingViewHolder(view7);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
//            case HEADER:
//                MenuMainViewHolder viewHolder2 = (MenuMainViewHolder) holder;
//                configureHeadingViewHolder(viewHolder2, position);
//                break;
            case TYPE_RESTAURANT:
                RestaurantViewHolder viewHolder1 = (RestaurantViewHolder) holder;
                configureRestaurantViewHolder(viewHolder1, position);
                break;
            default:
                LoadingViewHolder viewHolder7 = (LoadingViewHolder) holder;
                configureLoadingViewHolder(viewHolder7, position);
                break;
        }

    }

    private Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void configureRestaurantViewHolder(RestaurantViewHolder vh, int position) {
        Restaurant restaurant = (Restaurant)items.get(position);
        if (restaurant != null) {
            String avg = restaurant.getAveragePoint() + "";
            if (avg.trim().length() > 3)
                avg = avg.substring(0, 3);
            vh.getTvAvgReview().setText(avg);
            vh.getTvAdress().setText(fortmartStr(restaurant.getAddress(),40));
            vh.getTvRestaurantName().setText(fortmartStr(restaurant.getName(),37));

//            String uri = "drawable/fdi"+restaurant.getImg().trim().replace(".png","");
//            int imgResource = mContext.getResources().getIdentifier(uri, null, mContext.getPackageName());
//            try {
//                Drawable image = mContext.getResources().getDrawable(imgResource);
//                vh.getImgPhoto().setImageDrawable(image);
//            }catch (OutOfMemoryError e){
//                e.printStackTrace();
        }
        String uri = ApiUtils.BASE_URL + "item/images/res/fdi" + restaurant.getImg() + ".png";
        Log.e("uri", uri);
        Glide.with(mContext).load(uri).crossFade().into(vh.getImgPhoto());
        Double rating = Double.valueOf(restaurant.getAveragePoint());
        vh.getTvAvgReview().setText(String.format("%.1f", rating) + "");
        vh.getTvTotalReviews().setText(restaurant.getTotalReviews() + "");
        vh.getTvTotalImage().setText(restaurant.getTotalPhoto() + "");
//        mPresenterReview = new PresenterReview(this);
//        mPresenterReview.getReviewByItem(restaurant.getItemId());
        ReviewHandler reviewHandler = new ReviewHandler(mContext, DATABASE_NAME, null, VERSION);
        reviewList = reviewHandler.getReviewByItemId(restaurant.getItemId());
        Log.e("rv",reviewList.size()+"");
        ReviewAdapter reviewAdapter = new ReviewAdapter(mContext, reviewList);
        vh.getRCComment().setLayoutManager(new LinearLayoutManager(mContext.getApplicationContext()));
        vh.getRCComment().setAdapter(reviewAdapter);
    }

    private void configureLoadingViewHolder(LoadingViewHolder vh, int position) {
        vh.getProgressBar().setIndeterminate(true);
    }

    private void configureHeadingViewHolder(MenuMainViewHolder vh, int position) {
        vh.setIsRecyclable(false);
        loadSlide(vh.getmViewpagerSlide());
    }

    private void loadSlide(final ViewPager mViewpagerSlide) {
        Timer timer;
        final int[] slideList = {R.drawable.slide4, R.drawable.slide5};
        MyCustomPagerAdapter myCustomPagerAdapter = new MyCustomPagerAdapter(mContext, slideList);
        mViewpagerSlide.setAdapter(myCustomPagerAdapter);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                mViewpagerSlide.post(new Runnable() {

                    @Override
                    public void run() {
                        mViewpagerSlide.setCurrentItem((mViewpagerSlide.getCurrentItem() + 1) % slideList.length);
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 1000, 5000);
    }
}

