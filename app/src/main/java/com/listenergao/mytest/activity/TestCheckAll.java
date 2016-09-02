package com.listenergao.mytest.activity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.listenergao.mytest.R;
import com.listenergao.mytest.data.TestCheckAllAdapter;
import com.listenergao.mytest.requestBean.TestCheckBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ListenerGao on 2016/8/29.
 * <p/>
 * listView实现全选,全不选功能
 */
public class TestCheckAll extends BaseActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "TestCheckAll";
    @BindView(R.id.tv_check)
    TextView tvCheck;
    @BindView(R.id.lv_check)
    ListView lvCheck;
    @BindView(R.id.ib_arrow_left)
    ImageButton ibArrowLeft;
    @BindView(R.id.bt_confirm)
    Button btConfirm;

    private List<TestCheckBean> mData;
    private TestCheckAllAdapter mAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_checkall;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        tvCheck.setVisibility(View.VISIBLE);
        tvCheck.setText("全选");
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
        mAdapter = new TestCheckAllAdapter(this, mData);
        lvCheck.setAdapter(mAdapter);

        lvCheck.setOnItemClickListener(this);

    }


    @OnClick({R.id.ib_arrow_left, R.id.tv_check, R.id.bt_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_arrow_left:
                finish();
                break;
            case R.id.tv_check:
                if (tvCheck.getText().equals("全选")) {
                    mAdapter.initCheckBox(true);
                    mAdapter.notifyDataSetChanged();
                    tvCheck.setText("全不选");
                } else if (tvCheck.getText().equals("全不选")) {
                    mAdapter.initCheckBox(false);
                    mAdapter.notifyDataSetChanged();
                    tvCheck.setText("全选");
                }
                break;

            case R.id.bt_confirm:
                //得到选中状态条目的数据Bean
                Map<Integer, Boolean> checkedMap = mAdapter.getCheckedMap();
                List<TestCheckBean> allChecked = new ArrayList<>();
                for (int i = 0; i < checkedMap.size(); i++) {

                    if (checkedMap.get(i) == null) {    //防止出现空指针,如果为空,证明没有被选中
                        continue;
                    }else if (checkedMap.get(i)){
                        TestCheckBean testCheckBean = mData.get(i);
                        allChecked.add(testCheckBean);
                    }
                }
                for (TestCheckBean bean : allChecked) {

                    Log.d(TAG, bean.toString());
                }
                break;
        }
    }

    /**
     * listView+checkBox时，listView的item焦点会失去点击。
     * 需要在listView 的item 布局的顶层布局中 添加属性：android:descendantFocusability="blocksDescendants"
     * 当点击item时checkBox实现联动效果
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TestCheckAllAdapter.ViewHolder holder = (TestCheckAllAdapter.ViewHolder) view.getTag();
        // 会自动触发CheckBox的checked事件
        holder.checkBox.toggle();
    }
}
