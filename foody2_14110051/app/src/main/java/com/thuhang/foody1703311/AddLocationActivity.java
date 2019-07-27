package com.thuhang.foody1703311;


import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.thuhang.foody1703311.adapters.CityAdapterLoca;
import com.thuhang.foody1703311.adapters.TypeAdapterLoca;
import com.thuhang.foody1703311.database.CityHandler;
import com.thuhang.foody1703311.database.DistrictHandler;
import com.thuhang.foody1703311.fragments.PlacesFragment;
import com.thuhang.foody1703311.googleAPI.GoogleAPI;
import com.thuhang.foody1703311.interfaces.IBaseView;
import com.thuhang.foody1703311.models.City;
import com.thuhang.foody1703311.models.District;
import com.thuhang.foody1703311.models.Restaurant;
import com.thuhang.foody1703311.models.TypeCate;
import com.thuhang.foody1703311.retrofit.PostRestaurant;
import com.thuhang.foody1703311.util.MyBitMap;
import com.thuhang.foody1703311.util.Utils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.thuhang.foody1703311.ChooseCityActivity.CITY_SETTING;
import static com.thuhang.foody1703311.ChooseCityActivity.CITY_STORED;
import static com.thuhang.foody1703311.util.Constant.DATABASE_NAME;
import static com.thuhang.foody1703311.util.Constant.VERSION;
import static com.thuhang.foody1703311.util.Utils.fortmartStr;
import static com.thuhang.foody1703311.util.Utils.isNotNull;

public class AddLocationActivity extends AppCompatActivity implements
        View.OnClickListener,LocationListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        Animation.AnimationListener , IBaseView {
    private static final long UPDATE_INTERVAL = 5000;
    private static final long FASTEST_INTERVAL = 5000;
    private static final int PICK_PICTURE = 1;

    private Button btnt1, btnt2, btncity, btnIgnore, btnDistrict;
    private TextView tvType, tvLatLng;
    private EditText txtResName, txtAddress;
    private View lnType, lnJPS;
    private ImageView imgAddPicture, imgTakePicture, pic;
    private String fileURI;
    private String encodedString;
    private Double lat=0.0,lng=0.0;
    AlertDialog.Builder dialogC, dialogD, dialogT;
    private RecyclerView cityRecyclerView, AllTypeRC;
    private List<Object> arrayCity = new ArrayList<>();
    private List<Object> arrayType = new ArrayList<>();
    private CityAdapterLoca adapterCity, adapterDis;
    private TypeAdapterLoca allTypeAdapter;
    private List<Object> arrayDistrict = new ArrayList<>();
    private AlertDialog alertDialogCity, alertDialogDis, alertDialogType;
    private DistrictHandler districtHandler;

    private City city;
    private District district;
    private TypeCate mType;

    private ProgressDialog loading;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        connectView();
        // load the animation
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sequential);
        // set animation listener
        anim.setAnimationListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle("Thêm địa điểm");
        }
        dialogC = new AlertDialog.Builder(this);
        dialogD = new AlertDialog.Builder(this);
        dialogT = new AlertDialog.Builder(this);
        loading=new ProgressDialog(this);
        loading.setMessage("Đang lưu...");
        loading.setCancelable(false);

        CityHandler cityHandler = new CityHandler(getBaseContext(), DATABASE_NAME, null, VERSION);
        arrayCity = cityHandler.getAllCity();
        SharedPreferences settings = getSharedPreferences(CITY_SETTING, 0);
        if (city == null)
            city = cityHandler.getCityById(settings.getInt(CITY_STORED, 1));
        btncity.setText(city.getName());
        arrayDistrict = PlacesFragment.mAddressList;


        btnDistrict = (Button) findViewById(R.id.btnDistrict);
        setDialogC();
        setDialogD();
        setLnType();

        btnt1.setOnClickListener(this);
        btnt2.setOnClickListener(this);
        btncity.setOnClickListener(this);
        lnType.setOnClickListener(this);
        btnDistrict.setOnClickListener(this);
        lnJPS.setOnClickListener(this);
        imgAddPicture.setOnClickListener(this);

        setUpLocationClientIfNeeded();
        buildLocationRequest();
    }

    public void startAnimation(View view) {
        // start the animation
        view.startAnimation(anim);
    }

    private void connectView() {
        btnt1 = (Button) findViewById(R.id.btnTimeOpen);
        btnt2 = (Button) findViewById(R.id.btnTimeClose);
        btncity = (Button) findViewById(R.id.btnCity);
        tvType = (TextView) findViewById(R.id.tvChooseType);
        lnType = findViewById(R.id.lnType);
        lnJPS = findViewById(R.id.ln_jps);
        tvLatLng = (TextView) findViewById(R.id.tv_latlng);
        imgAddPicture = (ImageView) findViewById(R.id.add_img);
        imgTakePicture = (ImageView) findViewById(R.id.take_img);
        pic = (ImageView) findViewById(R.id.imgAdd);
        txtAddress = (EditText)findViewById(R.id.txt_Address);
        txtResName = (EditText)findViewById(R.id.txtLocationName);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnTimeClose:
                setBtnt2();
                break;
            case R.id.btnTimeOpen:
                setBtnt1();
                break;
            case R.id.btnCity:
                alertDialogCity.show();
                break;
            case R.id.btnDistrict:
                alertDialogDis.show();
                break;
            case R.id.lnType:
                alertDialogType.show();
                break;
            case R.id.ln_jps:
                setLnJPS();
                break;
            case R.id.add_img:
                pickCamera();
                break;
        }
    }

    // lat lng
    private void setLnJPS() {
        GoogleAPI googleAPI = new GoogleAPI(this);
        if (!googleAPI.isPlayServicesAvailable()) {
            Toast.makeText(getBaseContext(), "Thiết bị không hỗ trợ Google play service.", Toast.LENGTH_SHORT).show();
            return;
        } else if (!googleAPI.isGpsOn()) {
            // notify user
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
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
            return;
        }
        updateUi();
    }

    @SuppressLint("NewApi")
    private String getPath(Uri uri) {
        if( uri == null ) {
            return null;
        }
        String[] projection = { MediaStore.Images.Media.DATA };

        Cursor cursor;
        if(Build.VERSION.SDK_INT >19)
        {
            // Will return "image:x*"
            String wholeID = DocumentsContract.getDocumentId(uri);
            // Split at colon, use second item in the array
            String id = wholeID.split(":")[1];
            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";
            cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection, sel, new String[]{ id }, null);
        }
        else
        {
            cursor = getContentResolver().query(uri, projection, null, null, null);
        }
        String path = null;
        try
        {
            int column_index = cursor
                    .getColumnIndex(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index).toString();
            cursor.close();
        }
        catch(NullPointerException e) {
            e.printStackTrace();
        }
        return path;
    }

    private void pickCamera(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra("return-data", true);
        try {
            startActivityForResult(intent, PICK_PICTURE);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }

    }
    // nhan resultcode de lay ket qua
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != data) {
            switch (requestCode) {
                case PICK_PICTURE:
                    final Bundle extras = data.getExtras();
                    if (extras != null) {
                        //Get image
                        //Bitmap bitmap = extras.getParcelable("data");
                        try {
                            fileURI = getPath(data.getData());
                            MyBitMap myBitMap = new MyBitMap(this);
                            Bitmap bitmap = myBitMap.decodeUri(data.getData());
                            pic.setImageBitmap(bitmap);
                            encodedString = myBitMap.getStringFromBitmap(bitmap);
                        }
                        catch (OutOfMemoryError | FileNotFoundException e){
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    }

    //chon loai hinh
    private void setLnType(){
        // hiển thị dialog
        dialogT.setCancelable(true);
        // custom title
        LayoutInflater layoutInflater = LayoutInflater.from((dialogD.getContext()));
        View v = layoutInflater.inflate(R.layout.bgr_dialog, null);
        TextView tv = (TextView)v.findViewById(R.id.title);
        tv.setText("Chọn loại hình");
        dialogT.setCustomTitle(v);
        // custom content
        View add = layoutInflater.inflate(R.layout.layout_add_loca_cate, null);
        AllTypeRC = (RecyclerView) add.findViewById(R.id.lv_all_type_addlocation);
        Button btn_ignore = (Button)add.findViewById(R.id.btnIgnoreType);
        Button btn_completed = (Button)add.findViewById(R.id.btnComleted);
        btn_completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mType!=null)
                    tvType.setText(mType.getName());
                else
                    tvType.setText("Chọn loại hình*");
                alertDialogType.dismiss();
            }
        });
        btn_ignore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogType.dismiss();
            }
        });
        //CategoryTypeHandler categoryTypeHandler = new CategoryTypeHandler(this,DATABASE_NAME, null,VERSION);
        arrayType = PlacesFragment.mTypeCateList;
        arrayType.remove(0);
        allTypeAdapter = new TypeAdapterLoca(arrayType,this);

        Log.e("sized",arrayType.size()+"");
        AllTypeRC.setLayoutManager(new LinearLayoutManager(this));
        AllTypeRC.setAdapter(allTypeAdapter);
        dialogT.setView(add);
        alertDialogType = dialogT.create();
        allTypeAdapter.setOnItemClickListener(new Utils.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                mType= (TypeCate) arrayType.get(position);
                allTypeAdapter.setPosCurrentTab2(position);
            }
        });
    }
    // chon quan/huyen
    private void setDialogD(){
        dialogD.setCancelable(true);
        // custom title
        LayoutInflater layoutInflater = LayoutInflater.from((dialogD.getContext()));
        View v = layoutInflater.inflate(R.layout.bgr_dialog, null);
        TextView tv = (TextView)v.findViewById(R.id.title);
        tv.setText("Chọn quận/huyện");
        dialogD.setCustomTitle(v);
        // custom content
        View add = layoutInflater.inflate(R.layout.layout_city_addlocation, null);
        cityRecyclerView = (RecyclerView) add.findViewById(R.id.lv_city_addlocation);
        adapterDis = new CityAdapterLoca(arrayDistrict,getBaseContext());
        Log.e("sized",arrayDistrict.size()+"");
        cityRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cityRecyclerView.setAdapter(adapterDis);
        dialogD.setView(add);
        alertDialogDis = dialogD.create();
        btnIgnore = (Button) add.findViewById(R.id.btnIgnore);
        btnIgnore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogDis.dismiss();
                // hideLoading();
            }
        });
        adapterDis.setItemClickListener(new Utils.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                district = (District) arrayDistrict.get(position);
                btnDistrict.setText(fortmartStr(district.getDistrictName(),8));
                adapterDis.setPosCurrent(position);
                alertDialogDis.dismiss();
            }
        });
    }
    // chon thanh pho
    private void setDialogC(){
        dialogC.setCancelable(true);
        // custom title
        LayoutInflater layoutInflater = LayoutInflater.from((dialogC.getContext()));
        View v = layoutInflater.inflate(R.layout.bgr_dialog, null);
        TextView tv = (TextView)v.findViewById(R.id.title);
        tv.setText("Chọn thành phố");
        dialogC.setCustomTitle(v);
        // custom content
        View add = layoutInflater.inflate(R.layout.layout_city_addlocation, null);
        cityRecyclerView = (RecyclerView) add.findViewById(R.id.lv_city_addlocation);
        adapterCity = new CityAdapterLoca(arrayCity,getBaseContext());
        Log.e("size",arrayCity.size()+"");
        cityRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cityRecyclerView.setAdapter(adapterCity);
        dialogC.setView(add);
        alertDialogCity = dialogC.create();
        btnIgnore = (Button) add.findViewById(R.id.btnIgnore);
        btnIgnore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogCity.dismiss();
                // hideLoading();
            }
        });
        adapterCity.setItemClickListener(new Utils.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                city = (City) arrayCity.get(position);
                btncity.setText(fortmartStr(city.getName(),8));
                adapterCity.setOldPos(position);
                district=null;
                btnDistrict.setText("Chọn quận");
                arrayDistrict.clear();
                arrayDistrict = districtHandler.getAllDistrictByCity(Integer.parseInt(city.getId()));
                setDialogD();
                alertDialogCity.dismiss();
            }
        });
    }
    // cat bot chuoi

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_loca,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mn_send:
                setSend();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    // them quan an
    private void setSend(){
        if(mType==null||district==null||!isNotNull(String.valueOf(txtResName.getText()))) {
            Toast.makeText(getBaseContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!isNotNull(String.valueOf(encodedString))||encodedString==null) {
            Toast.makeText(getBaseContext(), "Vui lòng chọn hình ảnh!", Toast.LENGTH_SHORT).show();
            return;
        }
        Restaurant res = new Restaurant();
        res.setAddress(txtAddress.getText().toString());
        res.setLatitude(lat.toString());
        res.setLongitude(lng.toString());
        res.setCateId(1);
        res.setCityId(Integer.parseInt(city.getId()));
        res.setCatetypeid(1);
        res.setName(txtResName.getText().toString());
        res.setDistrictId(Integer.parseInt(district.getDistrictId()));
        res.setTypeId(mType.getId());
        res.setImg(encodedString);
        PostRestaurant post = new PostRestaurant(this);
        post.postRestaurant(res);
    }
    // gio mo cua
    private void setBtnt1(){
        Calendar mcurrentTime = Calendar.getInstance();
        final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(AddLocationActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                if(selectedHour<11){
                    btnt1.setText(selectedHour + ":" + selectedMinute + " AM");
                }
                else
                    btnt1.setText(selectedHour + ":" + selectedMinute + " PM");

            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Giờ mở cửa");
        mTimePicker.show();
    }
    //gio dong cua
    private void setBtnt2(){
        Calendar mcurrentTime = Calendar.getInstance();
        final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                if(selectedHour<11){
                    btnt2.setText(selectedHour + ":" + selectedMinute + " AM");
                }
                else
                    btnt2.setText(selectedHour + ":" + selectedMinute + " PM");

            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Giờ mở cửa");
        mTimePicker.show();
    }
    // cap nhat lat, lng
    private void updateUi() {
        if(mLastLocation==null)
            onConnected(null);
        if (mLastLocation != null) {
            lat = mLastLocation.getLatitude();
            lng = mLastLocation.getLongitude();
            tvLatLng.setText("Lat "+ mLastLocation.getLatitude()+"- Long "+mLastLocation.getLongitude());
        }
        //Log.e("address",getAddressFromLatLng(getBaseContext(), lat,lng).toString());
    }
    @Override
    public void onLocationChanged(Location location) {
        Log.d("onLocationChanged", String.format(Locale.getDefault(), "onLocationChanged : %f, %f",
                location.getLatitude(), location.getLongitude()));
        mLastLocation = location;
        updateUi();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
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
    protected void onDestroy() {
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
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
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
            mGoogleApiClient = new GoogleApiClient.Builder(this)
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

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void showLoading() {
        loading.show();
    }

    @Override
    public void hideLoading(String msg) {
        loading.dismiss();
        Toast.makeText(getBaseContext(),msg,Toast.LENGTH_SHORT).show();
    }
}
