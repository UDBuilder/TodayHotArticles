package com.udbuilder.todaynews.datamodel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by xiongjianbo on 2018/3/9.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todaysnews.db";

    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_CATEGORY = "category";
    public static final String TABLE_ARTICLE = "article";

    private static final Object sLock = new Object();
    private final Context mApplicationContext;
    private SQLiteDatabase mDatabase;

    private static DatabaseHelper sHelperInstance;      // Protected by sLock.

    public static class CategoryColumns implements BaseColumns {
        public static final String CATEGORY = "category";
        public static final String NAME = "name";
        public static final String LAST_REFRESH_TIME = "last_refresh_time";
        public static final String LAST_LOADMORE_TIME = "last_loadmore_time";
        public static final String TOP_TIME = "top_time";
        public static final String BOTTOM_TIME = "bottom_time";
    }

    private static final String CREATE_CATEGORY_TABLE_SQL = "CREATE TABLE " + TABLE_CATEGORY + " ("
            + CategoryColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CategoryColumns.CATEGORY + " VARCHAR NOT NULL, "
            + CategoryColumns.NAME + " VARCHAR NOT NULL, "
            + CategoryColumns.LAST_REFRESH_TIME + " INTEGER NOT NULL DEFAULT 0, "
            + CategoryColumns.LAST_LOADMORE_TIME + " INTEGER NOT NULL DEFAULT 0, "
            + CategoryColumns.TOP_TIME + " INTEGER NOT NULL DEFAULT 0, "
            + CategoryColumns.BOTTOM_TIME + " INTEGER NOT NULL DEFAULT 0"
            + ")";

    public static class ArticleColumns implements BaseColumns {
        public static final String CATEGORY = "category";
        public static final String ABSTRACT = "abstract";
        public static final String AGGR_TYPE = "aggr_type";
        public static final String ACTION_LIST = "action_list";
        public static final String ALLOW_DOWNLOAD = "allow_download";
        public static final String ARTICLE_SUB_TYPE = "article_sub_type";
        public static final String ARTICLE_TYPE = "article_type";
        public static final String ARTICLE_URL = "article_url";
        public static final String BAN_COMMENT = "ban_comment";
        public static final String BEHOT_TIME = "behot_time";
        public static final String BURY_COUNT = "bury_count";
        public static final String CELL_FLAG = "cell_flag";
        public static final String CELL_LAYOUT_STYLE = "cell_layout_style";
        public static final String CELL_TYPE = "cell_type";
        public static final String COMMENT_COUNT = "comment_count";
        public static final String CONTENT_DECORATION = "content_decoration";
        public static final String CURSOR = "cursor";
        public static final String DIGG_COUNT = "digg_count";
        public static final String DISPLAY_URL = "display_url";
        public static final String FILTER_WORDS = "filter_words";
        public static final String FORWARD_INFO = "forward_info";
        public static final String GROUP_FLAGS = "group_flags";
        public static final String GROUP_ID = "group_id";
        public static final String HAS_M3U8_VIDEO = "has_m3u8_video";
        public static final String HAS_MP4_VIDEO = "has_mp4_video";
        public static final String HAS_VIDEO = "has_video";
        public static final String HAS_IMAGE = "has_image";
        public static final String IGNORE_WEB_TRANSFORM = "ignore_web_transform";
        public static final String IS_SUBJECT = "is_subject";
        public static final String ITEM_ID = "item_id";
        public static final String ITEM_VERSION = "item_version";
        public static final String LARGE_IMAGE_LIST = "large_image_list";
        public static final String IMAGE_LIST = "image_list";
        public static final String LEVEL = "level";
        public static final String LOG_PB = "log_pb";
        public static final String MEDIA_NAME = "media_name";
        public static final String MIDDLE_IMAGE = "middle_image";
        public static final String NEED_CLIENT_IMPR_RECYCLE = "need_client_impr_recycle";
        public static final String PUBLISH_TIME = "publish_time";
        public static final String READ_COUNT = "read_count";
        public static final String RID = "rid";
        public static final String SHARE_COUNT = "share_count";
        public static final String SHARE_INFO = "share_info";
        public static final String SHARE_URL = "share_url";
        public static final String SHOW_DISLIKE = "show_dislike";
        public static final String SHOW_PORTRAIT = "show_portrait";
        public static final String SHOW_PORTRAIT_ARTICLE = "show_portrait_article";
        public static final String SOURCE = "source";
        public static final String SOURCE_AVATAR = "source_avatar";
        public static final String SOURCE_ICON_STYLE = "source_icon_style";
        public static final String TAG = "tag";
        public static final String TAG_ID = "tag_id";
        public static final String TIP = "tip";
        public static final String TITLE = "title";
        public static final String UGC_RECOMMEND = "ugc_recommend";
        public static final String URL = "url";
        public static final String USER_INFO = "user_info";
        public static final String USER_REPIN = "user_repin";
        public static final String USER_VERIFIED = "user_verified";
        public static final String VERIFIED_CONTENT = "verified_content";
        public static final String VIDEO_DETAIL_INFO = "video_detail_info";
        public static final String VIDEO_DURATION = "video_duration";
        public static final String VIDEO_ID = "video_id";
        public static final String VIDEO_STYLE = "video_style";
    }

    private static final String CREATE_ARTICLE_TABLE_SQL = "CREATE TABLE " + TABLE_ARTICLE + " ("
            + ArticleColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ArticleColumns.CATEGORY + " TEXT, "
            + ArticleColumns.ABSTRACT + " TEXT, "
//            + ArticleColumns.AGGR_TYPE + "  INTEGER NOT NULL DEFAULT 0, "
//            + ArticleColumns.ARTICLE_SUB_TYPE + " INTEGER NOT NULL DEFAULT 0, "
//            + ArticleColumns.ARTICLE_TYPE + " INTEGER NOT NULL DEFAULT 0, "
            + ArticleColumns.ARTICLE_URL + " VARCHAR, "
            + ArticleColumns.BAN_COMMENT + " INTEGER NOT NULL DEFAULT 0, "
            + ArticleColumns.BEHOT_TIME + " INTEGER NOT NULL DEFAULT 0, "
            + ArticleColumns.BURY_COUNT + " INTEGER NOT NULL DEFAULT 0, "
            + ArticleColumns.COMMENT_COUNT + " INTEGER NOT NULL DEFAULT 0, "
            + ArticleColumns.DIGG_COUNT + " INTEGER NOT NULL DEFAULT 0, "
            + ArticleColumns.DISPLAY_URL + " VARCHAR, "
            + ArticleColumns.GROUP_FLAGS + " INTEGER NOT NULL DEFAULT 0, "
            + ArticleColumns.GROUP_ID + " INTEGER NOT NULL DEFAULT 0, "
            + ArticleColumns.HAS_VIDEO + " INTEGER NOT NULL DEFAULT 0, "
            + ArticleColumns.HAS_IMAGE + " INTEGER NOT NULL DEFAULT 0, "
            + ArticleColumns.HAS_M3U8_VIDEO + " INTEGER NOT NULL DEFAULT 0, "
            + ArticleColumns.HAS_MP4_VIDEO + " INTEGER NOT NULL DEFAULT 0, "
            + ArticleColumns.ITEM_ID + " INTEGER NOT NULL, "
            + ArticleColumns.ITEM_VERSION + " INTEGER NOT NULL DEFAULT 0, "
            + ArticleColumns.FILTER_WORDS + " TEXT, "
            + ArticleColumns.FORWARD_INFO + " TEXT, "
            + ArticleColumns.LARGE_IMAGE_LIST + " TEXT, "
            + ArticleColumns.IMAGE_LIST + " TEXT, "
            + ArticleColumns.LEVEL + " INTEGER NOT NULL DEFAULT 0, "
            + ArticleColumns.MEDIA_NAME + " VARCHAR, "
            + ArticleColumns.MIDDLE_IMAGE + " TEXT, "
            + ArticleColumns.PUBLISH_TIME + " INTEGER, "
            + ArticleColumns.READ_COUNT + " INTEGER, "
            + ArticleColumns.RID + " INTEGER, "
            + ArticleColumns.SHARE_COUNT + " INTEGER, "
            + ArticleColumns.SHARE_URL + " VARCHAR, "
            + ArticleColumns.SHARE_INFO + " VARCHAR, "
            + ArticleColumns.SOURCE + " VARCHAR, "
            + ArticleColumns.TAG + " VARCHAR, "
            + ArticleColumns.TIP + " INTEGER NOT NULL DEFAULT 0, "
            + ArticleColumns.TITLE + " VARCHAR, "
            + ArticleColumns.URL + " VARCHAR, "
            + ArticleColumns.USER_INFO + " VARCHAR, "
            + ArticleColumns.USER_REPIN + " INTEGER, "
            + ArticleColumns.VIDEO_DETAIL_INFO + " TEXT, "
            + ArticleColumns.VIDEO_DURATION + " INTEGER NOT NULL DEFAULT 0, "
            + ArticleColumns.VIDEO_ID + " INTEGER"
            + ")";

    private static final String[] CREATE_TABLE_SQLS = {
            CREATE_CATEGORY_TABLE_SQL,
            CREATE_ARTICLE_TABLE_SQL,
    };

    private void createTables(SQLiteDatabase db) {
        for (String sql : CREATE_TABLE_SQLS) {
            db.execSQL(sql);
        }
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mApplicationContext = context;
        mDatabase = getWritableDatabase();
    }

    public static DatabaseHelper getInstance(final Context context) {
        synchronized (sLock) {
            if (sHelperInstance == null) {
                sHelperInstance = new DatabaseHelper(context);
            }
            return sHelperInstance;
        }
    }

    public static SQLiteDatabase getDatabase(Context context) {
        return DatabaseHelper.getInstance(context).mDatabase;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
