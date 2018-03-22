package com.udbuilder.todaynews.view;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.udbuilder.todaynews.datamodel.data.CategoryData;

import java.util.ArrayList;

/**
 * Created by xiongjianbo on 2018/3/7.
 */

public class TabAndViewPageAdapter extends FragmentPagerAdapter {

    private ArrayList<CategoryData> mList = new ArrayList<>();

    public TabAndViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return NewsFragment.newInstance(mList.get(position));
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mList.size() > 0) {
            return mList.get(position).getName();
        }
        return super.getPageTitle(position);
    }

    public void setData(Cursor cursor) {
        mList.clear();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                mList.add(new CategoryData(cursor));
            } while (cursor.moveToNext());
        }
        notifyDataSetChanged();
    }
}
