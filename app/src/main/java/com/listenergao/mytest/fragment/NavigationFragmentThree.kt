package com.listenergao.mytest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.listenergao.mytest.R
import com.listenergao.mytest.databinding.FragmentNavigationThreeBinding


/**
 * Navigation fragment three
 * @date 2020/06/15
 * @author ListenerGao
 */
class NavigationFragmentThree : BaseFragment() {

    private lateinit var mBinding: FragmentNavigationThreeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentNavigationThreeBinding.inflate(inflater, container, false)
        return mBinding.root
    }


    override fun initView() {
        mBinding.btnJump.setOnClickListener {
            findNavController().navigate(R.id.action_navigationThree_to_navigationOne)
        }
    }

    override fun initData() {

    }
}