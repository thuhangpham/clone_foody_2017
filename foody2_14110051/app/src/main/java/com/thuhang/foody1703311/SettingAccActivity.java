package com.thuhang.foody1703311;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.thuhang.foody1703311.interfaces.IPresenterUser;
import com.thuhang.foody1703311.models.User;
import com.thuhang.foody1703311.presenters.PresenterUser;

import static com.thuhang.foody1703311.UserActivity.USER_ID;
import static com.thuhang.foody1703311.retrofit.ApiUtils.BASE_URL;

public class SettingAccActivity extends AppCompatActivity implements IPresenterUser {
    private Toolbar mToolbar;
    private ImageView imgAvatar;
    private PresenterUser presenterUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_acc);
        connectView();
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Thiết lập phiên hoạt động");
            ab.setDisplayHomeAsUpEnabled(true);
        }
        if(USER_ID>0) {
            presenterUser = new PresenterUser(this);
            presenterUser.getUserById(USER_ID);
        }
    }
    private void connectView(){
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        imgAvatar = (ImageView)findViewById(R.id.imageViewUser);
    }

    @Override
    public void getUserById(User user) {
        Glide.with(getBaseContext()).load(BASE_URL+" ,images/user/ava"+user.getImageURL()).error(R.drawable.ic_user_avatar).into(imgAvatar);
    }
}
