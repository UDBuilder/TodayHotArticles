package com.udbuilder.todaynews.datamodel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.udbuilder.todaynews.TodayNewsApp;
import com.udbuilder.todaynews.datamodel.data.ArticleData;
import com.udbuilder.todaynews.datamodel.data.CategoryData;
import com.udbuilder.todaynews.util.LogUtil;

/**
 * Created by xiongjianbo on 2018/3/12.
 */

public class DatabaseOperation {

    private static final String TAG = "DatabaseOperation";

    public static void insertArticle(String category, ArticleData article) {
        Context context = TodayNewsApp.getContext();
        SQLiteDatabase db = DatabaseHelper.getDatabase(context);
        ContentValues values = article.getInsertContentValues();
        values.put(DatabaseHelper.ArticleColumns.CATEGORY, category);
        String where = DatabaseHelper.ArticleColumns.ITEM_ID + "=" + article.getItemId();
        if (db.update(DatabaseHelper.TABLE_ARTICLE, values, where, null) <= 0) {
            long ret = db.insert(DatabaseHelper.TABLE_ARTICLE, null, values);
            LogUtil.d(TAG, "insertArticle insert ret=" + ret);
        } else {
            LogUtil.d(TAG, "insertArticle update success");
        }
    }

    public static void insertOrUpdateCategory(CategoryData category) {
        Context context = TodayNewsApp.getContext();
        SQLiteDatabase db = DatabaseHelper.getDatabase(context);
        ContentValues values = category.getInsertContentValues();
        boolean hasExist = false;
        Cursor cursor = null;
        String where = DatabaseHelper.CategoryColumns.CATEGORY + "='" + category.getCategory() + "'";
//        try {
//            cursor = db.query(DatabaseHelper.TABLE_CATEGORY, null,
//                    where, null, null, null, null);
//            if (cursor != null && cursor.getCount() > 0) {
//                hasExist = true;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//        LogUtil.d(TAG, "insertOrUpdateCategory hasExist=" + hasExist);
//        if (hasExist) {
//            db.update(DatabaseHelper.TABLE_CATEGORY, values, where, null);
//        } else {
//            db.insert(DatabaseHelper.TABLE_CATEGORY, null, values);
//        }

        if (db.update(DatabaseHelper.TABLE_CATEGORY, values, where, null) <= 0) {
            long ret = db.insert(DatabaseHelper.TABLE_CATEGORY, null, values);
            LogUtil.d(TAG, "insertOrUpdateCategory insert ret=" + ret);
        } else {
            LogUtil.d(TAG, "insertOrUpdateCategory update success");
        }

    }

    public static void updateCategory(CategoryData categoryData) {
        LogUtil.d(TAG, "updateCategory categoryData=" + categoryData);
        Context context = TodayNewsApp.getContext();
        SQLiteDatabase db = DatabaseHelper.getDatabase(context);
        String where = DatabaseHelper.CategoryColumns.CATEGORY + "='" + categoryData.getCategory() + "'";
        ContentValues values = categoryData.getInsertContentValues();
        db.update(DatabaseHelper.TABLE_CATEGORY, values, where, null);
    }
}
