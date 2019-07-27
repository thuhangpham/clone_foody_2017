package com.thuhang.foody1703311;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.thuhang.foody1703311.interfaces.IBaseView;
import com.thuhang.foody1703311.models.User;
import com.thuhang.foody1703311.retrofit.UserService;

import static com.thuhang.foody1703311.util.Utils.isLess;
import static com.thuhang.foody1703311.util.Utils.isNotNull;
import static com.thuhang.foody1703311.util.Utils.validate;

public class LoginEmailActivity extends AppCompatActivity implements View.OnClickListener, IBaseView{
    private Toolbar mToolbar;
    private EditText edtEmail, edtPass;
    private ProgressDialog loading;
    private TextView tvLogin, tvSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);
        connectView();
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Đăng nhập với Email");
            ab.setDisplayHomeAsUpEnabled(true);
        }
        loading=new ProgressDialog(this);
        loading.setMessage("Đang đăng nhập");
        loading.setCancelable(false);

        tvLogin.setOnClickListener(this);
        tvSignup.setOnClickListener(this);
    }
    private void connectView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        edtEmail = (EditText)findViewById(R.id.edt_email);
        edtPass = (EditText)findViewById(R.id.edt_pass);
        tvLogin = (TextView)findViewById(R.id.tv_login);
        tvSignup = (TextView)findViewById(R.id.tv_singup);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login:
                setTvLogin();
                break;
            case R.id.tv_singup:
                setTvSignup();
                break;
        }
    }
    private void setTvLogin(){
        final User user = new User();
        user.setPassword(edtPass.getText().toString().trim());
        user.setEmail(edtEmail.getText().toString().toLowerCase().trim());
        if(!isNotNull(user.getEmail()))
        {
            edtEmail.requestFocus();
            edtEmail.setError("Please type your email!");
        }else
        if(!validate(user.getEmail()))
        {
            edtEmail.requestFocus();
            edtEmail.setError("Email incorrect!");
        }
        else
        if(!isNotNull(user.getPassword()))
        {
            edtPass.requestFocus();
            edtPass.setError("Please type your password!");
        }else
        if(!isLess(user.getPassword())) {
            edtPass.requestFocus();
            edtPass.setError("Your password must have more than 4 characters.");
        }
        else{
            UserService userService = new UserService(this);
            userService.userLogin(user);
        }
    }
    private void setTvSignup(){
        startActivity((new Intent(this,RegisterActivity.class)));
    }

    @Override
    public void showLoading() {
        loading.show();
    }

    @Override
    public void hideLoading(String msg) {
        loading.dismiss();
        if(!msg.equals("Đăng nhập thành công!")) {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage(msg);
            dialog.setCancelable(true);
            dialog.setNegativeButton(getString(R.string.close), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    paramDialogInterface.dismiss();
                }
            });
            dialog.show();
        }
        else{
            startActivity((new Intent(getBaseContext(),SettingAccActivity.class)));
        }
    }
}
