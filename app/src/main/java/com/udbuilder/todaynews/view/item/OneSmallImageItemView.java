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
 * Created by xiongjianbo on 2018/3/7.
 */

public class OneSmallImageItemView extends BaseItemView {

    private ImageView mOneSmallImage;

    public OneSmallImageItemView(Context context) {
        super(context);
    }

    public OneSmallImageItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mOneSmallImage = findViewById(R.id.one_small_image);
    }

    @Override
    public void bind(ArticleData article) {
        super.bind(article);

        initTextView();
    }

    private void initTextView() {
        if (mArticleData.getMiddleImageURL() == null) {
            mOneSmallImage.setVisibility(GONE);
        } else {
            mOneSmallImage.setVisibility(VISIBLE);
//            mOneSmallImage.setImageBitmap(ImageLoaderEngine.INSTANCE.loadImageSync(
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
                    mOneSmallImage.setImageBitmap(aVoid);
                }
            }
        }.execute(null, null, null);
    }
}
