package com.udbuilder.todaynews.view;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.udbuilder.todaynews.R;
import com.udbuilder.todaynews.datamodel.TransactionService;
import com.udbuilder.todaynews.datamodel.data.CategoryData;
import com.udbuilder.todaynews.datamodel.data.LoaderListener;
import com.udbuilder.todaynews.util.LogUtil;

/**
 * Created by xiongjianbo on 2018/3/7.
 */

public class NewsFragment extends Fragment implements LoaderListener {

    private static final String TAG = "NewsFragment";

    private CategoryData mCategoryData;
    private PullToRefreshRecyclerView mRecyclerView;
    private NewsRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private boolean mLoadMore = false;
    private boolean mRefresh = false;
    private boolean mFirstLoad = true;

    private static final String CATEGORY_ITEM = "category";

    public static NewsFragment newInstance(CategoryData item) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(CATEGORY_ITEM, item);
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCategoryData = getArguments().getParcelable(CATEGORY_ITEM);
        mCategoryData.setLoaderListener(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategoryData.init(getLoaderManager());
        mAdapter = new NewsRecyclerViewAdapter(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news_fragment, container, false);
        mRecyclerView = rootView.findViewById(R.id.recycler_view);

        mLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        mLayoutManager.setStackFromEnd(false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setPullRefreshEnabled(true);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setPullToRefreshListener(new PullToRefreshListener() {
            @Override
            public void onRefresh() {
                LogUtil.d(TAG, "onRefresh ");
                mRefresh = true;
                mCategoryData.doLoadRefresh();
                loadRemoteData();
            }

            @Override
            public void onLoadMore() {
                LogUtil.d(TAG, "onLoadMore ");
                loadMoreLocaleData();
            }
        });
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(getContext().getDrawable(R.drawable.list_divider));
        mRecyclerView.addItemDecoration(divider);
        mRecyclerView.setAdapter(mAdapter);
//        initArticleListData();
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCategoryData.destroy();
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onCursorUpdate(final Cursor cursor) {
        LogUtil.d(TAG, "onCursorUpdate cursor size=" + (cursor == null ? 0 : cursor.getCount()));
//        mAdapter.swapCursor(cursor);
        if (mLoadMore) {
            mLoadMore = false;
            mRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRecyclerView.setLoadMoreComplete();
                    mAdapter.addData(cursor, true);
                }
            }, 500);
        } else if (mRefresh) {
            mRefresh = false;
            mRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRecyclerView.setRefreshComplete();
                    mAdapter.addData(cursor, false);
                }
            }, 500);
        } else {
            if (mFirstLoad && (cursor == null || cursor.getCount() == 0)) {
                mFirstLoad = false;
                loadRemoteData();
            } else {
                mAdapter.setData(cursor);
            }
        }
    }

    private void loadMoreLocaleData() {
        mLoadMore = true;
        mCategoryData.doLoadRefresh();
    }

    public void refresh() {
        if (mAdapter.getItemCount() > 0) {
            mRecyclerView.smoothScrollToPosition(0);
        }
        mRecyclerView.onRefresh();
    }

    private void loadRemoteData() {
        mCategoryData.setLastRefreshTime(System.currentTimeMillis());
        TransactionService.startGrabArticleList(getContext(), mCategoryData);
    }
}
