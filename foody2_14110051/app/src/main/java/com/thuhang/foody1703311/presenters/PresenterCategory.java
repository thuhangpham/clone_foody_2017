package com.thuhang.foody1703311.presenters;

import android.content.Context;
import android.os.AsyncTask;

import com.thuhang.foody1703311.R;
import com.thuhang.foody1703311.database.CategoryHandler;
import com.thuhang.foody1703311.interfaces.IPresenterCate;
import com.thuhang.foody1703311.interfaces.IView;
import com.thuhang.foody1703311.models.Categories;

import java.util.ArrayList;
import java.util.List;

import static com.thuhang.foody1703311.util.Constant.DATABASE_NAME;
import static com.thuhang.foody1703311.util.Constant.VERSION;

/**
 * Created by ThuHang on 4/3/2017.
 */

public class PresenterCategory implements IPresenterCate {
    private IView mIView;
    private CategoryHandler categoryHandler;
    private Context mContext;
    private List<Categories> categoriesList = new ArrayList<>();
    public PresenterCategory(IView mIView, Context mContext) {
        this.mIView = mIView;
        this.mContext = mContext;
        categoryHandler = new CategoryHandler(mContext, DATABASE_NAME,null, VERSION);
    }

    @Override
    public void getALlCate() {
        mIView.showLoading();
        GetCates getCates = new GetCates();
        getCates.execute();
    }
    class GetCates extends AsyncTask<String, Categories, List<Categories>>{

        @Override
        protected List<Categories> doInBackground(String... params) {
            categoriesList  = categoryHandler.getAllCategory();
            return categoriesList;
        }
        @Override
        protected void onPostExecute(List<Categories> categories) {
            if(categories.size()>0)
                mIView.configListCate(categories);
            else
                mIView.error(mContext.getString(R.string.not_found));
        }
    }
}
