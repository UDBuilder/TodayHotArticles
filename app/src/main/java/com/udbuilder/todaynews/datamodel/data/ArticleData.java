package com.udbuilder.todaynews.datamodel.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import com.udbuilder.todaynews.datamodel.DatabaseHelper.ArticleColumns;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by xiongjianbo on 2018/3/6.
 */

public class ArticleData {

    public static final int TYPE_NO_IMAGE = 1;
    public static final int TYPE_ONE_SMALL_IMAGE = 2;
    public static final int TYPE_BIG_IMAGE = 3;
    public static final int TYPE_THREE_IMAGE = 4;
    public static final int TYPE_SMALL_VIDEO = 5;
    public static final int TYPE_BIG_VIDEO = 6;

    public static final String[] PROJECTIONS = {
            ArticleColumns._ID,
            ArticleColumns.ABSTRACT,
//            ArticleColumns.AGGR_TYPE,
//            ArticleColumns.ARTICLE_SUB_TYPE,
//            ArticleColumns.ARTICLE_TYPE,
            ArticleColumns.ARTICLE_URL,
            ArticleColumns.BAN_COMMENT,
            ArticleColumns.BEHOT_TIME,
            ArticleColumns.BURY_COUNT,
            ArticleColumns.COMMENT_COUNT,
            ArticleColumns.DIGG_COUNT,
            ArticleColumns.DISPLAY_URL,
            ArticleColumns.FILTER_WORDS,
            ArticleColumns.FORWARD_INFO,
            ArticleColumns.GROUP_FLAGS,
            ArticleColumns.GROUP_ID,
            ArticleColumns.HAS_M3U8_VIDEO,
            ArticleColumns.HAS_MP4_VIDEO,
            ArticleColumns.HAS_VIDEO,
            ArticleColumns.HAS_IMAGE,
            ArticleColumns.ITEM_ID,
            ArticleColumns.ITEM_VERSION,
            ArticleColumns.LARGE_IMAGE_LIST,
            ArticleColumns.IMAGE_LIST,
            ArticleColumns.LEVEL,
            ArticleColumns.MEDIA_NAME,
            ArticleColumns.MIDDLE_IMAGE,
            ArticleColumns.PUBLISH_TIME,
            ArticleColumns.READ_COUNT,
            ArticleColumns.RID,
            ArticleColumns.SHARE_COUNT,
            ArticleColumns.SHARE_INFO,
            ArticleColumns.SHARE_URL,
            ArticleColumns.SOURCE,
            ArticleColumns.TAG,
            ArticleColumns.TIP,
            ArticleColumns.TITLE,
            ArticleColumns.URL,
            ArticleColumns.USER_INFO,
            ArticleColumns.USER_REPIN,
            ArticleColumns.VIDEO_DETAIL_INFO,
            ArticleColumns.VIDEO_DURATION,
            ArticleColumns.VIDEO_ID,
    };

    private String mAbstract;
    private String mUrl;
    private long mBehotTime;
    private long mPublishTime;
    private int mCommentCount;
    private String mDisplayUrl;
    private boolean mHasVideo;
    private boolean mHasImage;
    private String mTag;
    private String mTagId;
    private String mTitle;
    private ArrayList<ImageInfo> mLargeImageList;
    private ArrayList<ImageInfo> mImageList;
    private ImageInfo mMiddleImageInfo;
    private VideoDetailInfo mVideoDetailInfo;
    private String mMediaName;
    private String mSource;
    private int mVideoDuration;
    private long mItemId;

    private String mLargeImageListJsonStr;
    private String mImageListJsonStr;
    private String mMiddleImageInfoJsonStr;
    private String mVideoDetailJsonStr;


    public ArticleData(JSONObject object) {
        try {
            JSONObject jsonObject = new JSONObject(object.getString("content"));
            mAbstract = jsonObject.has(ArticleColumns.ABSTRACT) ? jsonObject.getString(ArticleColumns.ABSTRACT) : null;
            mUrl = jsonObject.has(ArticleColumns.URL) ? jsonObject.getString(ArticleColumns.URL) : null;
            mBehotTime = jsonObject.has(ArticleColumns.BEHOT_TIME) ? jsonObject.getLong(ArticleColumns.BEHOT_TIME) : 0;
            mItemId = jsonObject.has(ArticleColumns.ITEM_ID) ? jsonObject.getLong(ArticleColumns.ITEM_ID) : 0;
            mCommentCount = jsonObject.has(ArticleColumns.COMMENT_COUNT) ? jsonObject.getInt(ArticleColumns.COMMENT_COUNT) : 0;
            mDisplayUrl = jsonObject.has(ArticleColumns.DISPLAY_URL) ? jsonObject.getString(ArticleColumns.DISPLAY_URL) : null;
            mHasVideo = jsonObject.has(ArticleColumns.HAS_VIDEO) && jsonObject.getBoolean(ArticleColumns.HAS_VIDEO);
            mPublishTime = jsonObject.has(ArticleColumns.PUBLISH_TIME) ? jsonObject.getLong(ArticleColumns.PUBLISH_TIME) : 0;
            mTag = jsonObject.has(ArticleColumns.TAG) ? jsonObject.getString(ArticleColumns.TAG) : null;
            mHasImage = jsonObject.has(ArticleColumns.HAS_IMAGE) && jsonObject.getBoolean(ArticleColumns.HAS_IMAGE);
            mTagId = jsonObject.has(ArticleColumns.TAG_ID) ? jsonObject.getString(ArticleColumns.TAG_ID) : null;
            mTitle = jsonObject.has(ArticleColumns.TITLE) ? jsonObject.getString(ArticleColumns.TITLE) : null;

            JSONObject obj = jsonObject.has(ArticleColumns.VIDEO_DETAIL_INFO) ?
                    jsonObject.getJSONObject(ArticleColumns.VIDEO_DETAIL_INFO) : null;
            if (obj != null) {
                mVideoDetailInfo = new VideoDetailInfo(obj);
                mVideoDetailJsonStr = obj.toString();
            } else {
                mVideoDetailInfo = null;
                mVideoDetailJsonStr = null;
            }

            mMediaName = jsonObject.has(ArticleColumns.MEDIA_NAME) ? jsonObject.getString(ArticleColumns.MEDIA_NAME) : null;
            mSource = jsonObject.has(ArticleColumns.SOURCE) ? jsonObject.getString(ArticleColumns.SOURCE) : null;

            JSONArray jsonArray = jsonObject.has(ArticleColumns.LARGE_IMAGE_LIST) ?
                    jsonObject.getJSONArray(ArticleColumns.LARGE_IMAGE_LIST) : null;
            if (jsonArray != null) {
                mLargeImageList = new ArrayList<>(jsonArray.length());
                for (int i = 0; i < jsonArray.length(); i++) {
                    mLargeImageList.add(new ImageInfo(jsonArray.getJSONObject(i)));
                }
                mLargeImageListJsonStr = jsonArray.toString();
            } else {
                mLargeImageList = null;
                mLargeImageListJsonStr = null;
            }

            jsonArray = jsonObject.has(ArticleColumns.IMAGE_LIST) ?
                    jsonObject.getJSONArray(ArticleColumns.IMAGE_LIST) : null;
            if (jsonArray != null) {
                mImageList = new ArrayList<>(jsonArray.length());
                for (int i = 0; i < jsonArray.length(); i++) {
                    mImageList.add(new ImageInfo(jsonArray.getJSONObject(i)));
                }
                mImageListJsonStr = jsonArray.toString();
            } else {
                mImageList = null;
                mImageListJsonStr = null;
            }

            obj = jsonObject.has(ArticleColumns.MIDDLE_IMAGE) ?
                    jsonObject.getJSONObject(ArticleColumns.MIDDLE_IMAGE) : null;
            if (obj != null) {
                mMiddleImageInfo = new ImageInfo(obj);
                mMiddleImageInfoJsonStr = obj.toString();
            } else {
                mMiddleImageInfo = null;
                mMiddleImageInfoJsonStr = null;
            }

            mVideoDuration = jsonObject.has(ArticleColumns.VIDEO_DURATION) ? jsonObject.getInt(ArticleColumns.VIDEO_DURATION) : 0;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArticleData(Cursor cursor) {
        mAbstract = cursor.getString(cursor.getColumnIndex(ArticleColumns.ABSTRACT));
        mUrl = cursor.getString(cursor.getColumnIndex(ArticleColumns.URL));
        mBehotTime = cursor.getLong(cursor.getColumnIndex(ArticleColumns.BEHOT_TIME));
        mCommentCount = cursor.getInt(cursor.getColumnIndex(ArticleColumns.COMMENT_COUNT));
        mDisplayUrl = cursor.getString(cursor.getColumnIndex(ArticleColumns.DISPLAY_URL));
        mHasVideo = cursor.getInt(cursor.getColumnIndex(ArticleColumns.HAS_VIDEO)) == 1;
        mPublishTime = cursor.getLong(cursor.getColumnIndex(ArticleColumns.PUBLISH_TIME));
        mTag = cursor.getString(cursor.getColumnIndex(ArticleColumns.TAG));
        mHasImage = cursor.getInt(cursor.getColumnIndex(ArticleColumns.HAS_IMAGE)) == 1;
//        mTagId = cursor.getString(cursor.getColumnIndex(ArticleColumns.TAG_ID));
        mTitle = cursor.getString(cursor.getColumnIndex(ArticleColumns.TITLE));
        mMediaName = cursor.getString(cursor.getColumnIndex(ArticleColumns.MEDIA_NAME));
        mSource = cursor.getString(cursor.getColumnIndex(ArticleColumns.SOURCE));
        mVideoDuration = cursor.getInt(cursor.getColumnIndex(ArticleColumns.VIDEO_DURATION));
        mItemId = cursor.getLong(cursor.getColumnIndex(ArticleColumns.ITEM_ID));

        try {
            mLargeImageListJsonStr = cursor.getString(cursor.getColumnIndex(ArticleColumns.LARGE_IMAGE_LIST));
            if (mLargeImageListJsonStr != null) {
                JSONArray jsonArray = new JSONArray(mLargeImageListJsonStr);
                mLargeImageList = new ArrayList<>(jsonArray.length());
                for (int i = 0; i < jsonArray.length(); i++) {
                    mLargeImageList.add(new ImageInfo(jsonArray.getJSONObject(i)));
                }
            } else {
                mLargeImageList = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            mLargeImageList = null;
            mLargeImageListJsonStr = null;
        }

        try {
            mImageListJsonStr = cursor.getString(cursor.getColumnIndex(ArticleColumns.IMAGE_LIST));
            if (mImageListJsonStr != null) {
                JSONArray jsonArray = new JSONArray(mImageListJsonStr);
                mImageList = new ArrayList<>(jsonArray.length());
                for (int i = 0; i < jsonArray.length(); i++) {
                    mImageList.add(new ImageInfo(jsonArray.getJSONObject(i)));
                }
            } else {
                mImageList = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            mImageList = null;
            mImageListJsonStr = null;
        }

        try {
            mMiddleImageInfoJsonStr = cursor.getString(cursor.getColumnIndex(ArticleColumns.MIDDLE_IMAGE));
            if (mMiddleImageInfoJsonStr != null) {
                JSONObject jsonObject = new JSONObject(mMiddleImageInfoJsonStr);
                mMiddleImageInfo = new ImageInfo(jsonObject);
            } else {
                mMiddleImageInfo = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            mMiddleImageInfo = null;
            mMiddleImageInfoJsonStr = null;
        }

        try {
            mVideoDetailJsonStr = cursor.getString(cursor.getColumnIndex(ArticleColumns.VIDEO_DETAIL_INFO));
            if (mVideoDetailJsonStr != null) {
                JSONObject jsonObject = new JSONObject(mVideoDetailJsonStr);
                mVideoDetailInfo = new VideoDetailInfo(jsonObject);
            } else {
                mVideoDetailInfo = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            mVideoDetailInfo = null;
            mVideoDetailJsonStr = null;
        }
    }

    public ContentValues getInsertContentValues() {
        ContentValues values = new ContentValues();
        values.put(ArticleColumns.ABSTRACT, mAbstract);
        values.put(ArticleColumns.URL, mUrl);
        values.put(ArticleColumns.BEHOT_TIME, mBehotTime);
        values.put(ArticleColumns.ITEM_ID, mItemId);
        values.put(ArticleColumns.COMMENT_COUNT, mCommentCount);
        values.put(ArticleColumns.DISPLAY_URL, mDisplayUrl);
        values.put(ArticleColumns.HAS_VIDEO, mHasVideo ? 1 : 0);
        values.put(ArticleColumns.PUBLISH_TIME, mPublishTime);
        values.put(ArticleColumns.TAG, mTag);
        values.put(ArticleColumns.HAS_IMAGE, mHasImage ? 1 : 0);
        values.put(ArticleColumns.TITLE, mTitle);
        values.put(ArticleColumns.MEDIA_NAME, mMediaName);
        values.put(ArticleColumns.SOURCE, mSource);
        values.put(ArticleColumns.VIDEO_DURATION, mVideoDuration);
        values.put(ArticleColumns.LARGE_IMAGE_LIST, mLargeImageListJsonStr);
        values.put(ArticleColumns.IMAGE_LIST, mImageListJsonStr);
        values.put(ArticleColumns.MIDDLE_IMAGE, mMiddleImageInfoJsonStr);
        values.put(ArticleColumns.VIDEO_DETAIL_INFO, mVideoDetailJsonStr);
        return values;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getCommentCount() {
        return mCommentCount;
    }

    public long getBehotTime() {
        return mBehotTime;
    }

    public long getPublishTime() {
        return mPublishTime;
    }

    public String getLabelSource() {
        return TextUtils.isEmpty(mSource) ? mMediaName : mSource;
    }

    public String getMiddleImageURL() {
        return mMiddleImageInfo == null ? null : mMiddleImageInfo.mUrl;
    }

    public String getBigImageURL() {
        return (mLargeImageList == null || mLargeImageList.size() == 0) ? null : mLargeImageList.get(0).mUrl;
    }

    public String[] getImageUrlList() {
        if (mImageList != null && mImageList.size() >= 3) {
            String[] urls = new String[3];
            for (int i = 0; i < mImageList.size(); i++) {
                urls[i] = mImageList.get(i).mUrl;
            }
            return urls;
        } else {
            return null;
        }
    }

    public int getMiddleImageWidth() {
        return mMiddleImageInfo == null ? 0 : mMiddleImageInfo.mWidth;
    }

    public int getMiddleImageHeight() {
        return mMiddleImageInfo == null ? 0 : mMiddleImageInfo.mHeight;
    }

    public int getArticleType() {
        if (mHasImage) {
            if (mImageList != null && mImageList.size() >= 3) {
                return TYPE_THREE_IMAGE;
            } else if (mLargeImageList != null) {
                return TYPE_BIG_IMAGE;
            } else {
                return TYPE_ONE_SMALL_IMAGE;
            }
        } else if (mHasVideo) {
            if (mLargeImageList == null) {
                return TYPE_SMALL_VIDEO;
            } else {
                return TYPE_BIG_VIDEO;
            }
        } else {
            return TYPE_NO_IMAGE;
        }
    }

    public int getVideoDuration() {
        return mVideoDuration;
    }

    public long getItemId() {
        return mItemId;
    }

    private class ImageInfo {
        private static final String LABEL_HEIGHT = "height";
        private static final String LABEL_URL = "url";
        private static final String LABEL_URL_LIST = "url_list";
        private static final String LABEL_WIDTH = "width";

        private int mWidth = 0;
        private int mHeight = 0;
        private String mUrl;
        private ArrayList<URL> mUrlList;

        public ImageInfo(JSONObject obj) throws JSONException {
            mWidth = obj.getInt(LABEL_WIDTH);
            mHeight = obj.getInt(LABEL_HEIGHT);
            try {
                mUrl = obj.getString(LABEL_URL);
                JSONArray jsonArray = obj.getJSONArray(LABEL_URL_LIST);
                mUrlList = new ArrayList<>(jsonArray.length());
                for (int i = 0; i < jsonArray.length(); i++) {
                    mUrlList.add(new URL(jsonArray.getJSONObject(i).getString(LABEL_URL)));
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "ImageInfo{" +
                    "mWidth=" + mWidth +
                    ", mHeight=" + mHeight +
                    ", mUrl=" + mUrl +
                    ", mUrlList=" + mUrlList +
                    '}';
        }
    }

    private class VideoDetailInfo {
        private static final String LABEL_DETAIL_VIDEO_LARGE_IMAGE = "detail_video_large_image";
        private static final String LABEL_DIRECT_PLAY = "direct_play";
        private static final String LABEL_GROUP_FLAGS = "group_flags";
        private static final String LABEL_SHOW_PGC_SUBSCRIBE = "show_pgc_subscribe";
        private static final String LABEL_VIDEO_ID = "video_id";
        private static final String LABEL_VIDEO_PRELOADING_FLAG = "video_preloading_flag";
        private static final String LABEL_VIDEO_TYPE = "video_type";
        private static final String LABEL_VIDEO_WATCH_COUNT = "video_watch_count";
        private static final String LABEL_VIDEO_WATCHING_COUNT = "video_watching_count";
        private static final String LABEL_HEIGHT = "height";
        private static final String LABEL_URL = "url";
        private static final String LABEL_URL_LIST = "url_list";
        private static final String LABEL_WIDTH = "width";

        private int mDirectPlay;
        private String mVideoId;
        private int mVideoType;
        private ImageInfo mDetailVideoLargeImageInfo;

        public VideoDetailInfo(JSONObject obj) throws JSONException {
            mDirectPlay = obj.getInt(LABEL_DIRECT_PLAY);
            mVideoId = obj.getString(LABEL_VIDEO_ID);
            mVideoType = obj.getInt(LABEL_VIDEO_TYPE);
            mDetailVideoLargeImageInfo = new ImageInfo(obj.getJSONObject(LABEL_DETAIL_VIDEO_LARGE_IMAGE));
        }

        @Override
        public String toString() {
            return "VideoDetailInfo{" +
                    "mDirectPlay=" + mDirectPlay +
                    ", mVideoId='" + mVideoId + '\'' +
                    ", mVideoType=" + mVideoType +
                    ", mDetailVideoLargeImageInfo=" + mDetailVideoLargeImageInfo +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ArticleData{" +
                "mAbstract='" + mAbstract + '\'' +
                ", mUrl='" + mUrl + '\'' +
                ", mBehotTime=" + mBehotTime +
                ", mCommentCount=" + mCommentCount +
                ", mDisplayUrl='" + mDisplayUrl + '\'' +
                ", mHasVideo=" + mHasVideo +
                ", mTag='" + mTag + '\'' +
                ", mTagId='" + mTagId + '\'' +
                ", mTitle='" + mTitle + '\'' +
                ", mLargeImageList=" + mLargeImageList +
                ", mMiddleImageInfo=" + mMiddleImageInfo +
                ", mVideoDetailInfo=" + mVideoDetailInfo +
                ", mMediaName='" + mMediaName + '\'' +
                ", mSource='" + mSource + '\'' +
                '}';
    }
}
