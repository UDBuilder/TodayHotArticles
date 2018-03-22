package com.udbuilder.todaynews.datamodel.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.udbuilder.todaynews.datamodel.ArticleContentProvider;
import com.udbuilder.todaynews.datamodel.DatabaseHelper.*;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xiongjianbo on 2018/3/7.
 */


public class CategoryData extends LoaderData implements Parcelable {

    private String mCategory;
    private String mName;
    private long mLastRefreshTime = 0;
    private long mLastLoadMoreTime = 0;
    private long mTopTime = 0;
    private long mBottomTime = 0;
    private int mPageIndex = 0;

    private static final String LABEL_CATEGORY = "category";
    private static final String LABEL_NAME = "name";
    private static final String SORT_BY = ArticleColumns.BEHOT_TIME + " DESC";
    private static final int EACH_PAGE_CAPACITY = 15;

    public static final String[] PROJECTIONS = {
            CategoryColumns._ID,
            CategoryColumns.CATEGORY,
            CategoryColumns.NAME,
            CategoryColumns.LAST_REFRESH_TIME,
            CategoryColumns.LAST_LOADMORE_TIME,
            CategoryColumns.TOP_TIME,
            CategoryColumns.BOTTOM_TIME,
    };

    public CategoryData(JSONObject jsonObject) throws JSONException {
        mCategory = jsonObject.getString(LABEL_CATEGORY);
        mName = jsonObject.getString(LABEL_NAME);
        mLastRefreshTime = 0;
        mLastLoadMoreTime = System.currentTimeMillis();
        mTopTime = 0;
        mBottomTime = 0;
    }

    public CategoryData(Cursor cursor) {
        mCategory = cursor.getString(cursor.getColumnIndex(CategoryColumns.CATEGORY));
        mName = cursor.getString(cursor.getColumnIndex(CategoryColumns.NAME));
        mLastRefreshTime = cursor.getLong(cursor.getColumnIndex(CategoryColumns.LAST_REFRESH_TIME));
        mLastLoadMoreTime = cursor.getLong(cursor.getColumnIndex(CategoryColumns.LAST_LOADMORE_TIME));
        mTopTime = cursor.getLong(cursor.getColumnIndex(CategoryColumns.TOP_TIME));
        mBottomTime = cursor.getLong(cursor.getColumnIndex(CategoryColumns.BOTTOM_TIME));
    }

    public void setLastRefreshTime(long time) {
        mLastRefreshTime = time;
    }

    public long getLastRefreshTime() {
        return mLastRefreshTime;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public long getLastLoadMoreTime() {
        return mLastLoadMoreTime;
    }

    public void setLastLoadMoreTime(long mLastLoadMoreTime) {
        this.mLastLoadMoreTime = mLastLoadMoreTime;
    }

    public long getTopTime() {
        return mTopTime;
    }

    public void setTopTime(long mTopTime) {
        this.mTopTime = mTopTime;
    }

    public long getBottomTime() {
        return mBottomTime;
    }

    public void setBottomTime(long mBottomTime) {
        this.mBottomTime = mBottomTime;
    }

    @Override
    int getLoaderId() {
        return Constants.ARTICLE_LIST_LOADER_ID;
    }

    @Override
    String[] getLoaderProjections() {
        return ArticleData.PROJECTIONS;
    }

    @Override
    Uri getLoaderUri() {
        Uri.Builder builder = ArticleContentProvider.ARTICLE_LIST_URI.buildUpon();
        builder.appendPath(mCategory);
        builder.appendQueryParameter("from", /*String.valueOf(mPageIndex * EACH_PAGE_CAPACITY)*/"0");
        builder.appendQueryParameter("to", String.valueOf((mPageIndex + 1) * EACH_PAGE_CAPACITY));
        return builder.build();
    }

    @Override
    String getLoaderSortOrder() {
        return SORT_BY;
    }

    public ContentValues getInsertContentValues() {
        ContentValues values = new ContentValues();
        values.put(CategoryColumns.CATEGORY, mCategory);
        values.put(CategoryColumns.NAME, mName);
        values.put(CategoryColumns.LAST_REFRESH_TIME, mLastRefreshTime);
        values.put(CategoryColumns.LAST_LOADMORE_TIME, mLastLoadMoreTime);
        values.put(CategoryColumns.TOP_TIME, mTopTime);
        values.put(CategoryColumns.BOTTOM_TIME, mBottomTime);
        return values;
    }

    public void doLoadRefresh() {
        mPageIndex++;
        restartLoader();
    }

    private CategoryData(Parcel in) {
        mCategory = in.readString();
        mName = in.readString();
        mLastRefreshTime = in.readLong();
        mLastLoadMoreTime = in.readLong();
        mTopTime = in.readLong();
        mBottomTime = in.readLong();
    }

    public static final Creator<CategoryData> CREATOR = new Creator<CategoryData>() {
        @Override
        public CategoryData createFromParcel(Parcel in) {
            return new CategoryData(in);
        }

        @Override
        public CategoryData[] newArray(int size) {
            return new CategoryData[size];
        }
    };

    @Override
    public String toString() {
        return "CategoryData{" +
                "mCategory='" + mCategory + '\'' +
                ", mName='" + mName + '\'' +
                ", mLastRefreshTime=" + mLastRefreshTime +
                ", mLastLoadMoreTime=" + mLastLoadMoreTime +
                ", mTopTime=" + mTopTime +
                ", mBottomTime=" + mBottomTime +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mCategory);
        dest.writeString(mName);
        dest.writeLong(mLastRefreshTime);
        dest.writeLong(mLastLoadMoreTime);
        dest.writeLong(mTopTime);
        dest.writeLong(mBottomTime);
    }
}