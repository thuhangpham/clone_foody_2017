package com.thuhang.foody1703311;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.thuhang.foody1703311.interfaces.IPresenterUser;
import com.thuhang.foody1703311.models.User;
import com.thuhang.foody1703311.presenters.PresenterUser;

import static com.thuhang.foody1703311.MainActivity.setStateTabHost;

public class UserActivity extends AppCompatActivity implements View.OnClickListener, IPresenterUser {
    public static int  USER_ID = 0;
    public static User USER;
    public static String USERNAME = "Đăng nhập";
    private PresenterUser presenterUser;
    private View logOut, logIn, infoMana;
    private ImageView imgAvatar;
    private TextView tvLogin;
    private ScrollView mScroll;
    private Button btnActi;
    private View settingAcc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        connectView();
//        userID = getMyPreferences(getBaseContext(),USER,USER_ID,1);
//        username = getMyPreferences(getBaseContext(),USER,USERNAME,"");
        if(USER_ID>0) {
            presenterUser = new PresenterUser(this);
            presenterUser.getUserById(USER_ID);
        }
        logOut.setOnClickListener(this);
        logIn.setOnClickListener(this);
        settingAcc.setOnClickListener(this);
        infoMana.setOnClickListener(this);
    }
    private void preLoad(){
        mScroll.scrollTo(0,0);
        setStateTabHost(View.VISIBLE);
        if(USER_ID==0) {
            logOut.setVisibility(View.GONE);
            imgAvatar.setImageResource(R.drawable.tn_ic_not_login_profile);
        }else{
            logOut.setVisibility(View.VISIBLE);
            imgAvatar.setImageResource(R.drawable.ic_vector_user);
            btnActi.setText("Xem hoạt động");
        }
        tvLogin.setText(USERNAME);
    }
    @Override
    protected void onStart() {
        super.onStart();
        preLoad();
    }
    private void connectView(){
        logOut = findViewById(R.id.log_out);
        imgAvatar = (ImageView)findViewById(R.id.avatar);
        tvLogin = (TextView)findViewById(R.id.tv_login);
        logIn = findViewById(R.id.login);
        mScroll = (ScrollView)findViewById(R.id.scroll);
        btnActi =(Button)findViewById(R.id.btnuser);
        settingAcc = findViewById(R.id.setting_acc);
        infoMana = findViewById(R.id.info);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.log_out:
                setLogOut();
                break;
            case R.id.login:
                setLogIn();
                break;
            case R.id.setting_acc:
                setSettingAcc();
                break;
            case R.id.info:
                if(USER_ID<=0)
                    startActivity((new Intent(getBaseContext(),LoginActivity.class)));
                else
                    startActivity((new Intent(getBaseContext(),InfoActivity.class)));
                break;
        }
    }
    private void setSettingAcc(){
        Intent i = null;
        if(USER_ID==0){
            i = new Intent(this, LoginActivity.class);
        }else
            i = new Intent(this, SettingAccActivity.class);
        if(i!=null)
            startActivity(i);

    }
    // dang nhap
    private void setLogIn(){
        if(USER_ID==0) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }
    }
    // dang xuat
    private void setLogOut(){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Đăng xuất");
        dialog.setMessage(this.getResources().getString(R.string.log_out));
        dialog.setPositiveButton(this.getResources().getString(R.string.signout), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
               /* setMyPreferences(getBaseContext(),USER, USER_ID,0);*/
                UserActivity.USER_ID=0;
                UserActivity.USERNAME="Đăng nhập";
                btnActi.setText("");
                preLoad();
            }
        });
        dialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                paramDialogInterface.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void getUserById(User user) {
        tvLogin.setText(user.getUsername());
    }
}

