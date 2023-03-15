package com.listenergao.mytest

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import com.listenergao.mytest.databinding.ActivityNewJetpackBinding
import com.listenergao.mytest.model.User
import com.listenergao.mytest.viewmodel.JetpackNewViewModel
import kotlinx.coroutines.delay

class JetpackNewActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityNewJetpackBinding
    private val viewModel: JetpackNewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView<ActivityNewJetpackBinding>(
            this,
            R.layout.activity_new_jetpack
        )

        Log.d("gys", "onCreate viewModel:$viewModel")

        mBinding.user = User("ListenerGao", 18)

//        viewModel.userData.observe(this) {
//            Log.d("gys", "userData:$it")
//        }

        viewModel.testData.observe(this) {
            Log.d("gys", "testData:$it")
        }

        lifecycle.addObserver(object :LifecycleEventObserver{
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                Log.d("gys", "onStateChanged source:${source}, event:${event.name}")
            }

        })


    }

    override fun onStart() {
        super.onStart()
//        repeat(10){
//            viewModel.updateData(it)
//        }
    }

    override fun onResume() {
        super.onResume()
        Handler(Looper.getMainLooper()).postDelayed({
            repeat(5) {
                viewModel.updateData(1)
            }
        }, 5000)

    }


}