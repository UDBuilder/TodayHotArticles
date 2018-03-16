package com.coolpad.www.todaynews.view.item;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.coolpad.www.todaynews.R;
import com.coolpad.www.todaynews.datamodel.data.Article;
import com.coolpad.www.todaynews.util.Dates;
import com.coolpad.www.todaynews.util.ImageLoaderEngine;

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
    public void bind(Article article) {
        super.bind(article);

        initTextView();
    }

    private void initTextView() {
        if (mArticle.getMiddleImageURL() == null) {
            mSmallVideoImage.setVisibility(GONE);
        } else {
            mSmallVideoImage.setVisibility(VISIBLE);
//            mSmallVideoImage.setImageBitmap(ImageLoaderEngine.INSTANCE.loadImageSync(
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
                    mSmallVideoImage.setImageBitmap(aVoid);
                    mVideoDuration.setText(Dates.getFormatDuration(mArticle.getVideoDuration()));
                }
            }
        }.execute(null, null, null);
    }

}
