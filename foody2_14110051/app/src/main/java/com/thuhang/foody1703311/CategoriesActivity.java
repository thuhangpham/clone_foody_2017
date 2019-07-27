package com.thuhang.foody1703311;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.thuhang.foody1703311.adapters.CateAdapter;
import com.thuhang.foody1703311.database.CategoryTypeHandler;
import com.thuhang.foody1703311.fragments.PlacesFragment;
import com.thuhang.foody1703311.models.CategoryType;
import com.thuhang.foody1703311.util.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.thuhang.foody1703311.adapters.CateAdapter.pos;
import static com.thuhang.foody1703311.util.Constant.DATABASE_NAME;
import static com.thuhang.foody1703311.util.Constant.VERSION;

public class CategoriesActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mGrigLayout;
    private CateAdapter mAdapter;
    private int[] iconList = {R.drawable.ic_h_angi, R.drawable.ic_h_dulich, R.drawable.ic_h_cuoihoi,
            R.drawable.ic_h_depkhoe,R.drawable.ic_h_giaitri, R.drawable.ic_h_muasam,
            R.drawable.ic_h_giaoduc, R.drawable.ic_h_dichvu};
    private List<Object> cateTypeList;
    public static int POSITION=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        connectView();
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if(ab!=null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle(getString(R.string.categories));
        }
        setRecyclerView();
    }
    private void setRecyclerView(){
        cateTypeList = new ArrayList<>();
        CategoryTypeHandler handler = new CategoryTypeHandler(this, DATABASE_NAME, null, VERSION);
        cateTypeList = handler.getALlCateType();
        mAdapter = new CateAdapter(this,iconList,cateTypeList);
        mGrigLayout = new GridLayoutManager(this,4);
        mRecyclerView.setLayoutManager(mGrigLayout);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(new Utils.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                PlacesFragment placesFragment = PlacesFragment.newInstance();
                if(pos!=position) {
                    CategoryType type = (CategoryType) cateTypeList.get(position);
                    CategoryType.CATE_TYPE_ID = type.getId();
                    POSITION = position;
                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(i);
                }
                pos = position;
                onBackPressed();
            }
        });

    }
    private void connectView(){
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerViewChangeCate);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
