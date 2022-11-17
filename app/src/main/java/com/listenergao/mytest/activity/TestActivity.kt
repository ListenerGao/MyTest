package com.listenergao.mytest.activity

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.listenergao.mytest.databinding.ActivityTestBinding
import kotlin.concurrent.thread

/**
 * @description: 描述
 * @date: 2022/11/14 09:21
 * @author: ListenerGao
 */
class TestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTestBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)



        initView()
    }

    private fun initView() {
        thread {
            // TextView 宽高为 wrap_content，match_parent, 100dp 时，均可运行
            binding.textViewOne.text = Thread.currentThread().name
        }

        binding.textViewTwo.setOnClickListener {
//            it.requestLayout()
            thread {
                // TextView 宽高为精确值 100dp 时才可以运行
                // 设置成 wrap_content 或者 match_parent 需要在主线程中调用 it.requestLayout()
                binding.textViewTwo.text =
                    "${Thread.currentThread().name} : ${SystemClock.uptimeMillis()}"
            }
        }

        binding.textViewTwo.viewTreeObserver.addOnGlobalLayoutListener {
            Log.d("gys", "one width:${binding.textViewTwo.width}")
        }

        binding.textViewTwo.post {
            Log.d("gys", "two width:${binding.textViewTwo.width}")
        }
    }
}