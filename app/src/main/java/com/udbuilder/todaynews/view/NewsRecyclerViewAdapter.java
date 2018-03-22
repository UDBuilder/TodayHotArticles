package com.udbuilder.todaynews.view;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udbuilder.todaynews.R;
import com.udbuilder.todaynews.datamodel.data.ArticleData;
import com.udbuilder.todaynews.util.LogUtil;
import com.udbuilder.todaynews.view.item.BaseItemView;

import java.util.ArrayList;

/**
 * Created by xiongjianbo on 2018/3/7.
 */

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "NewsRecyclerViewAdapter";

    private ArrayList<ArticleData> mArticleList = new ArrayList<>();
    private Context mContext;

    public NewsRecyclerViewAdapter(final Context context) {
        mContext = context;
    }

    /**
     * 添加数据
     *
     * @param append true表示在后面追加，false表示向前添加
     */
    public void addData(Cursor cursor, boolean append) {
        ArrayList<ArticleData> list = null;
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            list = new ArrayList<>(cursor.getCount());
            do {
                list.add(new ArticleData(cursor));
            } while (cursor.moveToNext());
        }
        if (list != null) {
            if (append) {
                mArticleList.addAll(list);
            } else {
                mArticleList.addAll(0, list);
            }
            notifyDataSetChanged();
        }
    }

    public void setData(Cursor cursor) {
        mArticleList.clear();
        addData(cursor, false);
    }

    @Override
    public int getItemCount() {
        return mArticleList == null ? 0 : mArticleList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId;
        switch (viewType) {
            case ArticleData.TYPE_ONE_SMALL_IMAGE:
                layoutId = R.layout.one_small_image_item;
                break;
            case ArticleData.TYPE_THREE_IMAGE:
                layoutId = R.layout.three_small_image_item;
                break;
            case ArticleData.TYPE_SMALL_VIDEO:
                layoutId = R.layout.small_video_item;
                break;
            case ArticleData.TYPE_BIG_VIDEO:
                layoutId = R.layout.big_video_item;
                break;
            case ArticleData.TYPE_BIG_IMAGE:
                layoutId = R.layout.big_image_item;
                break;
            default:
                layoutId = R.layout.base_item_view;
        }
        BaseItemView itemView = (BaseItemView) LayoutInflater.from(mContext).
                inflate(layoutId, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ArticleData article = mArticleList.get(position);
        LogUtil.i(TAG, "onBindViewHolder article=" + article);
        BaseItemView view = (BaseItemView) holder.itemView;
        view.bind(article);
    }

    @Override
    public int getItemViewType(int position) {
        return mArticleList.get(position).getArticleType();
    }

    final class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
//            setIsRecyclable(false);
        }
    }
}
