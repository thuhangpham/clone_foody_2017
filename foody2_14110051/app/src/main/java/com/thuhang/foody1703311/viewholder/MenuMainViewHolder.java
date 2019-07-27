package com.thuhang.foody1703311.viewholder;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.thuhang.foody1703311.R;

/**
 * Created by thuha on 4/19/2017.
 */

public class MenuMainViewHolder extends RecyclerView.ViewHolder{
    private ViewPager mViewpagerSlide;

    public ViewPager getmViewpagerSlide() {
        return mViewpagerSlide;
    }

    public void setmViewpagerSlide(ViewPager mViewpagerSlide) {
        this.mViewpagerSlide = mViewpagerSlide;
    }
    public MenuMainViewHolder(View itemView) {
        super(itemView);
        mViewpagerSlide = (ViewPager)itemView.findViewById(R.id.viewpagerSlide);
    }
}
