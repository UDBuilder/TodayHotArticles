package com.udbuilder.todaynews.datamodel.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xiongjianbo on 2018/3/6.
 */

public class HomeTop {
    private static final String LABEL_DATA = "data";
    private static final String LABEL_MESSAGE = "message";
    private static final String LABEL_CALL_PER_REFRESH = "call_per_refresh";
    private static final String LABEL_HOMEPAGE_SEARCH_SUGGEST = "homepage_search_suggest";

    private String mSearchSuggest;

    public HomeTop(String jsonStr) {
        try {
            parseJson(new JSONObject(jsonStr));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseJson(JSONObject jsonObject) throws JSONException {
        JSONObject obj = jsonObject.getJSONObject(LABEL_DATA);
        mSearchSuggest = obj.getString(LABEL_HOMEPAGE_SEARCH_SUGGEST);
    }

    public String getSearchSuggest() {
        return mSearchSuggest;
    }
}
