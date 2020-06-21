package com.listenergao.mytest.activity

import android.util.Log
import android.view.View
import androidx.navigation.findNavController
import com.listenergao.mytest.R
import com.listenergao.mytest.databinding.ActivityNavigationLayoutBinding

/**
 * JetPack Navigation
 * @date 2020/06/12
 * @author ListenerGao
 */
class NavigationActivity : BaseActivity() {
    companion object {
        private const val TAG = "NavigationActivity"
    }

    private lateinit var mBinding: ActivityNavigationLayoutBinding

    override fun getLayoutResId(): Int {
        return -1
    }

    override fun getContentView(): View {
        mBinding = ActivityNavigationLayoutBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun initData() {

    }

    override fun initView() {
        mBinding.titleLayout.toolbar.title = "jetpack Navigation"
        setSupportActionBar(mBinding.titleLayout.toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mBinding.titleLayout.toolbar.setNavigationOnClickListener {
            Log.d(TAG, "toolbar on click")
            onBackPressed()
        }

    }

    override fun onBackPressed() {
        //处理Fragment出栈，返回true，表示栈中存在Fragment，正常出栈。返回false，表示栈中不存在Fragment，此时结束Activity即可。
        if (!findNavController(R.id.nav_host_fragment).navigateUp()) {
            super.onBackPressed()
        }

    }
}