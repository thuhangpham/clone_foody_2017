package com.thuhang.foody1703311;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thuhang.foody1703311.adapters.ViewPagerAdapter;
import com.thuhang.foody1703311.fragments.FoodFragment;
import com.thuhang.foody1703311.fragments.PlacesFragment;
import com.thuhang.foody1703311.interfaces.IFragmentLifecycle;

import static com.thuhang.foody1703311.CategoriesActivity.POSITION;
import static com.thuhang.foody1703311.UserActivity.USER_ID;
import static com.thuhang.foody1703311.util.Constant.TAB_FOOD;
import static com.thuhang.foody1703311.util.Constant.TAB_PLACES;

public class HomeActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private Context mContext;
    private ViewPager mViewPager;
    private ViewPagerAdapter mPagerAdapter;
    private TextView tvTab1, tvTab2;
    private Toolbar mToolbar;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private int currentPosition;
    public static String TAB_DEFAULT="tabDefault", SET_TAB = "setTab";
    private int[] iconList = {R.drawable.icon_anuong, R.drawable.icon_dulich, R.drawable.icon_cuoihoi,
            R.drawable.icon_depkhoe,R.drawable.icon_giaitri, R.drawable.icon_muasam,
            R.drawable.icon_giaoduc, R.drawable.icon_dichvu};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mContext = this;
        connectView();
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if(ab!=null) {
            ab.setTitle("");
            Drawable drawable= getResources().getDrawable(iconList[POSITION]);
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            Drawable newdrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 33, 33, true));
            newdrawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(newdrawable);
        }

        addPage();

        currentPosition = mViewPager.getCurrentItem();
        onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                IFragmentLifecycle fragmentHide = (IFragmentLifecycle)mPagerAdapter.getItem(currentPosition);
                fragmentHide.onPauseFragment();
                IFragmentLifecycle fragmentShow = (IFragmentLifecycle)mPagerAdapter.getItem(position);
                fragmentShow.onResumeFragment();
                currentPosition=position;

                SharedPreferences preferences = getSharedPreferences(SET_TAB,0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt(TAB_DEFAULT, mViewPager.getCurrentItem());
                editor.apply();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };

        SharedPreferences sharedPreferences = getSharedPreferences(SET_TAB, 0);
        mViewPager.setCurrentItem(sharedPreferences.getInt(TAB_DEFAULT, 0));
        if(sharedPreferences.getInt(TAB_DEFAULT, 0)==0)
            resetTab1();
        else
            resetTab2();
        Log.e("tab default", sharedPreferences.getInt(TAB_DEFAULT, 0)+"");

        mViewPager.addOnPageChangeListener(onPageChangeListener);
    }


    private void connectView(){
        mViewPager = (ViewPager)findViewById(R.id.viewpager_home_where);
        tvTab1 = (TextView)findViewById(R.id.textViewTab1);
        tvTab2 = (TextView)findViewById(R.id.textViewTab2);
        mToolbar = (Toolbar)findViewById(R.id.toolbar_home);
    }
    private void addPage() {
        mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),mContext,mViewPager,mTabLayout);
        mViewPager.setAdapter(mPagerAdapter);

        mPagerAdapter.addFrag(PlacesFragment.newInstance(), getString(R.string.what));
        mPagerAdapter.addFrag(FoodFragment.newInstance(), getString(R.string.where));
        mPagerAdapter.notifyDataSetChanged();

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(mViewPager.getCurrentItem()== TAB_PLACES)
                    resetTab1();
                else  if(mViewPager.getCurrentItem()== TAB_FOOD)
                    resetTab2();

            }
            @Override
            public void onPageSelected(int position) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    // event click tab1 (textview) - onClick via xml
    public void setTextViewTab1(View view){
        resetTab1();
        mViewPager.setCurrentItem(TAB_PLACES);
    }
    public void setTextViewTab2(View view){
        resetTab2();
        mViewPager.setCurrentItem(TAB_FOOD);
    }
    private void resetTab1(){
        tvTab1.setSelected(true);
        tvTab2.setSelected(false);
    }
    private void resetTab2(){
        tvTab1.setSelected(false);
        tvTab2.setSelected(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent i = new Intent(this, CategoriesActivity.class);
                startActivity(i);
                break;
            case R.id.mn_plus:
                eventClick();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void eventClick() {
        View view = getLayoutInflater().inflate(R.layout.buttom_sheet_layout, null);
        View l1 = view.findViewById(R.id.ln1);
        View l2 = view.findViewById(R.id.ln2);
        View l3 = view.findViewById(R.id.ln3);
        View l4 = view.findViewById(R.id.ln4);
        View l5 = view.findViewById(R.id.ln5);

        final Dialog mBottomSheetDialog = new Dialog(this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();

        l5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(USER_ID>0) {
                    Intent i = new Intent(getBaseContext(), AddLocationActivity.class);
                    startActivity(i);
                }
                else{
                    startActivity((new Intent(getBaseContext(),LoginActivity.class)));
                }
                mBottomSheetDialog.dismiss();
            }
        });

    }
    @Override
    public void onBackPressed() {
        PlacesFragment.setCurrentPage();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("resume home","rs");
    }
}

