package com.listenergao.mytest.data;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.listenergao.mytest.R;
import com.listenergao.mytest.requestBean.NewsMsgBean;
import com.listenergao.mytest.utils.OkHttpManager;

import java.util.List;

/**
 * Created by ListenerGao on 2016/7/21.
 */
public class TopImgsPagerAdapter extends PagerAdapter {

    private List<NewsMsgBean.TopStoriesBean> mData;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    public TopImgsPagerAdapter(Context context,List<NewsMsgBean.TopStoriesBean> data) {
        this.mData = data;
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mLayoutInflater.inflate(R.layout.top_imgs_layout, null);
        ImageView iv_img = (ImageView) view.findViewById(R.id.iv_img);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        OkHttpManager.displayImage(mData.get(position).getImage(),iv_img);
        tv_content.setText(mData.get(position).getTitle());
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
}
