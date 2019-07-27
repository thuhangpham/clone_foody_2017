package com.thuhang.foody1703311.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.thuhang.foody1703311.R;
import com.thuhang.foody1703311.models.Categories;

import java.util.List;

import static com.thuhang.foody1703311.fragments.PlacesFragment.LST_ICON_ACT;

/**
 * Created by ThuHang on 3/28/2017.
 */

public class CategoriesListViewAdapter extends BaseAdapter {
    private int[] imgList;
    private List<Categories> cateList;
    private Context mContext;
    private int posCurrentTab1=0;

    public int getPosCurrentTab1() {
        return posCurrentTab1;
    }

    public void setPosCurrentTab1(int posCurrentTab1){
        this.posCurrentTab1 = posCurrentTab1;
        notifyDataSetChanged();
    }
    public CategoriesListViewAdapter(Context context, int[] imgs, List<Categories> categories) {
        this.mContext = context;
        this.cateList = categories;
        this.imgList= imgs;
    }
    public interface OnItemClickListener{
        void OnItemClick(View v, int position);
    }
    private OnItemClickListener clickListener;
    public void setOnClickListener (OnItemClickListener onClickListener){
        this.clickListener = onClickListener;
    }
    @Override
    public int getCount() {
        return imgList.length;
    }
    @Override
    public Object getItem(int position) {
        return cateList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View view = convertView;
        LayoutInflater inflater;
        TextView txtName;
        ImageView img, imgCheck;
        //if(view==null)
        {
            inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(R.layout.item_category,null,false);
        }
        txtName = (TextView) view.findViewById(R.id.textViewNameCate);
        img = (ImageView) view.findViewById(R.id.imgCate);
        imgCheck = (ImageView)view.findViewById(R.id.imgCateChecked);
        if(position>=0)
            txtName.setText(cateList.get(position).getTitle());
        try {
            img.setImageResource(imgList[position]);
        }catch (OutOfMemoryError e){
            e.printStackTrace();
        }
        if(position==posCurrentTab1) {
            txtName.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            img.setImageResource(LST_ICON_ACT[posCurrentTab1]);
            imgCheck.setVisibility(View.VISIBLE);
    }
    view.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(clickListener!=null)
                clickListener.OnItemClick(v, position);
        }
    });
      /*  view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position!=POS_CURRENT_TAB1) {
                    TextView tv;
                    tv = (TextView)listView1.getChildAt(position).findViewById(R.id.textViewNameCate);
                    tvTab1.setText(tv.getText());  // set text for tab 1
                    POS_CURRENT_TAB1 = position;
                    CATEGORY_ID = Integer.parseInt(cateList.get(position).getId());
                    OFFSET_NO_ROW=0;
                    PlacesFragment.hideTab();
                }
            }
        });*/
        return view;
    }}