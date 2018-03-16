package com.coolpad.www.todaynews.view.item;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.coolpad.www.todaynews.R;
import com.coolpad.www.todaynews.datamodel.data.Article;
import com.coolpad.www.todaynews.util.ImageLoaderEngine;

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
    public void bind(Article article) {
        super.bind(article);

        initTextView();
    }

    private void initTextView() {
        if (mArticle.getMiddleImageURL() == null) {
            mOneSmallImage.setVisibility(GONE);
        } else {
            mOneSmallImage.setVisibility(VISIBLE);
//            mOneSmallImage.setImageBitmap(ImageLoaderEngine.INSTANCE.loadImageSync(
//                    mArticle.getMiddleImageURL(), mArticle.getMiddleImageWidth(),
//                    mArticle.getMiddleImageHeight()));
            setImage();
        }
    }

    private void setImage() {
        new AsyncTask<Void, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(Void... voids) {
                return ImageLoaderEngine.INSTANCE.loadImageSync(mArticle.getMiddleImageURL());
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
