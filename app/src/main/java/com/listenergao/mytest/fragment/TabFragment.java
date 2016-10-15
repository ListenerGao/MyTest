package com.listenergao.mytest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.listenergao.mytest.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Tab标签对应的页面
 *
 * @author By ListenerGao
 *         Create at 2016/10/15 16:53
 */
public class TabFragment extends Fragment {
    @BindView(R.id.tv_content)
    TextView tvContent;
    private View view;
    private String content;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            content = bundle.getString("Content");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvContent.setText(content);
    }
}
