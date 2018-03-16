package com.coolpad.www.todaynews.util;

import android.content.Context;

public class DensityUtil {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据宽度计算高度，保证宽高比为300:190
     */
    public static int computeSmallHeight(int width) {
        return (int) ((width * 190) / 300);
    }
    /**
     * 根据宽度计算高度，保证宽高比为9:5
     */
    public static int computeBigHeight(int width) {
        return (int) ((width * 5) / 9);
    }
}
