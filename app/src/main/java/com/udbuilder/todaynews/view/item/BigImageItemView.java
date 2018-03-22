package com.udbuilder.todaynews.view.item;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.udbuilder.todaynews.R;
import com.udbuilder.todaynews.datamodel.data.ArticleData;
import com.udbuilder.todaynews.util.ImageLoaderEngine;

/**
 * Created by xiongjianbo on 2018/3/9.
 */

public class BigImageItemView extends BaseItemView {

    private ImageView mBigImage;

    public BigImageItemView(Context context) {
        super(context);
    }

    public BigImageItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mBigImage = findViewById(R.id.one_big_image);
    }

    @Override
    public void bind(ArticleData article) {
        super.bind(article);

        initTextView();
    }

    private void initTextView() {
        if (mArticleData.getBigImageURL() == null) {
            mBigImage.setVisibility(GONE);
        } else {
            mBigImage.setVisibility(VISIBLE);
//            mBigImage.setImageBitmap(ImageLoaderEngine.INSTANCE.loadImageSync(
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
                    mBigImage.setImageBitmap(aVoid);
                }
            }
        }.execute(null, null, null);
    }
}
