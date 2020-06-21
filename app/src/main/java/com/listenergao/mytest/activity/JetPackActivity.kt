package com.listenergao.mytest.activity

import android.content.Intent
import android.util.Log
import android.view.View
import com.listenergao.mytest.R
import com.listenergao.mytest.databinding.ActivityJetpackLayoutBinding

/**
 * JetPack 测试
 * @date 2020/06/12
 * @author ListenerGao
 */
class JetPackActivity : BaseActivity(), View.OnClickListener {
    companion object {
        const val TAG = "JetPackActivity"
    }

    private lateinit var mBinding: ActivityJetpackLayoutBinding

    override fun getLayoutResId(): Int {
        return -1
    }

    override fun getContentView(): View {
        mBinding = ActivityJetpackLayoutBinding.inflate(layoutInflater)
        Log.d(TAG, "${mBinding.root}")
        return mBinding.root
    }

    override fun initView() {
        mBinding.titleLayout.toolbar.title = "jetpack 测试"
        setSupportActionBar(mBinding.titleLayout.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun initData() {
        mBinding.btnNavigation.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_navigation -> {
                startActivity(Intent(this, NavigationActivity::class.java))
            }
        }

    }
}