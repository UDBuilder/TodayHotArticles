package com.coolpad.www.todaynews.datamodel.data;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xiongjianbo on 2018/3/7.
 */


public class CategoryItem implements Parcelable {

    private String mCategory;
    private String mName;
    private long mLastFreshTime = 0;

    private static final String LABEL_CATEGORY = "category";
    private static final String LABEL_NAME = "name";

    CategoryItem(JSONObject jsonObject) throws JSONException {
        mCategory = jsonObject.getString(LABEL_CATEGORY);
        mName = jsonObject.getString(LABEL_NAME);
    }

    protected CategoryItem(Parcel in) {
        mCategory = in.readString();
        mName = in.readString();
    }

    public void setLastFreshTime(long time) {
        mLastFreshTime = time;
    }

    public long getLastFreshTime() {
        return mLastFreshTime;
    }

    public static final Creator<CategoryItem> CREATOR = new Creator<CategoryItem>() {
        @Override
        public CategoryItem createFromParcel(Parcel in) {
            return new CategoryItem(in);
        }

        @Override
        public CategoryItem[] newArray(int size) {
            return new CategoryItem[size];
        }
    };

    @Override
    public String toString() {
        return "CategoryItem{" +
                "mCategory='" + mCategory + '\'' +
                ", mName='" + mName + '\'' +
                ", mLastFreshTime=" + mLastFreshTime +
                '}';
    }

    public String getCategory() {
        return mCategory;
    }

    public String getName() {
        return mName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mCategory);
        dest.writeString(mName);
    }

}
