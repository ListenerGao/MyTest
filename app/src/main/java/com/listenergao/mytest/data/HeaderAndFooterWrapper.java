package com.listenergao.mytest.data;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.listenergao.mytest.requestBean.NewsMsgBean;

import java.util.List;

/**
 * Created by ListenerGao on 2016/7/27.
 *      原因:当我们使用RecycleView时,它没有addHeaderView和addFooterView方法.
 * 使用装饰者模式的思想,去设计一个这个类,增强原有Adapter的功能,
 * 使其支持addHeaderView和addFooterView,灵活扩展功能.
 */
public class HeaderAndFooterWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_HEADER = 100000;
    private static final int TYPE_NORMAL = 200000;
    /**
     * 头布局
     */
    private View mHeaderView;
    private List<NewsMsgBean.StoriesBean> mData;

    public HeaderAndFooterWrapper(List<NewsMsgBean.StoriesBean> data) {
        this.mData = data;
    }

    public void setHeaderView(View headerView) {
        this.mHeaderView = headerView;
        notifyItemChanged(0);
    }

    public View getHeaderView(){
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
        if (position == 100000)
            return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
