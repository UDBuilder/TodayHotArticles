package com.coolpad.www.todaynews.datamodel.data;

import com.coolpad.www.todaynews.util.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by xiongjianbo on 2018/3/6.
 */

public class AllCategory {
    private static final String LABEL_MESSAGE = "message";
    private static final String LABEL_DATA = "data";
    private static final String LABEL_VERSION = "version";
    private static final String LABEL_CATEGORY = "category";
    private static final String LABEL_WEB_URL = "web_url";
    private static final String LABEL_FLAGS = "flags";
    private static final String LABEL_NAME = "name";
    private static final String LABEL_TIP_NEW = "tip_new";
    private static final String LABEL_DEFAULT_ADD = "default_add";
    private static final String LABEL_CONCERN_ID = "concern_id";
    private static final String LABEL_TYPE = "type";
    private static final String LABEL_ICON_URL = "icon_url";

    private ArrayList<CategoryItem> mCategoryList;

    public AllCategory(String jsonStr) {
        parseJson(jsonStr);
    }

    public ArrayList<CategoryItem> getCategoryList() {
        return mCategoryList;
    }

    public int getListSize() {
        return mCategoryList == null ? 0 : mCategoryList.size();
    }

    public CategoryItem getCategoryItem(int index) {
        return (mCategoryList == null || index >= mCategoryList.size()) ? null : mCategoryList.get(index);
    }

    private void parseJson(String str) {
        try {
            JSONObject object = new JSONObject(str);
            if ("success".equals(object.getString(LABEL_MESSAGE))) {
                JSONArray array = object.getJSONObject(LABEL_DATA).getJSONArray(LABEL_DATA);
                if (array != null && array.length() > 0) {
                    mCategoryList = new ArrayList<>(array.length());
                    for (int i = 0; i < array.length(); i++) {
                        mCategoryList.add(new CategoryItem(array.getJSONObject(i)));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (CategoryItem item : mCategoryList) {
            LogUtil.i("AllCategory", "parseJson item=" + item);
        }
    }

}
