package com.udbuilder.todaynews.view.item;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.udbuilder.todaynews.R;
import com.udbuilder.todaynews.datamodel.data.ArticleData;
import com.udbuilder.todaynews.util.Dates;
import com.udbuilder.todaynews.util.ImageLoaderEngine;

/**
 * Created by xiongjianbo on 2018/3/9.
 */

public class BigVideoItemView extends BaseItemView {

    private ImageView mBigVideoImage;
    private TextView mVideoDuration;

    public BigVideoItemView(Context context) {
        super(context);
    }

    public BigVideoItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mBigVideoImage = findViewById(R.id.one_video_image);
        mVideoDuration = findViewById(R.id.video_duration);
    }

    @Override
    public void bind(ArticleData article) {
        super.bind(article);

        initTextView();
    }

    private void initTextView() {
        if (mArticleData.getBigImageURL() == null) {
            mBigVideoImage.setVisibility(GONE);
        } else {
            mBigVideoImage.setVisibility(VISIBLE);
//            mBigVideoImage.setImageBitmap(ImageLoaderEngine.INSTANCE.loadImageSync(
//                    mArticleData.getMiddleImageURL(), mArticleData.getMiddleImageWidth(),
//                    mArticleData.getMiddleImageHeight()));
            setImage();
        }
    }

    private void setImage() {
        new AsyncTask<Void, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(Void... voids) {
                return ImageLoaderEngine.INSTANCE.loadImageSync(mArticleData.getBigImageURL(), false);
            }

            @Override
            protected void onPostExecute(Bitmap aVoid) {
                super.onPostExecute(aVoid);
                if (aVoid != null) {
                    mBigVideoImage.setImageBitmap(aVoid);
                    mVideoDuration.setText(Dates.getFormatDuration(mArticleData.getVideoDuration()));
                }
            }
        }.execute(null, null, null);
    }
}
