package com.udbuilder.todaynews.view.item;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.udbuilder.todaynews.R;
import com.udbuilder.todaynews.datamodel.data.ArticleData;
import com.udbuilder.todaynews.util.Dates;

/**
 * Created by xiongjianbo on 2018/3/8.
 */

public class BaseItemView extends RelativeLayout {

    public static final int SMALL_IMAGE_WIDTH = 315;
    public static final int SMALL_IMAGE_HEIGHT = 200;

    protected TextView mTitle;
    protected TextView mLabelSource;
    protected TextView mLabelCommentCount;
    protected TextView mLabelTime;

    protected ArticleData mArticleData;


    public BaseItemView(Context context) {
        this(context, null);
    }

    public BaseItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTitle = findViewById(R.id.title);
        mLabelSource = findViewById(R.id.label_source);
        mLabelCommentCount = findViewById(R.id.label_comment_count);
        mLabelTime = findViewById(R.id.label_time);
    }

    public void bind(ArticleData article) {
        mArticleData = article;
        mTitle.setText(mArticleData.getTitle());
        mLabelCommentCount.setText(String.format(mContext.getString(R.string.comment_count), mArticleData.getCommentCount()));
        if (mArticleData.getPublishTime() == 0) {
            mLabelTime.setVisibility(GONE);
        } else {
            mLabelTime.setVisibility(VISIBLE);
            mLabelTime.setText(Dates.getRelativeTimeSpanString(mArticleData.getBehotTime() * 1000));
        }
        if (TextUtils.isEmpty(mArticleData.getLabelSource())) {
            mLabelSource.setVisibility(GONE);
        } else {
            mLabelSource.setVisibility(VISIBLE);
            mLabelSource.setText(mArticleData.getLabelSource());
        }
    }
}
