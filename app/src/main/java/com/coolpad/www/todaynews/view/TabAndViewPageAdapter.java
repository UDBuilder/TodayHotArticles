package com.coolpad.www.todaynews.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.coolpad.www.todaynews.datamodel.data.AllCategory;

/**
 * Created by xiongjianbo on 2018/3/7.
 */

public class TabAndViewPageAdapter extends FragmentPagerAdapter {


    private AllCategory mAllCategory;

    public TabAndViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return NewsFragment.newInstance(mAllCategory.getCategoryItem(position));
    }

    @Override
    public int getCount() {
        return mAllCategory == null ? 0 : mAllCategory.getListSize();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mAllCategory != null && mAllCategory.getListSize() > 0) {
            return mAllCategory.getCategoryList().get(position).getName();
        }
        return super.getPageTitle(position);
    }

    public void setData(AllCategory allCategory) {
        mAllCategory = allCategory;
        notifyDataSetChanged();
    }


}
