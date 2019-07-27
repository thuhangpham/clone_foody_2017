package com.thuhang.foody1703311;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener  {
    private Toolbar mToolbar;
    private TextView tvSignUp;
    private View signinEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        connectView();
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Đăng nhập Foody");
            ab.setDisplayHomeAsUpEnabled(true);
        }
        tvSignUp.setOnClickListener(this);
        signinEmail.setOnClickListener(this);
    }
    private void connectView(){
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        tvSignUp = (TextView)findViewById(R.id.txtSingup);
        signinEmail = findViewById(R.id.SigninEmail);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txtSingup:
                setTvSignUp();
                break;
            case R.id.SigninEmail:
                setTvSinginEmail();
                break;
        }
    }
    private void setTvSinginEmail(){
        Intent i = new Intent(this,LoginEmailActivity.class);
        startActivity(i);
    }
    private void setTvSignUp(){
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }
}
