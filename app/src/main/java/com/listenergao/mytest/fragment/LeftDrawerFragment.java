package com.listenergao.mytest.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.listenergao.mytest.MainActivity;
import com.listenergao.mytest.R;
import com.listenergao.mytest.activity.Android6NewWidget;
import com.listenergao.mytest.activity.DownLoadActivity;
import com.listenergao.mytest.activity.JetPackActivity;
import com.listenergao.mytest.activity.PopupWindowTest;
import com.listenergao.mytest.activity.RxJavaTestActivity;
import com.listenergao.mytest.activity.SettingsActivity;
import com.listenergao.mytest.activity.TestCheckAll;
import com.listenergao.mytest.activity.TestCheckOne;
import com.listenergao.mytest.activity.TestFragmentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ListenerGao on 2016/6/29.
 * <p/>
 * 侧拉菜单界面
 */
public class LeftDrawerFragment extends BaseFragment {

    @BindView(R.id.settings)
    LinearLayout mSettings;
    @BindView(R.id.about_us)
    LinearLayout mAboutUs;
    @BindView(R.id.tv_popupwindow)
    TextView tvPopupwindow;
    @BindView(R.id.tv_download)
    TextView tvDownload;
    @BindView(R.id.tv_test)
    TextView tvTest;
    @BindView(R.id.tv_test1)
    TextView tvTest1;
    @BindView(R.id.tv_activity_anim)
    TextView tvActivityAnim;
    @BindView(R.id.tv_activity_new_widget)
    TextView tvNewWidget;
    @BindView(R.id.tv_activity_RxJava)
    TextView tvActivityRxJava;
    private MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_left_menu, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        mainActivity = (MainActivity) getActivity();
    }

    @OnClick({R.id.settings, R.id.about_us, R.id.tv_popupwindow, R.id.tv_download, R.id.tv_test,
            R.id.tv_activity_anim, R.id.tv_test1, R.id.tv_activity_new_widget,
            R.id.tv_activity_RxJava, R.id.tv_activity_jetpack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.settings:     //设置页面
                openActivity(SettingsActivity.class);
                break;
            case R.id.about_us:
                break;
            case R.id.tv_popupwindow:   //PopupWindow测试页面
                openActivity(PopupWindowTest.class);
                break;
            case R.id.tv_download:  //断点续传下载
                openActivity(DownLoadActivity.class);
                break;

            case R.id.tv_test:  //ListView实现全选
                openActivity(TestCheckAll.class);
                break;

            case R.id.tv_test1:     //ListView实现单选
                openActivity(TestCheckOne.class);
                break;

            case R.id.tv_activity_anim:     //Fragment测试
                openActivity(TestFragmentActivity.class);
                break;

            case R.id.tv_activity_new_widget:   //Android6.0新控件测试
                openActivity(Android6NewWidget.class);
                break;
            case R.id.tv_activity_RxJava:   //RxJava测试
                openActivity(RxJavaTestActivity.class);
                break;

            case R.id.tv_activity_jetpack: //jetpack测试
                openActivity(JetPackActivity.class);
                break;

            default:
                break;


        }
        mainActivity.drawerLayout.closeDrawer(Gravity.LEFT);    //关闭侧滑菜单
    }


    private void openActivity(Class clazz) {
        if (getContext() != null) {
            Intent intent = new Intent(getContext(), clazz);
            startActivity(intent);
        }
    }
}
