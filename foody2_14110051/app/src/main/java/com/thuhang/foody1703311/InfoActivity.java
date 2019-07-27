package com.thuhang.foody1703311;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.thuhang.foody1703311.interfaces.IBaseView;
import com.thuhang.foody1703311.interfaces.IPresenterUser;
import com.thuhang.foody1703311.models.User;
import com.thuhang.foody1703311.presenters.PresenterUser;
import com.thuhang.foody1703311.retrofit.UserService;

import static com.thuhang.foody1703311.UserActivity.USER_ID;
import static com.thuhang.foody1703311.util.Utils.isNotNull;

public class InfoActivity extends AppCompatActivity implements IPresenterUser, IBaseView {
    private Toolbar mToolbar;
    private PresenterUser presenterUser;

    private EditText edtUsername, edtFirstname, edtLastname;
    private TextView tvDate,tvEmail;
    private ProgressDialog loading;
    private  ActionBar ab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        connectView();
        setSupportActionBar(mToolbar);
        ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        presenterUser = new PresenterUser(this);
        presenterUser.getUserById(USER_ID);
        loading=new ProgressDialog(this);
        loading.setMessage("Đang lưu...");
        loading.setCancelable(false);
    }
    private void connectView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        edtUsername = (EditText)findViewById(R.id.profile_info_username);
        edtFirstname = (EditText)findViewById(R.id.profile_info_firstname);
        edtLastname=(EditText)findViewById(R.id.profile_info_lastname);
        tvDate=(TextView)findViewById(R.id.profile_info_date);
        tvEmail = (TextView)findViewById(R.id.profile_info_email);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_info,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mn_info:
                setSend();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void setSend(){
        User u = new User();
        u.setFirstname(edtFirstname.getText().toString());
        u.setUsername(edtUsername.getText().toString());
        u.setLastname(edtLastname.getText().toString());
        if(!isNotNull(u.getUsername().trim()))
            edtUsername.requestFocus();
        else
        {
            UserService userService= new UserService(this);
            userService.updateUser(u);
        }

    }
    @Override
    public void getUserById(User user) {
        Log.e("uname",user.getUsername());
        ab = getSupportActionBar();
        ab.setTitle(user.getUsername());
        edtUsername.setText(user.getUsername());
        edtFirstname.setText(user.getFirstname());
        edtLastname.setText(user.getLastname());
        tvEmail.setText(user.getEmail());
    }

    @Override
    public void showLoading() {
        loading.show();
    }

    @Override
    public void hideLoading(String msg) {
        loading.dismiss();
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
}
