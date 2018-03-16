package com.coolpad.www.todaynews.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coolpad.www.todaynews.R;
import com.coolpad.www.todaynews.datamodel.data.Category;
import com.coolpad.www.todaynews.datamodel.data.CategoryItem;
import com.coolpad.www.todaynews.util.HttpUtil;
import com.coolpad.www.todaynews.util.LogUtil;

/**
 * Created by xiongjianbo on 2018/3/7.
 */

public class NewsFragment extends Fragment {

    private static final String TAG = "NewsFragment";

    private CategoryItem mCategoryItem;
    private RecyclerView mRecyclerView;
    private NewsRecyclerViewAdapter mAdapter;

    private static final String CATEGORY_ITEM = "category";

    public static NewsFragment newInstance(CategoryItem item) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(CATEGORY_ITEM, item);
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategoryItem = getArguments().getParcelable(CATEGORY_ITEM);
        mAdapter = new NewsRecyclerViewAdapter(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news_fragment, container, false);
        mRecyclerView = rootView.findViewById(R.id.recycler_view);

        final LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setStackFromEnd(false);
        mRecyclerView.setLayoutManager(manager);
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(getContext().getDrawable(R.drawable.list_divider));
        mRecyclerView.addItemDecoration(divider);
        mRecyclerView.setAdapter(mAdapter);
        initArticleListData();
        return rootView;
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onResume() {
//        if (getUserVisibleHint()) {
//            initArticleListData();
//        }
        super.onResume();
    }

    private void initArticleListData() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                String address = HttpUtil.getFormatAddress(mCategoryItem);
                LogUtil.i(TAG, "doInBackground address=" + address);
                String response = HttpUtil.get(address);
                Category category = new Category(response);
                mHandler.sendMessage(mHandler.obtainMessage(1, category));
                return null;
            }
        }.execute(null, null, null);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mAdapter.setData(((Category) msg.obj).getArticleList());
                    break;
            }
            super.handleMessage(msg);
        }
    };
}
