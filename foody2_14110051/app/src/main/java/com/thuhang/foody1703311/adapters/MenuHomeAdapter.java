package com.thuhang.foody1703311.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thuhang.foody1703311.R;
import com.thuhang.foody1703311.models.CategoriesHomeMenu;
import com.thuhang.foody1703311.viewholder.CategoriesMenuMainViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thuha on 4/19/2017.
 */

public class MenuHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Object> items;
    private Context mContext;

    public MenuHomeAdapter(Context mContext) {
        this.mContext = mContext;
        items = new ArrayList<>();
        items = getCategories();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view4 = inflater.inflate(R.layout.item_menu_home,parent, false);
        viewHolder = new CategoriesMenuMainViewHolder(view4);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CategoriesMenuMainViewHolder viewHolder2 = (CategoriesMenuMainViewHolder) holder;
        configureCategoriesMenuViewHolder(viewHolder2,position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    private void configureCategoriesMenuViewHolder(CategoriesMenuMainViewHolder vh, int position){
        CategoriesHomeMenu categories = (CategoriesHomeMenu) items.get(position);
        if(categories !=null){
            vh.getTextView().setText(categories.getTitle());
            vh.getImgButton().setImageResource(categories.getImageDrawable());
        }
    }
    private List<Object> getCategories(){
        List<Object> list = new ArrayList<>();
        list.add(new CategoriesHomeMenu(R.drawable.ic_nearby, "Gần tôi"));
        list.add( new CategoriesHomeMenu(R.drawable.ic_ecoupon, "Coupon"));
        list.add( new CategoriesHomeMenu(R.drawable.ic_dat_cho_uu_dai, "Đặt chỗ ưu đãi"));
        list.add( new CategoriesHomeMenu(R.drawable.dn_ic_more_delivery, "Đặt giao hàng"));
        list.add( new CategoriesHomeMenu(R.drawable.ic_ecard, "E-card"));
        list.add( new CategoriesHomeMenu(R.drawable.ic_game, "Game & Fun"));
        list.add( new CategoriesHomeMenu(R.drawable.ic_icon_binhluanmoi, "Bình luận"));
        list.add( new CategoriesHomeMenu(R.drawable.ic_blogs, "Blogs"));
        list.add( new CategoriesHomeMenu(R.drawable.ic_icon_topthanhvien, "Top thành viên"));
        list.add( new CategoriesHomeMenu(R.drawable.ic_video, "Video"));
        return list;
    }
}
