package com.listenergao.mytest.activity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.listenergao.mytest.R;
import com.listenergao.mytest.data.TestCheckOneAdapter;
import com.listenergao.mytest.requestBean.TestCheckBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ListenerGao on 2016/9/12.
 * <p/>
 * ListView实现单选
 */
public class TestCheckOne extends BaseActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "TestCheckOne";
    @BindView(R.id.ib_arrow_left)
    ImageButton ibArrowLeft;
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    @BindView(R.id.lv_check)
    ListView lvCheck;

    private List<TestCheckBean> mData;
    private TestCheckOneAdapter mAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_checkone;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        ibArrowLeft.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        //初始化数据
        mData = new ArrayList<>();
        for (int i = 0; i < 150; i++) {
            TestCheckBean testCheckBean = new TestCheckBean(i, "张三" + i);
            mData.add(testCheckBean);
        }
        mAdapter = new TestCheckOneAdapter(this, mData);
        lvCheck.setAdapter(mAdapter);

        lvCheck.setOnItemClickListener(this);

    }

    @OnClick({R.id.ib_arrow_left, R.id.bt_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_arrow_left:
                finish();
                break;
            case R.id.bt_confirm:
                //根据得到的位置,获取选中item的数据Bean
                int selectPosition = mAdapter.getSelectPosition();
                TestCheckBean checkBean = mData.get(selectPosition);
                Log.d(TAG, checkBean.toString());
                break;
        }
    }

    //设置ListView的点击事件,实现CheckBox联动效果.
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TestCheckOneAdapter.ViewHolder holder = (TestCheckOneAdapter.ViewHolder) view.getTag();
        holder.checkBox.toggle();
    }
}
