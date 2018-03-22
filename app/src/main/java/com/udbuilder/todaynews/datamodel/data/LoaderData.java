package com.udbuilder.todaynews.datamodel.data;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.udbuilder.todaynews.TodayNewsApp;

/**
 * Created by xiongjianbo on 2018/3/20.
 */

public abstract class LoaderData implements LoaderManager.LoaderCallbacks<Cursor> {

    private LoaderManager mLoaderManager;
    private LoaderListener mListener;

    public void init(LoaderManager loaderManager) {
        mLoaderManager = loaderManager;
        mLoaderManager.initLoader(getLoaderId(), null, this);
    }

    public void destroy() {
        if (mLoaderManager != null) {
            mLoaderManager.destroyLoader(getLoaderId());
        }
    }

    public void restartLoader() {
        if (mLoaderManager != null) {
            mLoaderManager.restartLoader(getLoaderId(), null, this);
        }
    }

    public void setLoaderListener(LoaderListener listener) {
        mListener = listener;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(TodayNewsApp.getContext(), getLoaderUri(),
                getLoaderProjections(), null, null, getLoaderSortOrder());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (mListener != null) {
            mListener.onCursorUpdate(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (mListener != null) {
            mListener.onCursorUpdate(null);
        }
    }

    abstract int getLoaderId();

    abstract String[] getLoaderProjections();

    abstract Uri getLoaderUri();

    abstract String getLoaderSortOrder();
}
