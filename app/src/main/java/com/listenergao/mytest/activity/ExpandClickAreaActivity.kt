package com.listenergao.mytest.activity

import android.view.View
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isGone
import com.blankj.utilcode.util.ToastUtils
import com.listenergao.mytest.R
import com.listenergao.mytest.utils.ClickUtils
import com.listenergao.mytest.utils.UiUtils

/**
 * @description: 描述
 * @date: 2022/11/9 15:36
 * @author: ListenerGao
 */
class ExpandClickAreaActivity : BaseActivity(), View.OnClickListener {

    private lateinit var toolbar: Toolbar
    private lateinit var mClick: Button
    private lateinit var mGone: Button

    override fun getLayoutResId(): Int {
        return R.layout.activity_expand_click_area
    }

    override fun initView() {
        toolbar = findViewById(R.id.toolbar)
        toolbar.title = "扩大点击区域"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mClick = findViewById(R.id.btn_click)
        mGone = findViewById(R.id.btn_gone)

        mClick.setOnClickListener(this)
        mGone.setOnClickListener(this)
    }

    override fun initData() {
        // 使用 TouchDelegate 扩大点击区域
        ClickUtils.expandClickArea(mClick, UiUtils.dp2px(30f))

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_click -> {
                ToastUtils.showShort("点击了")
            }
            R.id.btn_gone -> {
                mGone.text = if (mClick.isGone) {
                    mClick.visibility = View.VISIBLE
                    // 由于扩大了点击区域，隐藏时，需要设置未不可点击
                    mClick.isEnabled = true
                    "隐藏"
                } else {
                    mClick.visibility = View.GONE
                    mClick.isEnabled = false
                    "显示"
                }
            }
        }
    }
}