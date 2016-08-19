package com.listenergao.mytest.data;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.listenergao.mytest.R;
import com.listenergao.mytest.activity.NewsDetails;
import com.listenergao.mytest.requestBean.NewsMsgBean;
import com.listenergao.mytest.utils.OkHttpManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ListenerGao on 2016/7/8.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NORMAL = 1;
    public static final String NEWS_ID = "NEWS_ID";
    private Context mContext;
    private List<NewsMsgBean.StoriesBean> mData;
    private LayoutInflater mLayoutInflater;
    private View mHeaderView;


    public NewsAdapter(Context context, List<NewsMsgBean.StoriesBean> data) {
        this.mContext = context;
        this.mData = data;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    /**
     * 为RecycleView添加头布局
     *
     * @param headerView
     */
    public void addHeaderView(View headerView) {
        this.mHeaderView = headerView;
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? mData.size() : mData.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null)
            return TYPE_NORMAL;
        if (position == 0)
            return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER)
            return new NewsViewHolder(mHeaderView);
        View view = mLayoutInflater.inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {

        if (getItemViewType(position) == TYPE_HEADER) return;
        int realPosition = getRealPosition(holder);
        final NewsMsgBean.StoriesBean bean = mData.get(realPosition);
        holder.tv_content.setText(bean.getTitle());
        OkHttpManager.displayImage(bean.getImages().get(0), holder.iv_img);
        //为CardView添加点击事件,来替代RecycleView的item点击事件
        holder.item_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "点击了Item", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, NewsDetails.class);
                intent.putExtra(NEWS_ID,bean.getId());
                mContext.startActivity(intent);
            }
        });
    }

    /**
     * 判断是否添加了头布局后,得到当前真实的位置
     *
     * @param holder
     * @return
     */
    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.iv_img)
        ImageView iv_img;
        @BindView(R.id.item_card)
        CardView item_card;

        public NewsViewHolder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView)
                return;
            ButterKnife.bind(this, itemView);
        }
    }
}
