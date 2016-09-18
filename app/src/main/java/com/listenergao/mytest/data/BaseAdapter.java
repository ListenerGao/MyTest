package com.listenergao.mytest.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ListenerGao on 2016/9/18.
 * <p/>
 * Adapter基类
 */
public abstract class BaseAdapter<V> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<V> mValueList;

    private OnItemClickListener<V> mOnItemClickListener;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return createViewHolder(parent.getContext(),parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BaseViewHolder)holder).setData(mValueList.get(position),position,mOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mValueList == null ? 0 : mValueList.size();
    }

    /**
     * 设置Item的点击事件
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener<V> listener){
        this.mOnItemClickListener = listener;
    }

    /**
     * 更新数据源
     * @param list
     */
    public void refreshData(List<V> list){
        this.mValueList = list;
        notifyDataSetChanged();
    }

    /**
     * 生成ViewHolder
     * @param context
     * @param parent
     * @return
     */
    protected abstract BaseViewHolder createViewHolder(Context context, ViewGroup parent);

}
