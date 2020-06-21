package com.listenergao.mytest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.listenergao.mytest.databinding.FragmentNavigationOneBinding

/**
 * Navigation fragment one
 * @date 2020/06/15
 * @author ListenerGao
 */
class NavigationFragmentOne : BaseFragment() {

    private lateinit var mBinding: FragmentNavigationOneBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentNavigationOneBinding.inflate(inflater, container, false)
        return mBinding.root
    }


    override fun initView() {
        mBinding.btnJump.setOnClickListener {
            val directions = NavigationFragmentOneDirections.actionNavigationOneToNavigationTwo(
                    //传递参数
                    argsValueOne = true,
                    argsValueTwo = "ListenerGao"
            )
            findNavController().navigate(directions)

        }
    }

    override fun initData() {

    }

}