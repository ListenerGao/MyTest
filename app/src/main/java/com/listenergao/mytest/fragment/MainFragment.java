package com.listenergao.mytest.fragment;

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


    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;

    private NewsMsgBean mNewsData;
    private NewsAdapter adapter;

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
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (swipeContainer.isRefreshing()){
                    return;
                }else {
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
//        swipeContainer.setRefreshing(true); //显示刷新进度条
        OkHttpManager.getAsyn("http://news-at.zhihu.com/api/4/news/latest", new OkHttpManager.ResultCallback<NewsMsgBean>() {

            @Override
            public void onError(Request request, Exception e) {
                swipeContainer.setRefreshing(false);
                Log.d("TAG", e.getMessage());
            }

            @Override
            public void onSuccess(NewsMsgBean response) {
                swipeContainer.setRefreshing(false);    //隐藏刷新进度条
                Logger.d(response);
                mNewsData = response;
                adapter = new NewsAdapter(UiUtils.getContext(), mNewsData.getStories());
                recycleView.setAdapter(adapter);
            }

        });
    }


}
