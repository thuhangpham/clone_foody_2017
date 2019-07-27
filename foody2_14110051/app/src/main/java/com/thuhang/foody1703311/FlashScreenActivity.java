package com.thuhang.foody1703311;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

public class FlashScreenActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);

        progressBar = (ProgressBar) findViewById(R.id.pg_flash_screen);

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(3000);
                }catch (Exception e){
                        e.printStackTrace();
                }finally{
                    Intent intent=new Intent(FlashScreenActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        thread.start();
    }
}
