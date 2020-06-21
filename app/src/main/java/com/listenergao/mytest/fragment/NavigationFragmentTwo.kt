package com.listenergao.mytest.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.listenergao.mytest.R
import com.listenergao.mytest.databinding.FragmentNavigationTwoBinding


/**
 * Navigation fragment two
 * @date 2020/06/15
 * @author ListenerGao
 */
class NavigationFragmentTwo : BaseFragment() {
    companion object{
        private const val TAG = "NavigationFragmentTwo"
    }

    private lateinit var mBinding: FragmentNavigationTwoBinding
    private val args: NavigationFragmentTwoArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentNavigationTwoBinding.inflate(inflater, container, false)
        return mBinding.root
    }


    override fun initView() {
        mBinding.btnJump.setOnClickListener {
            findNavController().navigate(R.id.action_navigationTwo_to_navigationThree)
        }
    }

    override fun initData() {
        Log.d(TAG, "value one:${args.argsValueOne}")
        Log.d(TAG, "value two:${args.argsValueTwo}")
    }
}