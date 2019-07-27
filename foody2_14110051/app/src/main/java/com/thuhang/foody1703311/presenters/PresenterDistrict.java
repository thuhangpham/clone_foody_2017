package com.thuhang.foody1703311.presenters;

import android.content.Context;

import com.thuhang.foody1703311.R;
import com.thuhang.foody1703311.database.DistrictHandler;
import com.thuhang.foody1703311.interfaces.IPresenterDistrict;
import com.thuhang.foody1703311.interfaces.IView;
import com.thuhang.foody1703311.models.District;

import java.util.List;

import static com.thuhang.foody1703311.util.Constant.DATABASE_NAME;
import static com.thuhang.foody1703311.util.Constant.VERSION;

/**
 * Created by ThuHang on 4/3/2017.
 */

public class PresenterDistrict implements IPresenterDistrict {
    private IView mIView;
    private DistrictHandler districtHandler;
    private Context mContext;

    public PresenterDistrict(IView mIView, Context mContext) {
        this.mIView = mIView;
        this.mContext = mContext;
        districtHandler = new DistrictHandler(mContext,DATABASE_NAME, null, VERSION);
    }

    @Override
    public void getDistrictByCityId(int cityId) {
       List<Object> districtList = districtHandler.getAllDistrictByCity(cityId);
        if (districtList.size()>0)
            mIView.configListDistrict(districtList);
        else
            mIView.error(mContext.getString(R.string.not_found));

    }
}
