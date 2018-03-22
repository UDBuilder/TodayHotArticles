package com.udbuilder.todaynews.datamodel;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.udbuilder.todaynews.datamodel.data.ArticleData;
import com.udbuilder.todaynews.datamodel.data.CategoryData;
import com.udbuilder.todaynews.datamodel.data.CategoryListData;
import com.udbuilder.todaynews.datamodel.data.CategoryContent;
import com.udbuilder.todaynews.datamodel.data.Constants;
import com.udbuilder.todaynews.util.HttpUtil;
import com.udbuilder.todaynews.util.LogUtil;

/**
 * Created by xiongjianbo on 2018/3/19.
 */

public class TransactionService extends IntentService {

    private static final String TAG = "TransactionService";

    public static final String KEY_OP = "option";
    public static final String KEY_CATEGORY_ITEM = "category_item";

    public static final int OP_GET_ARTICLE_LIST = 1;
    public static final int OP_GET_CATEGORY_LIST = 2;

    public TransactionService() {
        super(TAG);
    }

    public static void startGrabArticleList(Context context, CategoryData item) {
        Intent intent = new Intent(context, TransactionService.class);
        intent.putExtra(KEY_CATEGORY_ITEM, item);
        intent.putExtra(KEY_OP, OP_GET_ARTICLE_LIST);
        context.startService(intent);
    }

    public static void startGrabCategoryList(Context context) {
        Intent intent = new Intent(context, TransactionService.class);
        intent.putExtra(KEY_OP, OP_GET_CATEGORY_LIST);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) {
            LogUtil.e(TAG, "onHandleIntent invalid intent == null.");
            return;
        }
        int op = intent.getIntExtra(KEY_OP, -1);
        switch (op) {
            case OP_GET_ARTICLE_LIST:
                grabArticles(intent);
                break;
            case OP_GET_CATEGORY_LIST:
                grabCategorys(intent);
                break;
            default:
                LogUtil.i(TAG, "onHandleIntent invalid option: " + op + " intent=" + intent);
        }
    }

    private void grabArticles(Intent intent) {
        CategoryData item = intent.getParcelableExtra(KEY_CATEGORY_ITEM);
        LogUtil.i(TAG, "getArticles item=" + item);

        DatabaseOperation.updateCategory(item);

        String address = HttpUtil.getFormatAddress(item);
        String response = HttpUtil.get(address);
        CategoryContent categoryContent = new CategoryContent(response);

        long time = System.currentTimeMillis();
        for (ArticleData article : categoryContent.getArticleList()) {
            DatabaseOperation.insertArticle(item.getCategory(), article);
        }
        ArticleContentProvider.notifyArticleListChanged();
        LogUtil.d(TAG, "grabArticles insert " + categoryContent.getArticleList().size()
                + " columns, cost time " + (System.currentTimeMillis() - time));
    }

    private void grabCategorys(Intent intent) {
        String topCategoryResponse = HttpUtil.get(Constants.NEWS_URL_PREFIX + Constants.TOP_CATEGORY_ADDRESS);
        CategoryListData categoryListData = new CategoryListData(topCategoryResponse);

        long time = System.currentTimeMillis();
        for (CategoryData item : categoryListData.getCategoryList()) {
            DatabaseOperation.insertOrUpdateCategory(item);
        }
        ArticleContentProvider.notifyCategoryListChanged();
        LogUtil.d(TAG, "grabCategorys insert " + categoryListData.getCategoryList().size()
                + " columns, cost time " + (System.currentTimeMillis() - time));

    }
}
