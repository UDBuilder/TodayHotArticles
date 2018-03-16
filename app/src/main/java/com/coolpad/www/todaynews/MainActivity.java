package com.coolpad.www.todaynews;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.coolpad.www.todaynews.datamodel.data.Constants;
import com.coolpad.www.todaynews.datamodel.data.HomeTop;
import com.coolpad.www.todaynews.datamodel.data.AllCategory;
import com.coolpad.www.todaynews.util.HttpUtil;
import com.coolpad.www.todaynews.view.TabAndViewPageAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private EditText mSearch;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private TabAndViewPageAdapter mAdapter;

    private static final int MSG_UPDATE_SEARCH_SUGGEST = 1;
    private static final int MSG_UPDATE_TAB_CATEGORY = 2;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_SEARCH_SUGGEST:
                    mSearch.setHint((String) msg.obj);
                    break;
                case MSG_UPDATE_TAB_CATEGORY:
                    setupTabLayout((AllCategory) msg.obj);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initHomeData();
    }

    private void initView() {
        mSearch = findViewById(R.id.home_search);
        mTabLayout = findViewById(R.id.category_tablayout);
        mViewPager = findViewById(R.id.viewpager);
        mAdapter = new TabAndViewPageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);

        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setTabTextColors(
                ContextCompat.getColor(this, android.R.color.black),
                ContextCompat.getColor(this, android.R.color.red));
        mTabLayout.setSelectedTabIndicatorColor(
                ContextCompat.getColor(this, android.R.color.red));
        ViewCompat.setElevation(mTabLayout, 10);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setupTabLayout(AllCategory allCategory) {
        mAdapter.setData(allCategory);
    }

    private void initHomeData() {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                String homeTopResponse = HttpUtil.get(Constants.NEWS_URL_PREFIX + Constants.HOME_TOP_ADDRESS);
                HomeTop homeTop = new HomeTop(homeTopResponse);
                mHandler.sendMessage(mHandler.obtainMessage(MSG_UPDATE_SEARCH_SUGGEST, homeTop.getSearchSuggest()));

                String topCategoryResponse = HttpUtil.get(Constants.NEWS_URL_PREFIX + Constants.TOP_CATEGORY_ADDRESS);
                AllCategory allCategory = new AllCategory(topCategoryResponse);
                mHandler.sendMessage(mHandler.obtainMessage(MSG_UPDATE_TAB_CATEGORY, allCategory));
                return null;
            }
        };
        task.execute(null, null, null);
    }

}
