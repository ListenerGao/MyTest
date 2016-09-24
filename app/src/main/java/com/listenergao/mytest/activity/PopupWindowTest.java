package com.listenergao.mytest.activity;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.listenergao.mytest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ListenerGao on 2016/8/18.
 */
public class PopupWindowTest extends BaseActivity {
    @BindView(R.id.civ_title_head)
    CircleImageView civTitleHead;
    @BindView(R.id.ib_arrow_left)
    ImageButton ibArrowLeft;
    @BindView(R.id.ib_drawer)
    ImageButton ibDrawer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bt_pop_bottom_dismiss)
    Button btPopBottom;
    @BindView(R.id.bt_pop_center)
    Button btPopCenter;
    @BindView(R.id.bt_pop_bottom_no_dismiss)
    Button btPopBottomNoDismiss;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_popupwindow;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        ibArrowLeft.setVisibility(View.VISIBLE);
        toolbar.setTitle("PopupWindowTest");
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.ib_arrow_left, R.id.bt_pop_bottom_dismiss, R.id.bt_pop_bottom_no_dismiss, R.id.bt_pop_center})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_arrow_left:
                finish();
                break;
            case R.id.bt_pop_bottom_dismiss:
                showBottomPopupWindow(view);
                break;
            case R.id.bt_pop_bottom_no_dismiss:
                showBottomPopupWindow2(view);
                break;
            case R.id.bt_pop_center:
                showCenterPopupWindow(view);
                break;
        }
    }

    /**
     * 底部弹出PopupWindow
     *
     * 点击PopupWindow以外部分或点击返回键时,PopupWindow 会 消失
     * @param view  parent view
     */
    public void showBottomPopupWindow(View view) {
        //自定义PopupWindow的布局
        View contentView = LayoutInflater.from(this).inflate(R.layout.popupwindow_layout, null);
        //初始化PopupWindow,并为其设置布局文件
        final PopupWindow popupWindow = new PopupWindow(contentView);
        //确定按钮点击事件
        contentView.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PopupWindowTest.this, "点击了确定", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });
        //取消按钮点击事件
        contentView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //设置PopupWindow的宽和高,必须设置,否则不显示内容(也可用PopupWindow的构造方法设置宽高)
        popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        //当需要点击返回键,或者点击空白时,需要设置下面两句代码.
        //如果有背景，则会在contentView外面包一层PopupViewContainer之后作为mPopupView，如果没有背景，则直接用contentView作为mPopupView。
        //而这个PopupViewContainer是一个内部私有类，它继承了FrameLayout，在其中重写了Key和Touch事件的分发处理
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));   //为PopupWindow设置透明背景.
        popupWindow.setOutsideTouchable(false);
        //设置PopupWindow进入和退出动画
        popupWindow.setAnimationStyle(R.style.anim_popup_bottombar);
        //设置PopupWindow显示的位置
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 底部弹出PopupWindow
     *
     * 点击PopupWindow以外部分或点击返回键时,PopupWindow 不会 消失
     * @param view  parent view
     */
    public void showBottomPopupWindow2(View view) {
        //自定义PopupWindow的布局
        View contentView = LayoutInflater.from(this).inflate(R.layout.popupwindow_layout, null);
        contentView.setFocusable(true);
        contentView.setFocusableInTouchMode(true);
        //初始化PopupWindow对象,并为其设置宽高以及布局文件
        final PopupWindow popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);

        contentView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    popupWindow.dismiss();

                    return true;
                }
                return false;
            }
        });
        //确定按钮点击事件
        contentView.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PopupWindowTest.this, "点击了确定", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });
        //取消按钮点击事件
        contentView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        //设置PopupWindow进入和退出动画
        popupWindow.setAnimationStyle(R.style.anim_popup_bottombar);
        //设置PopupWindow显示在底部
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 中间弹出PopupWindow
     *
     *  设置PopupWindow以外部分有一中变暗的效果
     * @param view  parent view
     */
    public void showCenterPopupWindow(View view) {
        View contentView = LayoutInflater.from(this).inflate(R.layout.popupwindow_layout, null);
        final PopupWindow popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView tvTitle = (TextView)contentView.findViewById(R.id.tv_title);
        TextView tvConfirm = (TextView)contentView.findViewById(R.id.tv_confirm);
        TextView tvCancel = (TextView)contentView.findViewById(R.id.tv_cancel);
        tvTitle.setText("标为已读");
        tvConfirm.setText("置顶公众号");
        tvCancel.setText("取消关注");

        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PopupWindowTest.this, "标为已读", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PopupWindowTest.this, "置顶公众号", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PopupWindowTest.this, "取消关注", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        // 设置PopupWindow以外部分的背景颜色  有一种变暗的效果
        final WindowManager.LayoutParams wlBackground = getWindow().getAttributes();
        wlBackground.alpha = 0.5f;      // 0.0 完全不透明,1.0完全透明
        getWindow().setAttributes(wlBackground);
        // 当PopupWindow消失时,恢复其为原来的颜色
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                wlBackground.alpha = 1.0f;
                getWindow().setAttributes(wlBackground);
            }
        });
        //设置PopupWindow进入和退出动画
        popupWindow.setAnimationStyle(R.style.anim_popup_centerbar);
        // 设置PopupWindow显示在中间
        popupWindow.showAtLocation(view,Gravity.CENTER,0,0);

    }

}
