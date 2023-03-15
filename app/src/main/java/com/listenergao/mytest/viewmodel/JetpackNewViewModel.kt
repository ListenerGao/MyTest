package com.listenergao.mytest.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.listenergao.mytest.model.User

class JetpackNewViewModel:ViewModel() {

    private val _userData = MutableLiveData<User>()
    val userData:LiveData<User> = _userData

    private val _testData = MutableLiveData<Int>()
    val testData:LiveData<Int> = _testData

    init {
        Log.d("gys", "JetpackNewViewModel init...")

//        viewModelScope.launch {
//            Log.d("gys", "launch 1...")
//            withContext(Dispatchers.IO) {
//                Log.d("gys", "launch 2...")
//                Thread.sleep(3000)
//                Log.d("gys", "launch 3...")
//            }
//            Log.d("gys", "launch 1...")
//            _userData.value = User("ListenerGao", 30)
//        }
    }

    fun updateData(num:Int) {
        Log.d("gys", "updateData num:$num")
        _testData.value = num
    }

    fun testData() {
        var num = 0
        repeat(100){
            num++
            Log.d("gys", "repeat num:$num")
            _testData.value = num
        }
    }


}