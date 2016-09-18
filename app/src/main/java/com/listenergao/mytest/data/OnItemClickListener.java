package com.listenergao.mytest.data;

/**
 * Created by ListenerGao on 2016/9/18.
 */
/**
 * 点击事件接口
 *
 * @param <V>
 */
public interface OnItemClickListener<V> {
    /**
     * @param itemValue 点击item的值
     * @param itemId    点击item的id
     * @param position  点击item的位置
     */
    void onItemClick(V itemValue, int itemId, int position);
}
