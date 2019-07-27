package com.thuhang.foody1703311;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TabHost;

public class MainActivity extends AppCompatActivity {

    public static TabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectView();
        loadTabs(savedInstanceState);
        setStateTabHost(View.VISIBLE);

      /*  Log.e("user_id",getMyPreferences(getBaseContext(),USER,USER_ID,1)+"");*/
    }
    private void connectView() {
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
    }

    private void loadTabs(Bundle savedInstanceState) {
        LocalActivityManager localActivityManager = new LocalActivityManager(this, false);
        localActivityManager.dispatchCreate(savedInstanceState);
        mTabHost.setup(localActivityManager);
        addTab(getString(R.string.home), R.drawable.bgr_tab_1_main, HomeActivity.class);
        addTab(getString(R.string.collection),R.drawable.bgr_tab_2_main,CollectionActivity.class);
        addTab(getString(R.string.account),R.drawable.bgr_tab_5_main,UserActivity.class);
      /*
        addTab(getString(R.string.search),R.drawable.bgr_tab_3_main,SearchActivity.class);
        addTab(getString(R.string.notifications),R.drawable.bgr_tab_4_main,NotifActivity.class);
       ;*/
        mTabHost.getTabWidget().setAlpha(0.97f);
        for (int i = 0; i < mTabHost.getTabWidget().getTabCount(); i++) {
            mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.white));
            mTabHost.getTabWidget().getChildAt(i).setAlpha(1);
        }
    }

    private void addTab(String labelId, int icon, Class<?> c) {
        Resources res = getResources();
        TabHost.TabSpec spec = mTabHost.newTabSpec(labelId);
        spec.setIndicator("", res.getDrawable(icon));
        Intent intent = new Intent(this, c);
        spec.setContent(intent);
        mTabHost.addTab(spec);
    }

    public static void setStateTabHost(int view) {
        mTabHost.getTabWidget().setVisibility(view);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setStateTabHost(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if(UserActivity.USER_ID==1)
//            mTabHost.setCurrentTab(2);
    }
}