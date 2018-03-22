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

public class SmallVideoImageItemView extends BaseItemView {

    private ImageView mSmallVideoImage;
    private TextView mVideoDuration;

    public SmallVideoImageItemView(Context context) {
        super(context);
    }

    public SmallVideoImageItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mSmallVideoImage = findViewById(R.id.one_video_image);
        mVideoDuration = findViewById(R.id.video_duration);
    }

    @Override
    public void bind(ArticleData article) {
        super.bind(article);

        initTextView();
    }

    private void initTextView() {
        if (mArticleData.getMiddleImageURL() == null) {
            mSmallVideoImage.setVisibility(GONE);
        } else {
            mSmallVideoImage.setVisibility(VISIBLE);
//            mSmallVideoImage.setImageBitmap(ImageLoaderEngine.INSTANCE.loadImageSync(
//                    mArticleData.getMiddleImageURL(), mArticleData.getMiddleImageWidth(),
//                    mArticleData.getMiddleImageHeight()));
            setImage();
        }
    }

    private void setImage() {
        new AsyncTask<Void, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(Void... voids) {
                return ImageLoaderEngine.INSTANCE.loadImageSync(mArticleData.getMiddleImageURL());
            }

            @Override
            protected void onPostExecute(Bitmap aVoid) {
                super.onPostExecute(aVoid);
                if (aVoid != null) {
                    mSmallVideoImage.setImageBitmap(aVoid);
                    mVideoDuration.setText(Dates.getFormatDuration(mArticleData.getVideoDuration()));
                }
            }
        }.execute(null, null, null);
    }

}
