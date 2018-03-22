package com.udbuilder.todaynews.datamodel;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.udbuilder.todaynews.TodayNewsApp;
import com.udbuilder.todaynews.util.LogUtil;

/**
 * Created by xiongjianbo on 2018/3/9.
 */

public class ArticleContentProvider extends ContentProvider {

    private static final String TAG = "ArticleContentProvider";

    public static final String AUTHORITY = "com.udbuilder.todaynews.datamodel.ArticleContentProvider";
    private static final String CONTENT_AUTHORITY = "content://" + AUTHORITY + '/';

    private static final String ARTICLE_LIST_QUERY = "articles";
    private static final String CATEGORY_LIST_QUERY = "categorys";

    private static final int ARTICLE_LIST_QUERY_CODE = 1;
    private static final int CATEGORY_LIST_QUERY_CODE = 2;

    public static final Uri ARTICLE_LIST_URI = Uri.parse(CONTENT_AUTHORITY + ARTICLE_LIST_QUERY);
    public static final Uri CATEGORY_LIST_URI = Uri.parse(CONTENT_AUTHORITY + CATEGORY_LIST_QUERY);

    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, ARTICLE_LIST_QUERY + "/*", ARTICLE_LIST_QUERY_CODE);
        sURIMatcher.addURI(AUTHORITY, CATEGORY_LIST_QUERY, CATEGORY_LIST_QUERY_CODE);
    }

    @Override
    public boolean onCreate() {
        mDatabaseHelper = DatabaseHelper.getInstance(getContext());
        mDatabase = mDatabaseHelper.getWritableDatabase();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        String[] queryArgs = selectionArgs;
        String groupBy = null;
        String limit = null;

        final int match = sURIMatcher.match(uri);
        switch (match) {
            case ARTICLE_LIST_QUERY_CODE:
                queryBuilder.setTables(DatabaseHelper.TABLE_ARTICLE);
                if (uri.getPathSegments().size() == 2) {
                    String category = uri.getPathSegments().get(1);
                    queryBuilder.appendWhere(DatabaseHelper.ArticleColumns.CATEGORY
                            + "='" + category + "'");
                    limit = uri.getQueryParameter("from") + "," + uri.getQueryParameter("to");
                    LogUtil.d(TAG, "query category=" + category + " limit=" + limit + " uri=" + uri);
                } else {
                    throw new IllegalArgumentException("Malformed URI " + uri);
                }
                break;
            case CATEGORY_LIST_QUERY_CODE:
                queryBuilder.setTables(DatabaseHelper.TABLE_CATEGORY);
                break;
            default: {
                throw new IllegalArgumentException("Unknown URI " + uri);
            }
        }
        final Cursor cursor = queryBuilder.query(mDatabase, projection, selection, selectionArgs,
                groupBy, null, sortOrder, limit);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        return 0;
    }

    public static void notifyArticleListChanged() {
        notifyUriChanged(ARTICLE_LIST_URI);
    }

    public static void notifyCategoryListChanged() {
        notifyUriChanged(CATEGORY_LIST_URI);
    }


    private static void notifyUriChanged(Uri uri) {
        Context context = TodayNewsApp.getContext();
        final ContentResolver cr = context.getContentResolver();
        cr.notifyChange(uri, null);
    }
}
