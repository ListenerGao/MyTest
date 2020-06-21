package com.listenergao.mytest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.listenergao.mytest.R
import com.listenergao.mytest.databinding.FragmentMainNavigationBinding

/**
 * Navigation main fragment
 * @date 2020/06/15
 * @author ListenerGao
 */
class MainNavigationFragment : BaseFragment(), View.OnClickListener {

    private lateinit var mBinding: FragmentMainNavigationBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentMainNavigationBinding.inflate(inflater, container, false)
        return mBinding.root
    }


    override fun initData() {
    }

    override fun initView() {
        mBinding.btnNavigationOne.setOnClickListener(this)
        mBinding.btnNavigationTwo.setOnClickListener(this)
        mBinding.btnNavigationThree.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_navigation_one -> {
                findNavController().navigate(R.id.action_mainFragment_to_navigationOne)
            }

            R.id.btn_navigation_two -> {
                findNavController().navigate(R.id.action_mainFragment_to_navigationTwo)
            }

            R.id.btn_navigation_three -> {
                findNavController().navigate(R.id.action_mainFragment_to_navigationThree)
            }
        }

    }
}