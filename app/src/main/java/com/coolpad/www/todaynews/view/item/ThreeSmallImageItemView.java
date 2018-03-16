package com.coolpad.www.todaynews.view.item;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.coolpad.www.todaynews.R;
import com.coolpad.www.todaynews.datamodel.data.Article;
import com.coolpad.www.todaynews.util.ImageLoaderEngine;

/**
 * Created by xiongjianbo on 2018/3/8.
 */

public class ThreeSmallImageItemView extends BaseItemView {

    private ImageView mImageOne;
    private ImageView mImageTwo;
    private ImageView mImageThree;
    private ViewGroup mImageRoot;

    public ThreeSmallImageItemView(Context context) {
        super(context);
    }

    public ThreeSmallImageItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mImageRoot = findViewById(R.id.images);
        mImageOne = findViewById(R.id.image_one);
        mImageTwo = findViewById(R.id.image_two);
        mImageThree = findViewById(R.id.image_three);

    }

    @Override
    public void bind(Article article) {
        super.bind(article);

        initTextView();
    }

    private void initTextView() {
        if (mArticle.getArticleType() != Article.TYPE_THREE_IMAGE) {
            mImageRoot.setVisibility(GONE);
        } else {
            mImageRoot.setVisibility(VISIBLE);
//            mOneSmallImage.setImageBitmap(ImageLoaderEngine.INSTANCE.loadImageSync(
//                    mArticle.getMiddleImageURL(), mArticle.getMiddleImageWidth(),
//                    mArticle.getMiddleImageHeight()));
            setImage();
        }
    }

    private void setImage() {
        new AsyncTask<Void, Void, Bitmap[]>() {

            @Override
            protected Bitmap[] doInBackground(Void... voids) {
                String[] urls = mArticle.getImageUrlList();
                Bitmap[] bitmaps = new Bitmap[3];
                for (int i = 0; i < 3; i++) {
                    bitmaps[i] = ImageLoaderEngine.INSTANCE.loadImageSync(urls[i]);
                }
                return bitmaps;
            }

            @Override
            protected void onPostExecute(Bitmap[] aVoid) {
                super.onPostExecute(aVoid);
                if (aVoid != null && aVoid.length == 3) {
                    mImageOne.setImageBitmap(aVoid[0]);
                    mImageTwo.setImageBitmap(aVoid[1]);
                    mImageThree.setImageBitmap(aVoid[2]);
                }
            }
        }.execute(null, null, null);
    }
}
