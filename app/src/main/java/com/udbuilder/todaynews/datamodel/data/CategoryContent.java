package com.udbuilder.todaynews.datamodel.data;

import com.udbuilder.todaynews.util.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by xiongjianbo on 2018/3/6.
 */

public class CategoryContent {

    private static final String TAG = "CategoryContent";

    private static final String LABEL_MESSAGE = "message";
    private static final String LABEL_TOTAL_NUMBER = "total_number";
    private static final String LABEL_HAS_MORE = "has_more";
    private static final String LABEL_LOGIN_STATUS = "login_status";
    private static final String LABEL_SHOW_ET_STATUS = "show_et_status";
    private static final String LABEL_POST_CONTENT_HINT = "post_content_hint";
    private static final String LABEL_HAS_MORE_TO_REFRESH = "has_more_to_refresh";
    private static final String LABEL_ACTION_TO_LAST_STICK = "action_to_last_stick";
    private static final String LABEL_FEED_FLAG = "feed_flag";
    private static final String LABEL_TIPS = "tips";
    private static final String LABEL_DATA = "data";
    private static final String LABEL_CONTENT = "content";
    private static final String LABEL_CODE = "code";

    private Tips mTips = null;
    private boolean mHasMore = false;
    private boolean mHasMoreToRefresh = false;
    private int mTotalNumber = 0;
    private String mMessage = "failed";
    private ArrayList<ArticleData> mArticleDataList;

    public CategoryContent(String str) {
        parseJson(str);
    }

    public ArrayList<ArticleData> getArticleList() {
        return mArticleDataList;
    }

    private void parseJson(String str) {
        JSONObject obj = null;
        try {
            obj = new JSONObject(str);
        } catch (JSONException e) {
            LogUtil.e(TAG, "parseJson Exception: ", e);
        }
        if (obj != null) {
            try {
                mMessage = obj.getString(LABEL_MESSAGE);
                if (mMessage != null && "success".equals(mMessage.toLowerCase())) {
                    mTotalNumber = obj.getInt(LABEL_TOTAL_NUMBER);
                    mHasMore = obj.getBoolean(LABEL_HAS_MORE);
                    mHasMoreToRefresh = obj.getBoolean(LABEL_HAS_MORE_TO_REFRESH);
                    mTips = new Tips(obj.getJSONObject(LABEL_TIPS));
                    JSONArray jsonArray = obj.getJSONArray(LABEL_DATA);
                    mArticleDataList = new ArrayList<>(jsonArray.length());
                    LogUtil.d(TAG, "parseJson mTotalNumber=" + mTotalNumber
                            + " jsonArray.length=" + jsonArray.length());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        mArticleDataList.add(new ArticleData(jsonArray.getJSONObject(i)));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public class Tips {
        private static final String LABEL_TYPE = "type";
        private static final String LABEL_DISPLAY_DURATION = "display_duration";
        private static final String LABEL_DISPLAY_INFO = "display_info";
        private static final String LABEL_DISPLAY_TEMPLATE = "display_template";
        private static final String LABEL_OPEN_URL = "open_url";
        private static final String LABEL_WEB_URL = "web_url";
        private static final String LABEL_DOWNLOAD_URL = "download_url";
        private static final String LABEL_APP_NAME = "app_name";
        private static final String LABEL_PACKAGE_NAME = "package_name";

        private String mType = null;
        private int mDisplayDuration = 0;
        private String mDisplayInfo = null;
        private String mDisplayTemplate = null;

        Tips(JSONObject jsonObject) throws JSONException {
            mType = jsonObject.getString(LABEL_TYPE);
            mDisplayDuration = jsonObject.getInt(LABEL_DISPLAY_DURATION);
            mDisplayInfo = jsonObject.getString(LABEL_DISPLAY_INFO);
            if (jsonObject.has(LABEL_DISPLAY_TEMPLATE)) {
                mDisplayTemplate = jsonObject.getString(LABEL_DISPLAY_TEMPLATE);
            }
        }
    }

}
