package com.thuhang.foody1703311;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.thuhang.foody1703311.adapters.CityAdapter;
import com.thuhang.foody1703311.database.CityHandler;
import com.thuhang.foody1703311.models.City;
import com.thuhang.foody1703311.util.Utils;
import com.thuhang.foody1703311.viewholder.CityViewHolder;

import java.util.ArrayList;
import java.util.List;

import static com.thuhang.foody1703311.util.Constant.DATABASE_NAME;
import static com.thuhang.foody1703311.util.Constant.VERSION;

public class ChooseCityActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private CityAdapter mCityAdapter;
    private RecyclerView mRecyclerViewCity;
    private List<Object> mCityList;
    LinearLayoutManager mLayoutManager;
    public static final String CITY_SETTING = "MyCitySetting";
    public static final String CITY_POSITION = "MyCityDefault";
    public static final String CITY_STORED= "MyCity";

    private int pos;
    public static int cityStored, posStored;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city);
        connectView();
        mCityList = new ArrayList<>();
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setTitle(R.string.choose_city);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Restore preferences
        SharedPreferences settings = getSharedPreferences(CITY_SETTING, 0);
        pos = settings.getInt(CITY_POSITION, 3);
        configListCity();

    }
    private void configListCity(){
        CityHandler handler = new CityHandler(this,DATABASE_NAME, null, VERSION);
        mCityList = handler.getAllCity();
        mCityAdapter = new CityAdapter(mCityList, this);
        mCityAdapter.setOldPos(pos);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewCity.setLayoutManager(mLayoutManager);
        mRecyclerViewCity.setAdapter(mCityAdapter);
        mCityAdapter.setItemClickListener(new Utils.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                setItemClickListener(itemView, position);
            }
        });
    }
    private void setItemClickListener(View view, int position){
        View view1 = mRecyclerViewCity.getChildAt(pos);
        CityViewHolder cityViewHolder;
        if(view1!=null) {
            cityViewHolder = new CityViewHolder(view1, this);
            cityViewHolder.getImgStick().setVisibility(View.INVISIBLE);
        }
        Log.e("posCity", mCityAdapter.getPosCurrent()+"");
        CityViewHolder holder = new CityViewHolder(view, this);
        if(pos==position)
            holder.getBtnCity().setVisibility(View.INVISIBLE);

        // scroll den position offset 78 pixel
        mLayoutManager.scrollToPositionWithOffset(position, 78);
        mCityAdapter.setPosCurrent(position);
        mCityAdapter.setClick(true);

        if(pos!=position) {
            City city = (City) mCityList.get(position);
            cityStored = Integer.parseInt(city.getId());
            posStored = position;
        }
        mRecyclerViewCity.setAdapter(mCityAdapter);
    }
    private void connectView(){
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mRecyclerViewCity = (RecyclerView)findViewById(R.id.recyclerViewChangeCity);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_choose_city,menu);
        return true;
    }
}
