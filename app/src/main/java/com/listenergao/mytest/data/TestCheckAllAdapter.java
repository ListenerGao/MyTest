package com.listenergao.mytest.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.listenergao.mytest.R;
import com.listenergao.mytest.requestBean.TestCheckBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ListenerGao on 2016/8/29.
 */
public class TestCheckAllAdapter extends BaseAdapter {
    private Context mContext;
    private List<TestCheckBean> mData;
    //存储CheckBox状态的集合
    private Map<Integer, Boolean> checkedMap;

    public TestCheckAllAdapter(Context context, List<TestCheckBean> data) {
        this.mContext = context;
        this.mData = data;
        checkedMap = new HashMap<>();
        //默认CheckBox为未勾选状态
        initCheckBox(false);
    }

    /**
     * 初始化Map集合
     *
     * @param isChecked CheckBox状态
     */
    public void initCheckBox(boolean isChecked) {
        for (int i = 0; i < mData.size(); i++) {
            checkedMap.put(i, isChecked);
        }
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_test_checkall, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(mData.get(position).getName());
        // 勾选框的点击事件
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 当勾选框状态发生改变时,重新存入map集合
                checkedMap.put(position, isChecked);
            }
        });
        if (checkedMap.get(position) == null) {  //防止出现空指针现象
            checkedMap.put(position, false);
        }
        //设置勾选框的状态
        holder.checkBox.setChecked(checkedMap.get(position));
        return convertView;
    }

    /**
     * 得到勾选状态的集合
     *
     * @return
     */
    public Map<Integer, Boolean> getCheckedMap() {
        return checkedMap;
    }

    public static class ViewHolder {
        @BindView(R.id.tv_name)
        public TextView tvName;
        @BindView(R.id.cb_check)
        public CheckBox checkBox;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
