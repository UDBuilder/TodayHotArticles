package com.udbuilder.todaynews;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.udbuilder.todaynews.R;
import com.udbuilder.todaynews.datamodel.TransactionService;
import com.udbuilder.todaynews.datamodel.data.CategoryListData;
import com.udbuilder.todaynews.datamodel.data.Constants;
import com.udbuilder.todaynews.datamodel.data.HomeTop;
import com.udbuilder.todaynews.datamodel.data.LoaderListener;
import com.udbuilder.todaynews.util.HttpUtil;
import com.udbuilder.todaynews.util.LogUtil;
import com.udbuilder.todaynews.view.NewsFragment;
import com.udbuilder.todaynews.view.TabAndViewPageAdapter;

public class MainActivity extends AppCompatActivity implements LoaderListener {

    private static final String TAG = "MainActivity";

    private EditText mSearch;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private TabAndViewPageAdapter mAdapter;

    private boolean isFirstLoad = true;

    private CategoryListData mCategoryListData;

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
                    setupTabLayout((CategoryListData) msg.obj);
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
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LogUtil.d(TAG, "onTabSelected " + tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                LogUtil.d(TAG, "onTabUnselected " + tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                LogUtil.d(TAG, "onTabReselected " + tab);
                if (getSupportFragmentManager().getFragments().size() > 0) {
                    int position = mTabLayout.getSelectedTabPosition();
                    Fragment fragment = getSupportFragmentManager().getFragments().get(position);
                    ((NewsFragment) fragment).refresh();
                }
            }
        });
    }

    private void setupTabLayout(CategoryListData categoryListData) {
//        mAdapter.setData(categoryListData);
    }

    private void initHomeData() {

        mCategoryListData = new CategoryListData();
        mCategoryListData.setLoaderListener(this);
        mCategoryListData.init(getSupportLoaderManager());

//        TransactionService.startGrabCategoryList(this);

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                String homeTopResponse = HttpUtil.get(Constants.NEWS_URL_PREFIX + Constants.HOME_TOP_ADDRESS);
                HomeTop homeTop = new HomeTop(homeTopResponse);
                mHandler.sendMessage(mHandler.obtainMessage(MSG_UPDATE_SEARCH_SUGGEST, homeTop.getSearchSuggest()));

//                String topCategoryResponse = HttpUtil.get(Constants.NEWS_URL_PREFIX + Constants.TOP_CATEGORY_ADDRESS);
//                CategoryListData categoryListData = new CategoryListData(topCategoryResponse);
//                mHandler.sendMessage(mHandler.obtainMessage(MSG_UPDATE_TAB_CATEGORY, categoryListData));
                return null;
            }
        };
        task.execute(null, null, null);
    }

    @Override
    protected void onStop() {
        super.onStop();
        isFirstLoad = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCategoryListData.destroy();
        LogUtil.d(TAG, "onDestroy ");
    }

    @Override
    public void onCursorUpdate(Cursor cursor) {
        if (isFirstLoad && (cursor == null || cursor.getCount() == 0)) {
            refresh();
            isFirstLoad = false;
        } else {
            mAdapter.setData(cursor);
        }
    }

    private void refresh() {
        TransactionService.startGrabCategoryList(this);
    }
}
