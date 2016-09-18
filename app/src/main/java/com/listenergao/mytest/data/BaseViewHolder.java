package com.listenergao.mytest.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by ListenerGao on 2016/9/18.
 *
 * ViewHolder的基类
 */
public abstract class BaseViewHolder<V> extends RecyclerView.ViewHolder {

    public BaseViewHolder(Context context, ViewGroup root, int layoutRes) {
        super(LayoutInflater.from(context).inflate(layoutRes, root, false));
        //这里使用了ButterKnife来进行控件的绑定
        ButterKnife.bind(this, itemView);
    }

    /**
     * 方便子类获得Context
     * @return
     */
    public Context getContext(){
        return itemView.getContext();
    }

    /**
     * 抽象方法
     * 让子类自行对数据和View进行绑定
     * @param itemValue
     * @param position
     * @param listener
     */
    protected abstract void bindData(V itemValue, int position, OnItemClickListener listener);

    /**
     * 用于传递数据和信息
     * @param itemValue
     * @param position
     * @param listener
     */
    public void setData(V itemValue,int position,OnItemClickListener listener){
        bindData(itemValue,position,listener);
    }
}
