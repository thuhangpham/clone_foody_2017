package com.thuhang.foody1703311.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thuhang.foody1703311.R;
import com.thuhang.foody1703311.models.TypeCate;
import com.thuhang.foody1703311.util.Utils;
import com.thuhang.foody1703311.viewholder.RecyclerViewSimpleTextViewHolder;
import com.thuhang.foody1703311.viewholder.TypeCateViewHolder;

import java.util.List;

/**
 * Created by ThuHang on 4/12/2017.
 */

public class TypeCateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Object> items;
    private Context mContext;
    private int posCurrentTab2;
    private final int string =0, typeCate = 1;
    public void setPosCurrentTab2(int posCurrentTab2){
        this.posCurrentTab2 = posCurrentTab2;
        notifyDataSetChanged();
    }

    private Utils.OnItemClickListener clickListener;
    public void setOnItemClickListener(Utils.OnItemClickListener clickListener){
        this.clickListener = clickListener;
    }

    public int getPosCurrentTab2() {
        return posCurrentTab2;
    }

    @Override
    public int getItemViewType(int position) {
        if(items.get(position) instanceof TypeCate)
            return typeCate;
        return -1;
    }

    public TypeCateAdapter(List<Object> items, Context mContext) {
        this.items = items;
        this.mContext = mContext;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder;
        if(viewType==typeCate) {
            View view = inflater.inflate(R.layout.item_type_category, parent, false);
            viewHolder = new TypeCateViewHolder(view);
            final RecyclerView.ViewHolder finalViewHolder = viewHolder;
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null)
                        clickListener.onItemClick(finalViewHolder.itemView, finalViewHolder.getLayoutPosition());
                }
            });
        }else{
            View view = inflater.inflate(R.layout.item_textview,parent, false);
            viewHolder = new RecyclerViewSimpleTextViewHolder(view);
            final RecyclerView.ViewHolder finalViewHolder = viewHolder;
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(clickListener!=null)
                        clickListener.onItemClick( finalViewHolder.itemView, finalViewHolder.getLayoutPosition());
                }
            });
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == typeCate) {
            TypeCateViewHolder holder1 = (TypeCateViewHolder) holder;
            configureCategoriesViewHolder(holder1, position);
        }
        else {
            RecyclerViewSimpleTextViewHolder viewHolder = (RecyclerViewSimpleTextViewHolder)holder;
            configureDefaultViewHolder(viewHolder, position);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    private void configureCategoriesViewHolder(TypeCateViewHolder vh, int position){
        vh.setIsRecyclable(false);
        TypeCate category = (TypeCate) items.get(position);
        if(category !=null){
            String uri = "drawable/fd"+category.getImg().trim().replace(".png","");
            int imgResource = mContext.getResources().getIdentifier(uri, null, mContext.getPackageName());
            try {
                Drawable image = mContext.getResources().getDrawable(imgResource);
                vh.getImgView().setImageDrawable(image);
            }catch (OutOfMemoryError e){
                e.printStackTrace();
            }
            vh.getTvName().setText(category.getName());
            /* Dat lai mau cho dong duoc chon truoc do*/
            if(position==posCurrentTab2){
                vh.getTvName().setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                vh.getImgCheck().setVisibility(View.VISIBLE);
            }
        }
    }
    private void configureDefaultViewHolder(RecyclerViewSimpleTextViewHolder vh, int position){
        vh.getTextView().setText((CharSequence)(items.get(position)));
        if(position==posCurrentTab2) {
            vh.getTextView().setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            vh.getImgCheck().setVisibility(View.VISIBLE);
        }
        else{
            vh.getTextView().setTextColor(mContext.getResources().getColor(R.color.text_color));
            vh.getImgCheck().setVisibility(View.INVISIBLE);
        }
    }

}
