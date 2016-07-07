package com.listenergao.mytest.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.listenergao.mytest.R;
import com.listenergao.mytest.data.OkHttpManager;
import com.listenergao.mytest.requestBean.NewMesgBean;
import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import okhttp3.Request;

/**
 * Created by admin on 2016/6/22.
 */
public class MainFragment extends BaseFragment{


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        ButterKnife.bind(this,view);
        return view;

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        OkHttpManager.getAsyn("http://news-at.zhihu.com/api/4/news/latest", new OkHttpManager.ResultCallback<NewMesgBean>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.d("TAG",e.getMessage());
            }

            @Override
            public void onSuccess(NewMesgBean response) {
                Log.d("TAG",response.toString());
                Logger.d(response);
            }

        });
    }





}
