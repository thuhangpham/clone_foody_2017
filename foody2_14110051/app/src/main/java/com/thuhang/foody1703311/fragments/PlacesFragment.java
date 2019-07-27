package com.thuhang.foody1703311.fragments;

import android.Manifest;
import android.app.Dialog;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.thuhang.foody1703311.adapters.RestaurantAdapter;
import com.thuhang.foody1703311.adapters.TypeCateAdapter;
import com.thuhang.foody1703311.database.CityHandler;
import com.thuhang.foody1703311.database.DistrictHandler;
import com.thuhang.foody1703311.database.StreetHandler;
import com.thuhang.foody1703311.googleAPI.GoogleAPI;
import com.thuhang.foody1703311.interfaces.IFragmentLifecycle;
import com.thuhang.foody1703311.interfaces.IPlacesView;
import com.thuhang.foody1703311.models.Categories;
import com.thuhang.foody1703311.models.City;
import com.thuhang.foody1703311.models.District;
import com.thuhang.foody1703311.models.Restaurant;
import com.thuhang.foody1703311.models.Street;
import com.thuhang.foody1703311.models.TypeCate;
import com.thuhang.foody1703311.presenters.PresenterCategory;
import com.thuhang.foody1703311.presenters.PresenterDistrict;
import com.thuhang.foody1703311.presenters.PresenterRestaurant;
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
import static com.thuhang.foody1703311.googleAPI.GoogleAPI.getAddressFromLatLng;
import static com.thuhang.foody1703311.models.CategoryType.CATE_TYPE_ID;
import static com.thuhang.foody1703311.util.Constant.DATABASE_NAME;
import static com.thuhang.foody1703311.util.Constant.TAB_PLACES_CATE;
import static com.thuhang.foody1703311.util.Constant.TAB_PLACES_CITY;
import static com.thuhang.foody1703311.util.Constant.TAB_PLACES_CONTENT;
import static com.thuhang.foody1703311.util.Constant.TAB_PLACES_TYPE_CATE;
import static com.thuhang.foody1703311.util.Constant.VERSION;
import static com.thuhang.foody1703311.util.Utils.fortmartStr;
import static com.thuhang.foody1703311.util.Utils.getMyPreferences;

/**
 * Created by ThuHang on 3/31/2017.
 */

public class PlacesFragment extends Fragment implements View.OnClickListener, IPlacesView,
        IFragmentLifecycle/*, IPlacesLoadmore*/
        ,LocationListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    private static final long UPDATE_INTERVAL = 5000;
    private static final long FASTEST_INTERVAL = 5000;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;

    public static TabHost mTabHost;
    private TabWidget mTabWidget;
    private Button btnCancel1, btnCancel2, btnCancel3;
    private RelativeLayout btnChangeCity;
    private TextView tvTab1, tvTab2, tvTab3;
    private View viewChangeCity, viewError;
    public Dialog m_Dialog;
    private TextView tvChangeCity;
    private ExpanableListAdapter expanableListAdapter;

    public static int[] LST_ICON_ACT = {R.drawable.home_ic_filter_latest_act, R.drawable.home_ic_filter_most_near_act,
            R.drawable.home_ic_filter_top_of_week_act, R.drawable.home_ic_filter_tourist_act,
            R.drawable.home_ic_filter_ecard_act, R.drawable.home_ic_filter_most_reservation_act,
            R.drawable.home_ic_filter_bankcard_act, R.drawable.home_ic_delivery_act},

    LST_ICON = {R.drawable.home_ic_filter_latest, R.drawable.home_ic_filter_most_near,
            R.drawable.home_ic_filter_top_of_week, R.drawable.home_ic_filter_tourist,
            R.drawable.home_ic_filter_ecard, R.drawable.home_ic_filter_most_reservation,
            R.drawable.home_ic_filter_bankcard, R.drawable.home_ic_delivery};

    private ListView listView1;//,  mListViewTab2;
    private TextView tvErr;
    private ExpandableListView exLvTab3;

    private RecyclerView mRecyclerViewTab3,
            mRecyclerViewRestaurant,
            mRecyclerViewTab2,
            mRecyclerViewMenuHome;
    public static List<Object>
            mTypeCateList;
    public  static List<Object> mAddressList;
    private List<Restaurant> mRestaurantList;
    private List<Categories> mCategoryList;

    private Parcelable recyclerViewState;

    private int categoryId = 1, typeId = -1, districtId = -1, cityId = 1, streetid=-1, city_id=-1;

    private ViewPager mViewpagerSlide;

    private RestaurantAdapter mRestaurantAdapter;
    private TypeCateAdapter mTypeCateAdapter;
    private CategoriesListViewAdapter mCateAdapter;

    private PresenterRestaurant mPresenterRestaurant;
    private PresenterDistrict mPresenterDistrict;
    private PresenterTypeCate mPresenterTypeCate;
    private PresenterCategory mPresenterCategory;

    private LinearLayoutManager linearLayoutManager;
    private NestedScrollView scrollView;

    private Timer timer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_places, container, false);
        connectView(v);
        loadTabs(savedInstanceState);
        cityId = getMyPreferences(getActivity(),CITY_SETTING,CITY_STORED,1);
        city_id=cityId;
        Log.e("Places city_id", city_id + "");

        mRestaurantList = new ArrayList<>();
        mCategoryList = new ArrayList<>();
        mAddressList = new ArrayList<>();
        mTypeCateList = new ArrayList<>();
        configLoadPre();

        mPresenterCategory = new PresenterCategory(this, getActivity());
        mPresenterDistrict = new PresenterDistrict(this, getActivity());
        mPresenterTypeCate = new PresenterTypeCate(this, getActivity());
        mPresenterRestaurant = new PresenterRestaurant(this, getActivity());

        mPresenterCategory.getALlCate();
        mPresenterDistrict.getDistrictByCityId(city_id);
        mPresenterTypeCate.getAllTypeCateByType(CATE_TYPE_ID);
        mPresenterRestaurant.getRestaurantAPI(city_id, categoryId, districtId,streetid, typeId, CATE_TYPE_ID);

        recyclerViewState = new LinearLayoutManager.SavedState();
        viewChangeCity.setOnClickListener(this);
        btnChangeCity.setOnClickListener(this);
        setUpLocationClientIfNeeded();
        buildLocationRequest();
        return v;
    }
    private void configLoadPre(){
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
        final int[] slideList = {R.drawable.slide4, R.drawable.slide5};
        MyCustomPagerAdapter myCustomPagerAdapter = new MyCustomPagerAdapter(getContext(), slideList);
        mViewpagerSlide.setAdapter(myCustomPagerAdapter);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                mViewpagerSlide.post(new Runnable() {

                    @Override
                    public void run() {
                        mViewpagerSlide.setCurrentItem((mViewpagerSlide.getCurrentItem() + 1) % slideList.length);
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
        //mRecyclerViewMenuHome = (RecyclerView) v.findViewById(R.id.recycleViewMenuHome);
        mRecyclerViewTab2 = (RecyclerView) v.findViewById(R.id.recycleViewTab2);
        //mRecyclerViewTab3 = (RecyclerView) v.findViewById(R.id.recycleViewTab3);
        mRecyclerViewRestaurant = (RecyclerView) v.findViewById(R.id.recyclerViewRestaurant);
        viewChangeCity = v.findViewById(R.id.viewChangeCity);
        viewError = v.findViewById(R.id.viewError);
        tvChangeCity = (TextView) v.findViewById(R.id.textViewChangeCityName);
        tvErr = (TextView)v.findViewById(R.id.tvError);
        mViewpagerSlide = (ViewPager) v.findViewById(R.id.viewpagerSlide);
        scrollView = (NestedScrollView)v.findViewById(R.id.scroll_pl);
        exLvTab3 = (ExpandableListView)v.findViewById(R.id.lvTab3);
    }
    /*Configure TabHost*/
    public void loadTabs(Bundle savedInstanceState) {                                                 //Cấu hình tab
        LocalActivityManager localActivityManager = new LocalActivityManager(getActivity(), false);
        localActivityManager.dispatchCreate(savedInstanceState);
        mTabHost.setup(localActivityManager);                               // gọi lệnh setup tabhost
        addTab(TAB_PLACES_CONTENT, null, R.id.places_content);                // add content main
        addTab(TAB_PLACES_CATE, getString(R.string.latest), R.id.header_cate);                // add header "moi nhat"
        addTab(TAB_PLACES_TYPE_CATE, getString(R.string.categories), R.id.header_type_cate);      // add header "Danh muc"
        addTab(TAB_PLACES_CITY, getString(city), R.id.header_city);                  // add header "TP.HCM"
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
        Log.d("mCategoryList", mCategoryList.size() + "");
        this.mCategoryList = categories;
        mCateAdapter = new CategoriesListViewAdapter(getActivity(), LST_ICON, mCategoryList);
        listView1.setAdapter(mCateAdapter);
        mCateAdapter.setOnClickListener(new CategoriesListViewAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View v, int position) {
                setItemTab1Click(v, position);
            }
        });
        listView1.setAdapter(mCateAdapter);
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
        List<Street> streets;
        HashMap<String, List<String>> map = new HashMap<>();
        mAddressList = new ArrayList<>();
        this.mAddressList = addressList;
        for (int i = 0; i < addressList.size(); i++) {
            childList = new ArrayList<>();
            District district = (District) addressList.get(i);
            for (Street s : district.getStreets()) {
                childList.add(s.getName());
            }
            map.put(district.getDistrictName(), childList);
        }
        exLvTab3.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                District district = (District) addressList.get(groupPosition);
                streetid = district.getStreets().get(childPosition).getId();
                districtId= Integer.parseInt(district.getDistrictId());
                setTextTab(tvTab3, fortmartStr(String.valueOf( district.getStreets().get(childPosition).getName()),12), getResources().getColor(R.color.colorPrimary));
                Log.e("strid", streetid + "");
                mPresenterRestaurant.getRestaurantAPI(city_id, categoryId, districtId,streetid, typeId, CATE_TYPE_ID);
                hideTab();
                return false;
            }
        });
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
                for (int i = 0; i < len; i++) {
                    if (i != groupPosition) {
                        exLvTab3.collapseGroup(i);
                    }
                }
            }
        });
        expanableListAdapter = new ExpanableListAdapter(getActivity(), addressList, map);
        exLvTab3.setAdapter(expanableListAdapter);
    }

    /*Config Places main content*/
    private void configMainContent(final List<Restaurant> restaurantList) {
        viewError.setVisibility(View.GONE);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewRestaurant.setVisibility(View.VISIBLE);
        mRecyclerViewRestaurant.setLayoutManager(linearLayoutManager);
        this.mRestaurantList = restaurantList;
        mRestaurantAdapter = new RestaurantAdapter(mRestaurantList, getActivity());
        mRecyclerViewRestaurant.setHasFixedSize(true);
        mRecyclerViewRestaurant.setNestedScrollingEnabled(false);
        mRecyclerViewRestaurant.setAdapter(mRestaurantAdapter);
//        scrollListener = new EndlessOnScrollListener(linearLayoutManager, this);
//        mRecyclerViewRestaurant.addOnScrollListener(scrollListener);
    }
    public static PlacesFragment newInstance() {
        return new PlacesFragment();
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
    private void setViewChangeCity() {
        tvChangeCity.setTextColor(getResources().getColor(R.color.colorPrimary));
        setTextTab(tvTab3, tvChangeCity.getText() + "", getResources().getColor(R.color.text_color));
        hideTab();
        districtId = -1;
        streetid=-1;
        city_id=cityId;
        expanableListAdapter.setPos(-1);
        mPresenterRestaurant.getRestaurantAPI(city_id, categoryId, districtId,streetid, typeId, CATE_TYPE_ID);
    }
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
                } else {
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
                        /*  updateUi();*/
                        mPresenterRestaurant.getRestaurantAPI(city_id, categoryId, districtId, streetid, typeId, CATE_TYPE_ID);
                }
            }
            else
                mPresenterRestaurant.getRestaurantAPI(city_id, categoryId, districtId, streetid, typeId, CATE_TYPE_ID);
        }
        hideTab();
    }

    private void setItemTab2Click(int position, View itemView) {
        if (position != mTypeCateAdapter.getPosCurrentTab2()) {
            TextView tvName;
            if (position == 0)
                tvName = (TextView) itemView.findViewById(R.id.textViewItemTypeCate);
            else tvName = (TextView) itemView.findViewById(R.id.textViewNameTypeCate);
            String txt="";
            if(tvName==null)
                return;
            txt = fortmartStr(tvName.getText().toString(),12);
            if (position == 0)
                typeId = -1;
            else {
                TypeCate typeCate = (TypeCate) mTypeCateList.get(position);
                if (typeCate != null)
                    typeId = typeCate.getId();
            }
            setTextTab(tvTab2, txt, getResources().getColor(R.color.colorPrimary));
            if (position == 0)
                setTextTab(tvTab2, txt, getResources().getColor(R.color.text_color));
            mTypeCateAdapter.setPosCurrentTab2(position);                        // dat lai dong duoc chon
            mPresenterRestaurant.getRestaurantAPI(city_id, categoryId, districtId, streetid,typeId, CATE_TYPE_ID);
        }
        hideTab();
    }
    // click thanh pho
    private void setItemTab3Click(View itemView, int position) {
        int pos = expanableListAdapter.getPos();
        if (position != pos) {
            TextView tvName;
            tvName = (TextView) itemView.findViewById(R.id.textViewDistrictName);
            tvName.setTextColor(getResources().getColor(R.color.colorPrimary));     // doi mau text
            if (pos == -1)                                                // doi mau text truoc do
                tvChangeCity.setTextColor(getResources().getColor(R.color.text_color));
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
            setTextTab(tvTab3, fortmartStr(String.valueOf(tvName.getText()),12), getResources().getColor(R.color.colorPrimary));
            expanableListAdapter.setPos(position);                 // dat lai dong duoc chon
            mPresenterRestaurant.getRestaurantAPI(city_id, categoryId, districtId,streetid, typeId, CATE_TYPE_ID);
        }
        expanableListAdapter.notifyDataSetChanged();
        exLvTab3.setAdapter(expanableListAdapter);
        hideTab();
    }
    // doi mau text cua tab
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
    public void configRestaurant(List<Restaurant> items) {
        scrollView.scrollTo(0,0);
        Log.e("items ff", items.size()+"");
        configMainContent(items);
        hideLoading("");
    }
    @Override
    public void error(String err) {
        tvErr.setText(err);
        viewError.setVisibility(View.VISIBLE);
        mRecyclerViewRestaurant.setVisibility(View.GONE);
        hideLoading("");
    }

    @Override
    public void onPauseFragment() {
        mTabHost.setCurrentTab(0);
        Log.e("Places", "onPause");
    }

    @Override
    public void onResumeFragment() {
        MainActivity.setStateTabHost(View.VISIBLE);
        Log.e("Places", "OnResume");
    }

    public static void setCurrentPage() {
        mTabHost.setCurrentTab(0);
    }

    @Override
    public void showLoading() {
        m_Dialog.show();
    }

    @Override
    public void hideLoading(String msg) {
        if (m_Dialog.isShowing()) {
            m_Dialog.dismiss();
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

    //
//    @Override
//    public void getDataLoadMore(final int offset, final List<Restaurant> objects) {
//        mRestaurantList.add(null);
//        mRestaurantAdapter.notifyItemInserted(mRestaurantList.size()-1);
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mRestaurantList.remove(null);
//                mRestaurantAdapter.notifyItemRemoved(mRestaurantList.size()-1);
//                mPresenterRestaurant.getRestaurantAPIByLoadMore(city_id,categoryId,districtId,typeId, CATE_TYPE_ID,offset);
//                mRestaurantList.addAll(objects);
//            }
//        }, 100);
//        scrollListener.setLoading(false);
//    }
}
