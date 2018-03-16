package com.coolpad.www.todaynews.view.item;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coolpad.www.todaynews.R;
import com.coolpad.www.todaynews.datamodel.data.Article;
import com.coolpad.www.todaynews.util.Dates;

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

    protected Article mArticle;


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

    public void bind(Article article) {
        mArticle = article;
        mTitle.setText(mArticle.getTitle());
        mLabelCommentCount.setText(String.format(mContext.getString(R.string.comment_count), mArticle.getCommentCount()));
        if (mArticle.getPublishTime() == 0) {
            mLabelTime.setVisibility(GONE);
        } else {
            mLabelTime.setVisibility(VISIBLE);
            mLabelTime.setText(Dates.getRelativeTimeSpanString(mArticle.getBehotTime() * 1000));
        }
        if (TextUtils.isEmpty(mArticle.getLabelSource())) {
            mLabelSource.setVisibility(GONE);
        } else {
            mLabelSource.setVisibility(VISIBLE);
            mLabelSource.setText(mArticle.getLabelSource());
        }
    }
}
