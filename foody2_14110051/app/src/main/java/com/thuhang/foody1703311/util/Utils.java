package com.thuhang.foody1703311.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Editable;
import android.view.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ThuHang on 3/24/2017.
 */

public class Utils{
    private static final String USERNAME_PATTERN = "^(?=.{3,25}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$",
            PASSWORD_PATTERN = "^[_A-Za-z0-9]{6,}",
            EMAIL_PATTERN    =   "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static Pattern pattern;
    private static Matcher matcher;
    public Bitmap decodeToBitmap(byte[] encode){
        Bitmap decodedByte = BitmapFactory.decodeByteArray(encode, 0, encode.length);
        return decodedByte;
    }
    public static void setMyPreferences(Context mContext, String KEY, String VALUE, int value){
        SharedPreferences settingCity =  mContext.getSharedPreferences(KEY, 0);
        SharedPreferences.Editor editor = settingCity.edit();
        editor.putInt(VALUE, value);
        editor.apply();
    }
    public static void setMyPreferences(Context mContext, String KEY, String VALUE, Editable value){
        SharedPreferences settingCity =  mContext.getSharedPreferences(KEY, 0);
        SharedPreferences.Editor editor = settingCity.edit();
        editor.putString(VALUE, String.valueOf(value));
        editor.apply();
    }
    public static int getMyPreferences(Context context, String KEY, String VALUE, int value){
        SharedPreferences settings = context.getSharedPreferences(KEY, 0);
        return settings.getInt(KEY, value);
    }
    public static String getMyPreferences(Context context, String KEY, String VALUE, String value){
        SharedPreferences settings = context.getSharedPreferences(KEY, 0);
        return settings.getString(KEY, value);
    }
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    public static String fortmartStr(String str, int len){
        if(str.length()>len)
            str = str.substring(0,len)+"...";
        return str;
    }
    public boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static boolean validate(String email) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();

    }
    // kiem tra phone number nhap tu user
    public static boolean isValidMatcherPhone(String phone) {
        String PHONE_PATTERN = "^[_0-9]{10,}";
        pattern = Pattern.compile(PHONE_PATTERN);
        matcher = pattern.matcher(phone);
        return matcher.matches();
    }
    public static boolean isValidMatcherUserName(String username) {
        pattern = Pattern.compile(USERNAME_PATTERN);
        matcher = pattern.matcher(username);
        return matcher.matches();
    }
    public static boolean isNotNull(String txt){
        return txt!=null && txt.trim().length()>0 ? true: false;
    }
    public static boolean isLess(String txt){
        return txt!=null && txt.trim().length()>3 ? true: false;
    }
    public static boolean isValidMatcherPass(String pass) {

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(pass);
        return matcher.matches();
    }
}
