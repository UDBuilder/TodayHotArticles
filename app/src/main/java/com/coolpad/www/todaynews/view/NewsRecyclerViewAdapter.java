package com.coolpad.www.todaynews.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coolpad.www.todaynews.R;
import com.coolpad.www.todaynews.datamodel.data.Article;
import com.coolpad.www.todaynews.util.LogUtil;
import com.coolpad.www.todaynews.view.item.BaseItemView;

import java.util.ArrayList;

/**
 * Created by xiongjianbo on 2018/3/7.
 */

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "NewsRecyclerViewAdapter";

    private ArrayList<Article> mArticles;
    private Context mContext;
    private ArrayList<String> mList;

    public NewsRecyclerViewAdapter(Context context) {
        mContext = context;
        mList = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            mList.add("hahahah" + i);
        }
//        setHasStableIds(true);
    }

    public void setData(ArrayList<Article> articles) {
        mArticles = articles;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        TextView textView = new TextView(mContext);
//        textView.setTextColor(ContextCompat.getColor(mContext, android.R.color.black));
//        return new ViewHolder(textView);
        int layoutId;
        switch (viewType) {
            case Article.TYPE_ONE_SMALL_IMAGE:
                layoutId = R.layout.one_small_image_item;
                break;
            case Article.TYPE_THREE_IMAGE:
                layoutId = R.layout.three_small_image_item;
                break;
            case Article.TYPE_SMALL_VIDEO:
                layoutId = R.layout.small_video_item;
                break;
            case Article.TYPE_BIG_VIDEO:
                layoutId = R.layout.big_video_item;
                break;
            case Article.TYPE_BIG_IMAGE:
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Article article = mArticles.get(position);
        LogUtil.i(TAG, "onBindViewHolder article=" + article);
        BaseItemView view = (BaseItemView) holder.itemView;
        view.bind(article);
//        ((TextView)holder.itemView).setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mArticles == null ? 0 : mArticles.size();
//        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mArticles.get(position).getArticleType();
    }

    private final class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
//            setIsRecyclable(false);
        }
    }
}
