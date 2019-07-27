package com.thuhang.foody1703311.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.thuhang.foody1703311.R;
import com.thuhang.foody1703311.models.District;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ThuHang on 3/29/2017.
 */

public class ExpanableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private int pos=-1;
    private List<Object> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
        notifyDataSetChanged();
    }

    public ExpanableListAdapter(Context context, List<Object> listDataHeader,
                                HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        District district = (District)this._listDataHeader.get(groupPosition);
        return this._listDataChild.get(district.getDistrictName())
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, final ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_street,parent,false);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.textViewItemTypeCate);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        District district = (District)this._listDataHeader.get(groupPosition);
        return this._listDataChild.get(district.getDistrictName())
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded,
                             View convertView, final ViewGroup parent) {
        District district = (District)getGroup(groupPosition);
        String headerTitle = district.getDistrictName();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_address_info, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.textViewDistrictName);
        TextView tvCount = (TextView)convertView.findViewById(R.id.textViewTotalRoads);
        Log.e("str count",district.getStreetCount()+"");
        tvCount.setText(district.getStreetCount()+" đường");
        lblListHeader.setText(headerTitle);
        View btn = convertView.findViewById(R.id.duong);
//        if(pos==groupPosition) {
//            lblListHeader.setTextColor(_context.getResources().getColor(R.color.colorPrimary));
//        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded) ((ExpandableListView) parent).collapseGroup(groupPosition);
                else ((ExpandableListView) parent).expandGroup(groupPosition, true);
            }
        });
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}