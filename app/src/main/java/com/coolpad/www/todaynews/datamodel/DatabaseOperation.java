package com.coolpad.www.todaynews.datamodel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.coolpad.www.todaynews.datamodel.data.Article;

/**
 * Created by xiongjianbo on 2018/3/12.
 */

public class DatabaseOperation {

    public static void insertArticleToDB(Context context, Article article) {
        SQLiteDatabase db = DatabaseHelper.getDatabase(context);
        db.insert()
    }

}
