package com.thuhang.foody1703311.googleAPI;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by thuha on 5/14/2017.
 */

public class GoogleAPI{
    private static final long UPDATE_INTERVAL = 5000;
    private static final long FASTEST_INTERVAL = 5000;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private double lat, lng;
    private  LocationManager manager;
    private Context mContext;
    public GoogleAPI(Context context){
        this.mContext = context;
        manager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
    }
    public boolean isPlayServicesAvailable() {
        return GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(mContext)
                == ConnectionResult.SUCCESS;
    }
    public boolean isGpsOn() {
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
    public boolean connectInternet(){
        return manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

    }
    // tinh khoang cach 2 diem
    public double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
    public static List<Address> getAddressFromLatLng(Context mContext,Double mLat, Double mLong){
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(mContext, Locale.getDefault());
        try {
            String sPlace;
            addresses = geocoder.getFromLocation(mLat, mLong, 1);
            String address = addresses.get(0).getAddressLine(0);
            String city = addresses.get(0).getAddressLine(1);
            String country = addresses.get(0).getAddressLine(2);

            String[] splitAddress = address.split(",");
            sPlace = splitAddress[0] + "\n";
            if(city != null && !city.isEmpty()) {
                String[] splitCity = city.split(",");
                sPlace += splitCity[0];
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return addresses;
    }
}

