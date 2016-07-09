package com.listenergao.mytest.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.listenergao.mytest.R;
import com.listenergao.mytest.data.NewsAdapter;
import com.listenergao.mytest.data.OkHttpManager;
import com.listenergao.mytest.requestBean.NewsMsgBean;
import com.listenergao.mytest.utils.UiUtils;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;

/**
 * Created by admin on 2016/6/22.
 */
public class MainFragment extends BaseFragment {


    private static final String TAG = "MainFragment";
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;

    private NewsMsgBean mNewsData;
    private NewsAdapter adapter;
    private boolean isRefreshing = false;   //是否正在刷新

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void initView() {
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        NewsAdapter adapter = new NewsAdapter(UiUtils.getContext(), mNewsData.getStories());
//        recycleView.setAdapter(adapter);
        //设置进度条的颜色主题，最多能设置四种 加载颜色是循环播放的，只要没有完成刷新就会一直循环，holo_blue_bright>holo_green_light>holo_orange_light>holo_red_light
        swipeContainer.setColorSchemeColors(Color.BLUE,
                Color.GREEN,
                Color.YELLOW,
                Color.RED);
        // 设置手指在屏幕下拉多少距离会触发下拉刷新
        swipeContainer.setDistanceToTriggerSync(300);
        // 设定下拉圆圈的背景
        swipeContainer.setProgressBackgroundColorSchemeColor(Color.WHITE);
        // 设置圆圈的大小
        swipeContainer.setSize(SwipeRefreshLayout.LARGE);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isRefreshing){
                    isRefreshing = true;
                    requestData();
                }
            }
        });
    }

    @Override
    public void initData() {
        requestData();
    }

    private void requestData(){
        swipeContainer.setRefreshing(true); //显示刷新进度条
        OkHttpManager.getAsyn("http://news-at.zhihu.com/api/4/news/latest", new OkHttpManager.ResultCallback<NewsMsgBean>() {

            @Override
            public void onError(Request request, Exception e) {
                swipeContainer.setRefreshing(false);
                isRefreshing = false;
                Log.d("TAG", e.getMessage());
            }

            @Override
            public void onSuccess(NewsMsgBean response) {
                swipeContainer.setRefreshing(false);    //隐藏刷新进度条
                isRefreshing = false;
                Logger.d(response);
                mNewsData = response;
                adapter = new NewsAdapter(UiUtils.getContext(), mNewsData.getStories());
                recycleView.setAdapter(adapter);
            }

        });
    }


}
