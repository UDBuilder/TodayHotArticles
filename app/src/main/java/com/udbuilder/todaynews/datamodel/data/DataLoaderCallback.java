package com.udbuilder.todaynews.datamodel.data;

import android.support.v4.app.LoaderManager;

/**
 * Created by xiongjianbo on 2018/3/19.
 */

public interface DataLoaderCallback extends LoaderManager.LoaderCallbacks<android.database.Cursor> {
    void init(LoaderManager loaderManager);
    void destroy();
}
