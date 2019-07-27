package com.thuhang.foody1703311.util;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.thuhang.foody1703311.interfaces.IPlacesLoadmore;

/**
 * Created by ThuHang on 4/6/2017.
 */

public class EndlessOnScrollListener  extends RecyclerView.OnScrollListener {
    private RecyclerView.LayoutManager layoutManager;
    private IPlacesLoadmore iLoadMore;
    private int visibleThreshold ;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;


    public EndlessOnScrollListener(RecyclerView.LayoutManager layoutManager, IPlacesLoadmore iLoadMore) {
        this.layoutManager = layoutManager;
        this.iLoadMore = iLoadMore;
        lastVisibleItem = 0;
        totalItemCount = 0;
        if(layoutManager instanceof LinearLayoutManager)
            visibleThreshold = 6;
        if(layoutManager instanceof GridLayoutManager)
            visibleThreshold = 6;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if(dy>0) { // Scrool up
            totalItemCount = layoutManager.getItemCount();
            if (layoutManager instanceof LinearLayoutManager)
                lastVisibleItem = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            if (layoutManager instanceof GridLayoutManager)
                lastVisibleItem = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            Log.e("Loadmore", dx + ", y= " + dy);
            Log.e("items", totalItemCount + ", " + lastVisibleItem);
            if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                if (iLoadMore != null) {
                    iLoadMore.getDataLoadMore(totalItemCount + 1,null);
                }
                loading = true;
            }
        }
    }
    public void setLoading(boolean loading){
        this.loading = loading;
    }
}
