package com.udbuilder.todaynews.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;

import com.udbuilder.todaynews.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by xiongjianbo on 2018/3/8.
 */

public enum ImageLoaderEngine {
    INSTANCE;

    private static final String IMAGE_CACHE_DIR = "/todaynews/image";

    private DisplayImageOptions mImageOptions;

    private ImageLoader mImageLoader;

    private ImageSize mListItemSize;

    private Context mAppContext;

    private ImageSize mSmallImageSize;

    private ImageSize mBigImageSize;

    public void initImageLoader(Context context) {
        if (mImageLoader == null) {
            mImageLoader = ImageLoader.getInstance();
        }

        mImageLoader.init(buildImageLoaderConfiguration(context));
//        int height = context.getResources().getDimensionPixelSize(R.dimen.contact_icon_view_normal_size);
//        int width = height;
//        mListItemSize = new ImageSize(height, width);

        mAppContext = context.getApplicationContext();

        initImageSize(mAppContext);
    }

    private void initImageSize(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int bigWidth = screenWidth -
                context.getResources().getDimensionPixelSize(R.dimen.recycler_view_padding) * 2;
        mBigImageSize = new ImageSize(bigWidth, DensityUtil.computeBigHeight(bigWidth));
        int smallWidth = (bigWidth -
                context.getResources().getDimensionPixelSize(R.dimen.image_padding) * 2) / 3;
        mSmallImageSize = new ImageSize(smallWidth, DensityUtil.computeSmallHeight(smallWidth));
        LogUtil.d("ImageLoaderEngine", "initImageSize mBigImageSize="
                + mBigImageSize + " mSmallImageSize=" + mSmallImageSize);
    }


    /**
     * 获取小图片
     */
    public Bitmap loadImageSync(String url) {
        // mImageLoader.denyNetworkDownloads(!SettingHolder.isNetworkSwitchOn());
        return loadImageSync(url, true);
    }

    /**
     * 获取大图片或者小图片
     */
    public Bitmap loadImageSync(String url, boolean isSmall) {
        // mImageLoader.denyNetworkDownloads(!SettingHolder.isNetworkSwitchOn());
        return mImageLoader.loadImageSync(url, isSmall ? mSmallImageSize : mBigImageSize, getImageOptions());
    }

    private ImageLoaderConfiguration buildImageLoaderConfiguration(Context context) {
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, IMAGE_CACHE_DIR);
        return new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .writeDebugLogs()
                .build();
    }

    private DisplayImageOptions getImageOptions() {
        if (mImageOptions == null) {
            mImageOptions = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .resetViewBeforeLoading(true)
                    .build();
        }
        return mImageOptions;
    }
}
