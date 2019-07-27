package com.thuhang.foody1703311.presenters;

import android.content.Context;

import com.thuhang.foody1703311.R;
import com.thuhang.foody1703311.database.TypeCateHandler;
import com.thuhang.foody1703311.interfaces.IPresenterTypeCate;
import com.thuhang.foody1703311.interfaces.IView;

import java.util.List;

import static com.thuhang.foody1703311.util.Constant.DATABASE_NAME;
import static com.thuhang.foody1703311.util.Constant.VERSION;

/**
 * Created by ThuHang on 4/3/2017.
 */

public class PresenterTypeCate implements IPresenterTypeCate{
    private IView mIView;
    private TypeCateHandler typeCateHandler;
    private Context mContext;

    public PresenterTypeCate(IView mIView, Context mContext) {
        this.mIView = mIView;
        this.mContext = mContext;
        typeCateHandler = new TypeCateHandler(mContext, DATABASE_NAME,null, VERSION);
    }


    @Override
    public void getAllTypeCateByType(int typeId) {
        List<Object> typeCateList = typeCateHandler.getTypeCateByType(typeId);
        if(typeCateList.size()>0)
            mIView.configListTypeCate(typeCateList);
        else
            mIView.error(mContext.getString(R.string.not_found));
    }
}
