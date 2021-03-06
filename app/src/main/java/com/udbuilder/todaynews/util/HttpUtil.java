package com.udbuilder.todaynews.util;

import com.udbuilder.todaynews.datamodel.data.CategoryData;
import com.udbuilder.todaynews.datamodel.data.Constants;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

/**
 * Created by xiongjianbo on 2018/3/6.
 */

public class HttpUtil {
    public static String get(String address) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(address).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);// 一行行的读取内容并追加到builder中去
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        } finally {
            if (connection != null) {
                connection.disconnect();//连接不为空就关闭连接
            }
        }
    }

    public static String getFormatAddress(CategoryData item) {
        long currentTime = System.currentTimeMillis();
        long lastTime = item.getLastRefreshTime();
        return Constants.NEWS_URL_PREFIX + String.format(Locale.CHINA,
                Constants.NEWS_HOT_CATEGORY_ADDRESS, item.getCategory(),
                30, lastTime, currentTime);
    }
}
