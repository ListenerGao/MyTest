package com.listenergao.mytest

import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.appcompat.app.AppCompatActivity

class TestViewActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_view)

        val handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)

            }
        }


        val message = Message.obtain()
        message.what = 1024
        message.obj = "ListenerGao"
        handler.sendMessageDelayed(message, 5000)

    }
}