package com.thuhang.foody1703311;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.thuhang.foody1703311.interfaces.IBaseView;
import com.thuhang.foody1703311.models.User;
import com.thuhang.foody1703311.retrofit.UserService;

import static com.thuhang.foody1703311.UserActivity.USERNAME;
import static com.thuhang.foody1703311.UserActivity.USER_ID;
import static com.thuhang.foody1703311.util.Utils.isLess;
import static com.thuhang.foody1703311.util.Utils.isNotNull;
import static com.thuhang.foody1703311.util.Utils.isValidMatcherPass;
import static com.thuhang.foody1703311.util.Utils.isValidMatcherUserName;
import static com.thuhang.foody1703311.util.Utils.validate;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, IBaseView{
    private Toolbar mToolbar;
    private TextView tvRegister,tvLogin;
    private EditText edituser,editpassword, editmail, editcomfirm_password;
    private ProgressDialog loading;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        connectView();
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Đăng nhập Foody");
            ab.setDisplayHomeAsUpEnabled(true);
        }
        sharedPreferences = getApplicationContext().getSharedPreferences(USERNAME, 1);
        editor = sharedPreferences.edit();
        loading=new ProgressDialog(this);
        loading.setMessage("Đang đăng ký");
        loading.setCancelable(false);
        tvRegister.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
    }
    private void connectView(){
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        tvRegister = (TextView)findViewById(R.id.txt_register);
        editcomfirm_password = (EditText)findViewById(R.id.editTextCONFIRMPASSWORD);
        editmail=(EditText)findViewById(R.id.editEMAIL);
        editpassword=(EditText)findViewById(R.id.editTextPASSWORD);
        edituser=(EditText)findViewById(R.id.editTextUSERNAME);
        tvLogin = (TextView)findViewById(R.id.txt_login);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_register:
                setTvRegister();
                break;
            case R.id.txt_login:
                startActivity((new Intent(getBaseContext(),LoginEmailActivity.class)));
                break;
        }
    }
    private void setTvRegister(){
            final User user = new User();
            user.setPassword(editpassword.getText().toString().trim());
            user.setUsername(edituser.getText().toString().toLowerCase().trim());
            user.setEmail(editmail.getText().toString().toLowerCase().trim());
            if(!isNotNull(user.getEmail()))
            {
                editmail.requestFocus();
                editmail.setError("Please type your email!");
            }else
            if(!validate(user.getEmail()))
            {
                editmail.requestFocus();
                editmail.setError("Email incorrect!");
            }else
            if(!isNotNull(user.getPassword()))
            {
                editpassword.requestFocus();
                editpassword.setError("Please type your password!");
            }else
            if(!isLess(user.getPassword())) {
                editpassword.requestFocus();
                editpassword.setError("Your password must have more than 4 characters.");
            }
            else

            if(isValidMatcherPass(user.getPassword()))
            {
                editpassword.requestFocus();
                editpassword.setError("Password only include the characters A-Za-z0-9.");
            }else
            if(!isNotNull(editcomfirm_password.getText().toString()))
            {
                editcomfirm_password.requestFocus();
                editcomfirm_password.setError("Please type your confirm password!");
            }
            else
            if(!user.getPassword().equals(editcomfirm_password.getText().toString()))
            {
                editcomfirm_password.requestFocus();
                editcomfirm_password.clearComposingText();
                editcomfirm_password.setError("The password confirmation must match your password");
            }else
            if(user.getUsername().equals(""))
            {
                edituser.requestFocus();
                edituser.setError("Please type your username!");
            }
            else
            if(!isValidMatcherUserName(user.getUsername()))
            {
                edituser.requestFocus();
                edituser.setError("Username only include a-z, 0-9, 3-25 characters!");
            }
            else{
                UserService createUser = new UserService(this);
                createUser.createUser(user);
            }
    }

    @Override
    public void showLoading() {
        loading.show();
    }

    @Override
    public void hideLoading(String msg) {
        Log.e("id", USER_ID+"");
        UserActivity.USERNAME= String.valueOf(edituser.getText());
        //setMyPreferences(this,USER,USER_ID,1);
       /* editor.putInt(USER_ID,1);
        editor.putString(USERNAME, String.valueOf(edituser.getText()));
        editor.apply();
        Log.e("user_id",getMyPreferences(getBaseContext(),USER,USER_ID,0)+"");*/
        Intent i;
        if(USER_ID>0) {
            i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
        else if(USER_ID==0){
            loading.dismiss();
            Toast.makeText(getBaseContext(), "Đăng ký không thanh công! Lỗi kết nối đến server.", Toast.LENGTH_SHORT).show();
        }
        else{
            loading.dismiss();
            Toast.makeText(getBaseContext(), "Email đã tồn tại!", Toast.LENGTH_SHORT).show();
        }
    }
}
