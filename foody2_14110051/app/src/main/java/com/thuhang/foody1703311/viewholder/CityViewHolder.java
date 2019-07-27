package com.thuhang.foody1703311.viewholder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.thuhang.foody1703311.MainActivity;
import com.thuhang.foody1703311.R;

import static com.thuhang.foody1703311.ChooseCityActivity.CITY_POSITION;
import static com.thuhang.foody1703311.ChooseCityActivity.CITY_SETTING;
import static com.thuhang.foody1703311.ChooseCityActivity.CITY_STORED;
import static com.thuhang.foody1703311.ChooseCityActivity.cityStored;
import static com.thuhang.foody1703311.ChooseCityActivity.posStored;

/**
 * Created by ThuHang on 3/21/2017.
 */

public class CityViewHolder extends RecyclerView.ViewHolder {
    private TextView textView;
    private Button btnCity;
    private ImageView imgStick;
    private Context mContext;
    private ImageView check;

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public Button getBtnCity() {
        return btnCity;
    }

    public void setBtnCity(Button btnCity) {
        this.btnCity = btnCity;
    }


    public ImageView getImgStick() {
        return imgStick;
    }

    public void setImgStick(ImageView imgStick) {
        this.imgStick = imgStick;
    }

    public ImageView getCheck() {
        return check;
    }

    public void setCheck(ImageView check) {
        this.check = check;
    }

    public CityViewHolder(final View itemView, Context context) {
        super(itemView);
        this.mContext = context;
        textView = (TextView)itemView.findViewById(R.id.textViewCityName);
        btnCity = (Button)itemView.findViewById(R.id.buttonCityDefault);
        imgStick = (ImageView)itemView.findViewById(R.id.imgStick);
        check = (ImageView)itemView.findViewById(R.id.imgCateChecked);
        btnCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settingCity =  mContext.getSharedPreferences(CITY_SETTING, 0);
                SharedPreferences.Editor editor = settingCity.edit();
                editor.putInt(CITY_STORED, cityStored);
                editor.putInt(CITY_POSITION, posStored);
                editor.apply();
                Intent i = new Intent(mContext, MainActivity.class);
                mContext.startActivity(i);
            }
        });
    }

}
