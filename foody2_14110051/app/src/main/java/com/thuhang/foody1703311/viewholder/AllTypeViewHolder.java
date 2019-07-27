package com.thuhang.foody1703311.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.thuhang.foody1703311.R;

/**
 * Created by thuha on 5/14/2017.
 */

public class AllTypeViewHolder extends RecyclerView.ViewHolder {
    private RecyclerView recyclerView;
    private TextView tvTitle;

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public void setTvTitle(TextView tvTitle) {
        this.tvTitle = tvTitle;
    }

    public AllTypeViewHolder(final View itemView) {
        super(itemView);
        recyclerView = (RecyclerView) itemView.findViewById(R.id.lv_type_addlocation);
        tvTitle = (TextView)itemView.findViewById(R.id.typeName);

    }

}
