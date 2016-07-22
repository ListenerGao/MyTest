package com.listenergao.mytest.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.listenergao.mytest.R;
import com.listenergao.mytest.requestBean.NewsMsgBean;
import com.listenergao.mytest.utils.OkHttpManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2016/7/8.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private Context mContext;
    private List<NewsMsgBean.StoriesBean> mData;
    private LayoutInflater mLayoutInflater;

    public NewsAdapter(Context context, List<NewsMsgBean.StoriesBean> data){
        this.mContext = context;
        this.mData = data;
        this.mLayoutInflater = LayoutInflater.from(context);
    }
    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        NewsMsgBean.StoriesBean bean = mData.get(position);
        holder.tv_content.setText(bean.getTitle());
        OkHttpManager.displayImage(bean.getImages().get(0),holder.iv_img);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0:mData.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.iv_img)
        ImageView iv_img;

        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
