package com.listenergao.mytest.utils

import android.graphics.Rect
import android.view.TouchDelegate
import android.view.View

/**
 * @description: 描述
 * @date: 2022/11/9 15:56
 * @author: ListenerGao
 */
object ClickUtils {

    fun expandClickArea(view: View, expandSize: Int) {
        expandClickArea(view, expandSize, expandSize, expandSize, expandSize)
    }

    fun expandClickArea(
        view: View,
        expandLeft: Int,
        expandTop: Int,
        expandRight: Int,
        expandBottom: Int
    ) {
        val parentView = view.parent as? View ?: return

        parentView.post {
            val rect = Rect()
            view.getHitRect(rect)

            rect.left -= expandLeft
            rect.top -= expandTop
            rect.right += expandRight
            rect.bottom += expandBottom
            parentView.touchDelegate = TouchDelegate(rect, view)
        }
    }
}