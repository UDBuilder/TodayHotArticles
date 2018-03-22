package com.udbuilder.todaynews.datamodel.data;

import android.database.Cursor;

/**
 * Created by xiongjianbo on 2018/3/20.
 */

public interface LoaderListener {
    void onCursorUpdate(Cursor cursor);
}
