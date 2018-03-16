package com.coolpad.www.todaynews.datamodel.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

import com.coolpad.www.todaynews.datamodel.DatabaseHelper.ArticleColumns;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by xiongjianbo on 2018/3/6.
 */

public class Article {

    public static final int TYPE_NO_IMAGE = 1;
    public static final int TYPE_ONE_SMALL_IMAGE = 2;
    public static final int TYPE_BIG_IMAGE = 3;
    public static final int TYPE_THREE_IMAGE = 4;
    public static final int TYPE_SMALL_VIDEO = 5;
    public static final int TYPE_BIG_VIDEO = 6;

    public static final String[] PROJECTION = {
            ArticleColumns.ABSTRACT,
            ArticleColumns.AGGR_TYPE,
            ArticleColumns.ACTION_LIST,
            ArticleColumns.ALLOW_DOWNLOAD,
            ArticleColumns.ARTICLE_SUB_TYPE,
            ArticleColumns.ARTICLE_TYPE,
            ArticleColumns.ARTICLE_URL,
            ArticleColumns.BAN_COMMENT,
            ArticleColumns.BEHOT_TIME,
            ArticleColumns.BURY_COUNT,
            ArticleColumns.CELL_FLAG,
            ArticleColumns.CELL_LAYOUT_STYLE,
            ArticleColumns.CELL_TYPE,
            ArticleColumns.COMMENT_COUNT,
            ArticleColumns.CONTENT_DECORATION,
            ArticleColumns.CURSOR,
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
            ArticleColumns.IGNORE_WEB_TRANSFORM,
            ArticleColumns.IS_SUBJECT,
            ArticleColumns.ITEM_ID,
            ArticleColumns.ITEM_VERSION,
            ArticleColumns.LARGE_IMAGE_LIST,
            ArticleColumns.IMAGE_LIST,
            ArticleColumns.LEVEL,
            ArticleColumns.LOG_PB,
            ArticleColumns.MEDIA_NAME,
            ArticleColumns.MIDDLE_IMAGE,
            ArticleColumns.NEED_CLIENT_IMPR_RECYCLE,
            ArticleColumns.PUBLISH_TIME,
            ArticleColumns.READ_COUNT,
            ArticleColumns.RID,
            ArticleColumns.SHARE_COUNT,
            ArticleColumns.SHARE_INFO,
            ArticleColumns.SHARE_URL,
            ArticleColumns.SHOW_DISLIKE,
            ArticleColumns.SHOW_PORTRAIT,
            ArticleColumns.SHOW_PORTRAIT_ARTICLE,
            ArticleColumns.SOURCE,
            ArticleColumns.SOURCE_AVATAR,
            ArticleColumns.SOURCE_ICON_STYLE,
            ArticleColumns.TAG,
            ArticleColumns.TAG_ID,
            ArticleColumns.TIP,
            ArticleColumns.TITLE,
            ArticleColumns.UGC_RECOMMEND,
            ArticleColumns.URL,
            ArticleColumns.USER_INFO,
            ArticleColumns.USER_REPIN,
            ArticleColumns.USER_VERIFIED,
            ArticleColumns.VERIFIED_CONTENT,
            ArticleColumns.VIDEO_DETAIL_INFO,
            ArticleColumns.VIDEO_DURATION,
            ArticleColumns.VIDEO_ID,
            ArticleColumns.VIDEO_STYLE,
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


    public Article(JSONObject object) {
        try {
            JSONObject jsonObject = new JSONObject(object.getString("content"));
            mAbstract = jsonObject.has(ArticleColumns.ABSTRACT) ? jsonObject.getString(ArticleColumns.ABSTRACT) : null;
            mUrl = jsonObject.has(ArticleColumns.URL) ? jsonObject.getString(ArticleColumns.URL) : null;
            mBehotTime = jsonObject.has(ArticleColumns.BEHOT_TIME) ? jsonObject.getLong(ArticleColumns.BEHOT_TIME) : 0;
            mCommentCount = jsonObject.has(ArticleColumns.COMMENT_COUNT) ? jsonObject.getInt(ArticleColumns.COMMENT_COUNT) : 0;
            mDisplayUrl = jsonObject.has(ArticleColumns.DISPLAY_URL) ? jsonObject.getString(ArticleColumns.DISPLAY_URL) : null;
            mHasVideo = jsonObject.has(ArticleColumns.HAS_VIDEO) && jsonObject.getBoolean(ArticleColumns.HAS_VIDEO);
            mPublishTime = jsonObject.has(ArticleColumns.PUBLISH_TIME) ? jsonObject.getLong(ArticleColumns.PUBLISH_TIME) : 0;
            mTag = jsonObject.has(ArticleColumns.TAG) ? jsonObject.getString(ArticleColumns.TAG) : null;
            mHasImage = jsonObject.has(ArticleColumns.HAS_IMAGE) && jsonObject.getBoolean(ArticleColumns.HAS_IMAGE);
            mTagId = jsonObject.has(ArticleColumns.TAG_ID) ? jsonObject.getString(ArticleColumns.TAG_ID) : null;
            mTitle = jsonObject.has(ArticleColumns.TITLE) ? jsonObject.getString(ArticleColumns.TITLE) : null;
            mVideoDetailInfo = jsonObject.has(ArticleColumns.VIDEO_DETAIL_INFO) ?
                    new VideoDetailInfo(jsonObject.getJSONObject(ArticleColumns.VIDEO_DETAIL_INFO)) : null;
            mMediaName = jsonObject.has(ArticleColumns.MEDIA_NAME) ? jsonObject.getString(ArticleColumns.MEDIA_NAME) : null;
            mSource = jsonObject.has(ArticleColumns.SOURCE) ? jsonObject.getString(ArticleColumns.SOURCE) : null;
            if (jsonObject.has(ArticleColumns.LARGE_IMAGE_LIST)) {
                JSONArray jsonArray = jsonObject.getJSONArray(ArticleColumns.LARGE_IMAGE_LIST);
                mLargeImageList = new ArrayList<>(jsonArray.length());
                for (int i = 0; i < jsonArray.length(); i++) {
                    mLargeImageList.add(new ImageInfo(jsonArray.getJSONObject(i)));
                }
            } else {
                mLargeImageList = null;
            }
            if (jsonObject.has(ArticleColumns.IMAGE_LIST)) {
                JSONArray jsonArray = jsonObject.getJSONArray(ArticleColumns.IMAGE_LIST);
                mImageList = new ArrayList<>(jsonArray.length());
                for (int i = 0; i < jsonArray.length(); i++) {
                    mImageList.add(new ImageInfo(jsonArray.getJSONObject(i)));
                }
            } else {
                mImageList = null;
            }
            mMiddleImageInfo = jsonObject.has(ArticleColumns.MIDDLE_IMAGE) ?
                    new ImageInfo(jsonObject.getJSONObject(ArticleColumns.MIDDLE_IMAGE)) : null;
            mVideoDuration = jsonObject.has(ArticleColumns.VIDEO_DURATION) ? jsonObject.getInt(ArticleColumns.VIDEO_DURATION) : 0;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Article(Cursor cursor) {

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

    public ContentValues getInsertValues(Context context) {
        ContentValues cv = new ContentValues();
        cv.put(ArticleColumns.ABSTRACT, mAbstract);
        return cv;
    }

    @Override
    public String toString() {
        return "Article{" +
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
