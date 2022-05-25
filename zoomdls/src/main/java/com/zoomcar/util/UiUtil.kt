package com.zoomcar.util

import android.content.Context
import android.util.TypedValue

class UiUtil {
    companion object {
        fun dpToPixels(i: Int, context: Context): Int {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    i.toFloat(), context.resources.displayMetrics).toInt()
        }

        fun dpToPixels(dp: Float, context: Context): Int {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    dp, context.resources.displayMetrics).toInt()
        }
    }
}