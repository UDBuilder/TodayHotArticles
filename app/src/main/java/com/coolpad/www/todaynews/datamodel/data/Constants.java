package com.coolpad.www.todaynews.datamodel.data;

/**
 * Created by xiongjianbo on 2018/3/6.
 */

public class Constants {

    public static final String NEWS_URL_PREFIX = "http://is.snssdk.com";

    public static final String HOME_TOP_ADDRESS = "/search/suggest/homepage_suggest/?";

    public static final String TOP_CATEGORY_ADDRESS = "/article/category/get_subscribed/v1/?";

    public static final String NEWS_HOT_CATEGORY_ADDRESS = "/api/news/feed/v79/?" +
            "category=%s" +
            "&refer=1" +
            "&count=%d" +
            "&min_behot_time=%d" +
            "&loc_time=%d" +
            "&iid=0123456789";
}
