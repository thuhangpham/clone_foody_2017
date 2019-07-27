package com.thuhang.foody1703311.fragments;

import android.Manifest;
import android.app.Dialog;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.thuhang.foody1703311.ChooseCityActivity;
import com.thuhang.foody1703311.MainActivity;
import com.thuhang.foody1703311.R;
import com.thuhang.foody1703311.adapters.CategoriesListViewAdapter;
import com.thuhang.foody1703311.adapters.ExpanableListAdapter;
import com.thuhang.foody1703311.adapters.MyCustomPagerAdapter;
import com.thuhang.foody1703311.adapters.RestaurantFoodAdapter;
import com.thuhang.foody1703311.adapters.TypeCateAdapter;
import com.thuhang.foody1703311.database.CityHandler;
import com.thuhang.foody1703311.database.DistrictHandler;
import com.thuhang.foody1703311.database.StreetHandler;
import com.thuhang.foody1703311.googleAPI.GoogleAPI;
import com.thuhang.foody1703311.interfaces.IFoodView;
import com.thuhang.foody1703311.interfaces.IFragmentLifecycle;
import com.thuhang.foody1703311.models.Categories;
import com.thuhang.foody1703311.models.City;
import com.thuhang.foody1703311.models.District;
import com.thuhang.foody1703311.models.RestaurantFood;
import com.thuhang.foody1703311.models.Street;
import com.thuhang.foody1703311.models.TypeCate;
import com.thuhang.foody1703311.presenters.PresenterCategory;
import com.thuhang.foody1703311.presenters.PresenterDistrict;
import com.thuhang.foody1703311.presenters.PresenterFoodRestaurant;
import com.thuhang.foody1703311.presenters.PresenterTypeCate;
import com.thuhang.foody1703311.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static com.thuhang.foody1703311.ChooseCityActivity.CITY_SETTING;
import static com.thuhang.foody1703311.ChooseCityActivity.CITY_STORED;
import static com.thuhang.foody1703311.R.string.city;
import static com.thuhang.foody1703311.fragments.PlacesFragment.LST_ICON;
import static com.thuhang.foody1703311.googleAPI.GoogleAPI.getAddressFromLatLng;
import static com.thuhang.foody1703311.models.CategoryType.CATE_TYPE_ID;
import static com.thuhang.foody1703311.util.Constant.DATABASE_NAME;
import static com.thuhang.foody1703311.util.Constant.TAB_FOOD_CATE;
import static com.thuhang.foody1703311.util.Constant.TAB_FOOD_CITY;
import static com.thuhang.foody1703311.util.Constant.TAB_FOOD_CONTENT;
import static com.thuhang.foody1703311.util.Constant.TAB_FOOD_TYPE_CATE;
import static com.thuhang.foody1703311.util.Constant.VERSION;
import static com.thuhang.foody1703311.util.Utils.fortmartStr;

/**
 * Created by ThuHang on 3/31/2017.
 */

public class FoodFragment extends Fragment implements View.OnClickListener, IFoodView,IFragmentLifecycle
        /*,IFoodLoadMore*/  ,LocationListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    private static final long UPDATE_INTERVAL = 5000;
    private static final long FASTEST_INTERVAL = 5000;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;

    private TabHost mTabHost;
    private TabWidget mTabWidget;

    private Button btnCancel1, btnCancel2, btnCancel3;
    private TextView tvTab1, tvTab2, tvTab3;
    private View viewChangeCity, viewError;
    private TextView tvChangeCity, tvErr;
    public Dialog m_Dialog;
    private NestedScrollView scrollView;
    private ListView listView1;//,  mListViewTab2;
    private ExpandableListView exLvTab3;

    private RecyclerView /*mRecyclerViewTab3*/
            mRecyclerViewRestaurant,
            mRecyclerViewTab2,
            mRecyclerViewMenuHome;
    private RelativeLayout btnChangeCity;

    private ViewPager mViewpagerSlide;

    private List<RestaurantFood> mRestaurantList;
    private List<Object> mTypeCateList,
            mAddressList;
    private List<Categories> mCategoryList;

    private Parcelable recyclerViewState;

    private int categoryId = 1, typeCateId = -1, districtId = -1, cityId = 1, streetid=-1;;

    private RestaurantFoodAdapter mRestaurantAdapter;
    private TypeCateAdapter mTypeCateAdapter;
    private CategoriesListViewAdapter mCateAdapter;


    private PresenterFoodRestaurant mPresenterRestaurantFood;
    private PresenterDistrict mPresenterDistrict;
    private PresenterTypeCate mPresenterTypeCate;
    private PresenterCategory mPresenterCategory;
    private ExpanableListAdapter expanableListAdapter;

    private int city_id=1;

    private Timer timer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_food, container, false);
        connectView(v);
        loadTabs(savedInstanceState);

        SharedPreferences settings = getActivity().getSharedPreferences(CITY_SETTING, 0);
        cityId = settings.getInt(CITY_STORED, 1);
        Log.e("cityid", cityId + "");
        city_id = cityId;

        mRestaurantList = new ArrayList<>();
        mCategoryList = new ArrayList<>();
        mAddressList = new ArrayList<>();
        mTypeCateList = new ArrayList<>();
        configLoadPre();

        mPresenterCategory = new PresenterCategory(this, getActivity());
        mPresenterDistrict = new PresenterDistrict(this, getActivity());
        mPresenterTypeCate = new PresenterTypeCate(this, getActivity());
        mPresenterRestaurantFood = new PresenterFoodRestaurant(this, getActivity());
        mPresenterCategory.getALlCate();
        mPresenterDistrict.getDistrictByCityId(city_id);
        mPresenterTypeCate.getAllTypeCateByType(CATE_TYPE_ID);
        mPresenterRestaurantFood.getResFoodAPI(city_id, categoryId, districtId,streetid, typeCateId, CATE_TYPE_ID);

        recyclerViewState = new LinearLayoutManager.SavedState();
        btnChangeCity.setOnClickListener(this);
        viewChangeCity.setOnClickListener(this);
        setUpLocationClientIfNeeded();
        buildLocationRequest();
        return v;
    }

    private void configLoadPre() {
        m_Dialog = new Dialog(getContext());
        m_Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        m_Dialog.setContentView(R.layout.layout_load);
        m_Dialog.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        m_Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        m_Dialog.setCancelable(true);
        m_Dialog.setCanceledOnTouchOutside(true);
    }


    private void restore() {
        CityHandler cityHandler = new CityHandler(getActivity(), DATABASE_NAME, null, VERSION);
        City city = cityHandler.getCityById(city_id);
        tvTab3.setText(city.getName());
        tvChangeCity.setText(city.getName());
    }

    private void loadSlide() {
        final int[] slideList = {R.drawable.slide1, R.drawable.slide2};
        MyCustomPagerAdapter myCustomPagerAdapter = new MyCustomPagerAdapter(getContext(), slideList);
        mViewpagerSlide.setAdapter(myCustomPagerAdapter);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                mViewpagerSlide.post(new Runnable(){

                    @Override
                    public void run() {
                        mViewpagerSlide.setCurrentItem((mViewpagerSlide.getCurrentItem()+1)%slideList.length);
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 1000, 5000);
    }

    private void connectView(View v) {
        mTabHost = (TabHost) v.findViewById(android.R.id.tabhost);
        btnCancel1 = (Button) v.findViewById(R.id.btnCancelAction1);
        btnCancel2 = (Button) v.findViewById(R.id.btnCancelAction2);
        btnCancel3 = (Button) v.findViewById(R.id.btnCancelAction3);
        btnChangeCity = (RelativeLayout) v.findViewById(R.id.btnChangeCity);
        listView1 = (ListView) v.findViewById(R.id.lstView1);
        mRecyclerViewTab2 = (RecyclerView) v.findViewById(R.id.recycleViewTab2);
        mRecyclerViewRestaurant = (RecyclerView) v.findViewById(R.id.recyclerViewRestaurant);
        viewChangeCity = v.findViewById(R.id.viewChangeCity);
        viewError = v.findViewById(R.id.viewError);
        tvChangeCity = (TextView) v.findViewById(R.id.textViewChangeCityName);
        tvErr = (TextView) v.findViewById(R.id.tvError);
        mViewpagerSlide = (ViewPager)v.findViewById(R.id.viewpagerSlide);
        scrollView = (NestedScrollView)v.findViewById(R.id.scrollfood);
        exLvTab3 = (ExpandableListView)v.findViewById(R.id.lvTab3);
    }

    /*Configure TabHost*/
    private void loadTabs(Bundle savedInstanceState) {                                                 //Cấu hình tab
        LocalActivityManager localActivityManager = new LocalActivityManager(getActivity(), false);
        localActivityManager.dispatchCreate(savedInstanceState);
        mTabHost.setup(localActivityManager);                               // gọi lệnh setup tabhost
        addTab(TAB_FOOD_CONTENT, null, R.id.places_content);                // add content main
        addTab(TAB_FOOD_CATE, getString(R.string.latest), R.id.header_cate);                // add header "moi nhat"
        addTab(TAB_FOOD_TYPE_CATE, getString(R.string.categories), R.id.header_type_cate);      // add header "Danh muc"
        addTab(TAB_FOOD_CITY, getString(city), R.id.header_city);                  // add header "TP.HCM"
        mTabHost.setCurrentTab(0);                                          // Thiết lập tab mặc định được chọn ban đầu là tab 0
        mTabWidget = mTabHost.getTabWidget();
        mTabWidget.getChildAt(0).setVisibility(View.GONE);                  // GONE tab 0
        tabEvent();                                                         // su kien click tab
        btnCancelEvent();                                                   // Su kien click button "Huy"
        /*get view of 3 tab*/
        tvTab1 = (TextView) mTabWidget.getChildTabViewAt(1);
        tvTab2 = (TextView) mTabWidget.getChildTabViewAt(2);
        tvTab3 = (TextView) mTabWidget.getChildTabViewAt(3);
        // default color is red
        tvTab1.setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    private void addTab(String tag, String title, int layoutId) {
        TabHost.TabSpec tabSpec = mTabHost.newTabSpec(tag);
        tabSpec.setContent(layoutId);
        tabSpec.setIndicator(createTabView(getActivity(), title));
        mTabHost.addTab(tabSpec);
    }

    private static View createTabView                                        // Custom tab's background
    (final Context context, final String text) {
        View view = LayoutInflater.from(context).inflate(R.layout.bgr_header, null);
        Button btn = (Button) view.findViewById(R.id.btnTabHeader);
        btn.setText(text);
        return view;
    }

    private void hideTab() {
        mTabHost.setCurrentTab(0);
        MainActivity.setStateTabHost(View.VISIBLE);
    }

    private void tabSelected(int tabId) {
        if (tabId == mTabHost.getCurrentTab())
            hideTab();
        else {
            mTabHost.setCurrentTab(tabId);
            MainActivity.setStateTabHost(View.INVISIBLE);
        }
    }

    private void tabEvent() {
        mTabWidget.getChildAt(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabSelected(1);
            }
        });
        mTabWidget.getChildAt(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabSelected(2);
            }
        });
        mTabWidget.getChildAt(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabSelected(3);
            }
        });
    }

    private void btnCancelEvent() {
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabSelected(mTabHost.getCurrentTab());
            }
        };
        btnCancel1.setOnClickListener(clickListener);
        btnCancel2.setOnClickListener(clickListener);
        btnCancel3.setOnClickListener(clickListener);
    }

    /*End Configure TabHost*/
    /*Configure tab 1*/
    private void configTab1(List<Categories> categories) {
        mCategoryList = new ArrayList<>();
        this.mCategoryList = categories;
        mCateAdapter = new CategoriesListViewAdapter(getActivity(), LST_ICON, mCategoryList);
        listView1.setAdapter(mCateAdapter);
        mCateAdapter.notifyDataSetChanged();
        mCateAdapter.setOnClickListener(new CategoriesListViewAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View v, int position) {
                setItemTab1Click(v, position);
            }
        });
    }

    /*Config tab 2 "Danh muc"*/
    private void configTab2(List<Object> typeCateList) {
        mTypeCateList = typeCateList;
        mTypeCateAdapter = new TypeCateAdapter(mTypeCateList, getActivity());
        mRecyclerViewTab2.setHasFixedSize(true);
        mRecyclerViewTab2.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerViewTab2.setAdapter(mTypeCateAdapter);
        mTypeCateAdapter.setOnItemClickListener(new Utils.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                setItemTab2Click(position, itemView);
            }
        });
    }

    /*Config tab 3 "TP.HCM"*/
    private void configTab3(final List<Object> addressList) {
        List<String> childList = new ArrayList<>();
        HashMap<String, List<String>> map = new HashMap<>();
        mAddressList = new ArrayList<>();
        this.mAddressList = addressList;
        for(int i=0;i<addressList.size();i++){
            childList=new ArrayList<>();
            District district = (District)addressList.get(i);
            for(Street s:district.getStreets())
                childList.add(s.getName());
            map.put(district.getDistrictName(),childList);
        }
        // item child click
        exLvTab3.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                District district = (District) addressList.get(groupPosition);
                streetid = district.getStreets().get(childPosition).getId();
                districtId= Integer.parseInt(district.getDistrictId());
                Log.e("strid",streetid+"");
                mPresenterRestaurantFood.getResFoodAPI(city_id, categoryId, districtId,streetid, typeCateId, CATE_TYPE_ID);
                hideTab();
                return false;
            }
        });
        // item group click
        exLvTab3.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                setItemTab3Click(v, groupPosition);
                return true; // This way the expander cannot be collapsed
            }
        });
        exLvTab3.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int len = expanableListAdapter.getGroupCount();
                for(int i=0; i<len; i++) {
                    if(i != groupPosition) {
                        exLvTab3.collapseGroup(i);
                    }
                }
            }
        });
        expanableListAdapter= new ExpanableListAdapter(getActivity(), addressList, map);
        exLvTab3.setAdapter(expanableListAdapter);
    }

    /*Config Places main content*/
    private void configMainContent(List<RestaurantFood> restaurantList) {
        viewError.setVisibility(View.GONE);
        mRecyclerViewRestaurant.setVisibility(View.VISIBLE);
        GridLayoutManager grigLayout = new GridLayoutManager(getActivity(), 2);
        mRecyclerViewRestaurant.setLayoutManager(grigLayout);
        this.mRestaurantList = restaurantList;
        mRecyclerViewRestaurant.setHasFixedSize(false);
        mRecyclerViewRestaurant.setNestedScrollingEnabled(false);
        mRestaurantAdapter = new RestaurantFoodAdapter(getActivity(), mRestaurantList);
        mRecyclerViewRestaurant.setAdapter(mRestaurantAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.viewChangeCity:
                setViewChangeCity();
                break;
            case R.id.btnChangeCity:
                Intent intent = new Intent(getActivity(), ChooseCityActivity.class);
                startActivity(intent);
                break;
        }
    }
    // doi thanh pho
    private void setViewChangeCity() {
        tvChangeCity.setTextColor(getResources().getColor(R.color.colorPrimary));
        setTextTab(tvTab3, tvChangeCity.getText() + "", getResources().getColor(R.color.text_color));
        hideTab();
        districtId = -1;
        streetid=-1;
        city_id = cityId;
        expanableListAdapter.setPos(-1);
        mPresenterRestaurantFood.getResFoodAPI(city_id, categoryId, districtId,streetid, typeCateId, CATE_TYPE_ID);
    }
    // su kien click tab list item 1
    private void setItemTab1Click(View v, int position) {
        if (position != mCateAdapter.getPosCurrentTab1()) {
            TextView tv;
            tv = (TextView) v.findViewById(R.id.textViewNameCate);
            tvTab1.setText(tv.getText());  // set text for tab 1
            mCateAdapter.setPosCurrentTab1(position);
            Categories categories = mCategoryList.get(position);
            if (categories != null)
                categoryId = Integer.parseInt(categories.getId());
            if(position==1) {
                GoogleAPI googleAPI = new GoogleAPI(getActivity());
                if (!googleAPI.isPlayServicesAvailable()) {
                    Toast.makeText(getActivity(), "Thiết bị không hỗ trợ Google play service.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!googleAPI.isGpsOn()) {
                    // notify user
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setTitle("Khuyến cáo");
                    dialog.setMessage(this.getResources().getString(R.string.gps_network_not_enabled));
                    dialog.setPositiveButton(this.getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            // chuyen sang man hinh cai dat jps
                            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(myIntent);
                            //get gps
                        }
                    });
                    dialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            paramDialogInterface.dismiss();
                        }
                    });
                    dialog.show();
                    hideTab();
                    return;
                } else { // on
                    if(mLastLocation==null) {
                        setUpLocationClientIfNeeded();
                        buildLocationRequest();
                        onConnected(null);
                    }
                        /* lat = mLastLocation.getLatitude();
                        lng = mLastLocation.getLongitude();*/
                    List<Address> list=getAddressFromLatLng(getActivity(), mLastLocation.getLatitude(), mLastLocation.getLongitude());
                    Log.e("address",list.get(0).toString()+",0 : "+ list.get(0).getAddressLine(0)+",1: "+list.get(0).getAddressLine(1)+",2: "+list.get(0).getAddressLine(2)+",3: "+list.get(0).getAddressLine(3)+",4: "+list.get(0).getAddressLine(4)+",6: "+list.get(0).getAddressLine(5)+",6: "+list.get(0).getAddressLine(6));
                    DistrictHandler districtHandler = new DistrictHandler(getActivity(),DATABASE_NAME,null,VERSION);
                    StreetHandler streetHandler = new StreetHandler(getActivity(),DATABASE_NAME,null,VERSION);
                    CityHandler cityHandler = new CityHandler(getActivity(),DATABASE_NAME,null,VERSION);
                    if(districtId<0) {
                        districtId = districtHandler.getIdByName(list.get(0).getAddressLine(2));
                        streetid = streetHandler.getIdByName(list.get(0).getThoroughfare(), districtId);
                        city_id = cityHandler.getIdByName(list.get(0).getAddressLine(3));
                        categoryId=1;
                    }
                    Log.e("qq",streetid+""+districtId+districtHandler.getIdByName(list.get(0).getAddressLine(2)));
                        /*  updateUi();*/
                    mPresenterRestaurantFood.getResFoodAPI(city_id, categoryId, districtId, streetid, typeCateId, CATE_TYPE_ID);
                }
            }
            else
                mPresenterRestaurantFood.getResFoodAPI(city_id, categoryId, districtId, streetid, typeCateId, CATE_TYPE_ID);
        }
        hideTab();
    }
    // su kien click tab list item 2
    private void setItemTab2Click(int position, View itemView) {
        int posCurrentTab2 = mTypeCateAdapter.getPosCurrentTab2();
        if (position != posCurrentTab2) {
            mTypeCateAdapter.setPosCurrentTab2(position);
            TextView tvName;
            if (position == 0)
                tvName = (TextView) itemView.findViewById(R.id.textViewItemTypeCate);
            else tvName = (TextView) itemView.findViewById(R.id.textViewNameTypeCate);
            String txt = fortmartStr(String.valueOf(tvName.getText()),13);
            if (position == 0)
                typeCateId = -1;
            else {
                TypeCate typeCate = (TypeCate) mTypeCateList.get(position);
                if (typeCate != null)
                    typeCateId = typeCate.getId();
            }
            setTextTab(tvTab2, txt, getResources().getColor(R.color.colorPrimary));
            if (position == 0)
                setTextTab(tvTab2, txt, getResources().getColor(R.color.text_color));
            mPresenterRestaurantFood.getResFoodAPI(city_id, categoryId, districtId,streetid, typeCateId, CATE_TYPE_ID);
        }
        hideTab();
    }
    // su kien click item tab 3, thanh pho/quan huyen
    private void setItemTab3Click(View itemView, int position) {
        int posCurrentTab3 = expanableListAdapter.getPos();
        if (position != posCurrentTab3) {
            expanableListAdapter.setPos(position);
            TextView tvName;
            tvName = (TextView) itemView.findViewById(R.id.textViewDistrictName);
            //tvName.setTextColor(getResources().getColor(R.color.colorPrimary));     // doi mau text
            if (posCurrentTab3 == -1)                                                // doi mau text truoc do
                tvChangeCity.setTextColor(getResources().getColor(R.color.text_color));
            String txt = fortmartStr(String.valueOf(tvName.getText()),12);
            District district = (District) mAddressList.get(position);
            districtId=-1;
            if (district != null) {
                districtId = Integer.parseInt(district.getDistrictId());
                streetid=-1;
            }
            if(mCateAdapter.getPosCurrentTab1()==1) {
                categoryId=1;
                mCateAdapter.setPosCurrentTab1(0);
                setTextTab(tvTab1, "Mới nhất", getResources().getColor(R.color.colorPrimary));
            }
            setTextTab(tvTab3, txt, getResources().getColor(R.color.colorPrimary));
            mPresenterRestaurantFood.getResFoodAPI(city_id, categoryId, districtId,streetid, typeCateId, CATE_TYPE_ID);
        }
        hideTab();
    }

    private void setTextTab(TextView tab, String text, int color) {
        tab.setText(text);
        tab.setTextColor(color);
    }

    @Override
    public void configListCate(List<Categories> categories) {
        configTab1(categories);
    }

    @Override
    public void configListTypeCate(List<Object> types) {
        configTab2(types);
    }

    @Override
    public void configListDistrict(List<Object> districts) {
       configTab3(districts);
    }

    @Override
    public void configRestaurant(List<RestaurantFood> items) {
        scrollView.scrollTo(0,-1);
        configMainContent(items);
        hideLoading("");
    }

    @Override
    public void error(String err) {
        hideLoading("");
        tvErr.setText(err);
        viewError.setVisibility(View.VISIBLE);
        mRecyclerViewRestaurant.setVisibility(View.GONE);
    }

    public static FoodFragment newInstance() {
        return new FoodFragment();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    // goi OnPause truoc
    @Override
    public void onPauseFragment() {
        Log.e("food", "OnPause");
    }

    @Override
    public void onResumeFragment() {
        if (mTabHost.getCurrentTab() != 0)
            MainActivity.setStateTabHost(View.GONE);
        else
            MainActivity.setStateTabHost(View.VISIBLE);
        Log.e("food", "OnResume" + " " + mTabHost.getCurrentTab());
    }
    public void showLoading() {
        m_Dialog.show();
    }

    @Override
    public void hideLoading(String msg) {
        Log.e("", "hidef");
        if (m_Dialog.isShowing()) {
            m_Dialog.dismiss();
            Log.e("", "hide2");
        }
    }
    @Override
    public void onLocationChanged(Location location) {
        Log.d("onLocationChanged", String.format(Locale.getDefault(), "onLocationChanged : %f, %f",
                location.getLatitude(), location.getLongitude()));
        mLastLocation = location;
        //updateUi();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (lastLocation != null) {
            mLastLocation = lastLocation;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onDestroy() {
        timer.cancel();
        if (mGoogleApiClient != null
                && mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
            mGoogleApiClient.disconnect();
            mGoogleApiClient = null;
        }
        Log.d("destroy", "onDestroy LocationService");
        super.onDestroy();
    }
    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }
    @Override
    public void onStart() {
        super.onStart();
        restore();
        loadSlide();
        Log.e("","onStart Places");
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onStop() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    private void setUpLocationClientIfNeeded() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }
    private void buildLocationRequest() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
    }

}