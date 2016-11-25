package com.listenergao.mytest.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.listenergao.mytest.R;
import com.listenergao.mytest.data.NewsAdapter;
import com.listenergao.mytest.data.TopImgsPagerAdapter;
import com.listenergao.mytest.requestBean.NewsMsgBean;
import com.listenergao.mytest.utils.OkHttpManager;
import com.listenergao.mytest.utils.UiUtils;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;
import okhttp3.Request;

/**
 * Created by ListenerGao on 2016/6/22.
 *
 */
public class MainFragment extends BaseFragment {


    private static final String TAG = "MainFragment";
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;

    private ViewPager topImgViewPager;
    /**
     * ViewPager指示器
     */
    private CircleIndicator indicator;

    private NewsMsgBean mNewsData;
    private NewsAdapter adapter;
    private boolean isRefreshing = false;   //是否正在刷新
    private static final int VIEWPAGER_MSG = 0x110;
    private int viewPagerPosition;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            viewPagerPosition++;
            viewPagerPosition %= mNewsData.getTop_stories().size();
            topImgViewPager.setCurrentItem(viewPagerPosition);
            mHandler.sendEmptyMessageDelayed(VIEWPAGER_MSG,3000);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void initView() {
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //这是RecycleView默认的item动画
        recycleView.setItemAnimator(new DefaultItemAnimator());
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
        swipeContainer.setSize(SwipeRefreshLayout.DEFAULT);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isRefreshing) {
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

    private void requestData() {
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
                List<NewsMsgBean.TopStoriesBean> top_stories = mNewsData.getTop_stories();

                adapter = new NewsAdapter(getActivity(), mNewsData.getStories());
                recycleView.setAdapter(adapter);
                setHeader(recycleView);

                TopImgsPagerAdapter pagerAdapter = new TopImgsPagerAdapter(UiUtils.getContext(),top_stories);
                topImgViewPager.setAdapter(pagerAdapter);
                //将指示器与ViewPager关联
                indicator.setViewPager(topImgViewPager);
                //ViewPager的轮播
                mHandler.sendEmptyMessage(0);
            }

        });
    }

    private void setHeader(RecyclerView recycleView) {
        View header = LayoutInflater.from(getContext()).inflate(R.layout.news_header, recycleView, false);
        topImgViewPager = (ViewPager) header.findViewById(R.id.top_imgs);
        indicator = (CircleIndicator) header.findViewById(R.id.indicator);
        adapter.addHeaderView(header);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //移除消息
        mHandler.removeMessages(VIEWPAGER_MSG);
    }
}
