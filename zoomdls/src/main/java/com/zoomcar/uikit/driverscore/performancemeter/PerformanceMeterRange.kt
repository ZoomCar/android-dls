package com.zoomcar.uikit.driverscore.performancemeter

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.zoomcar.zoomdls.R

data class PerformanceMeterRange(
    var low: Int,
    var high: Int,
    @ColorRes var scaleColor: Int? = null,
    @DrawableRes var pointerImage: Int? = R.drawable.ic_chevron_down,
    @ColorRes var pointerColor: Int? = R.color.phantom_grey_08,
    var text: String? = null,
) {
    companion object {
        val defaultRanges = listOf(
            PerformanceMeterRange(
                low = 0,
                high = 20,
                scaleColor = R.color.fire_red_06,
                pointerColor = R.color.fire_red_06,
            ),
            PerformanceMeterRange(
                low = 20,
                high = 80,
                scaleColor = R.color.sunrise_yellow_04,
                pointerColor = R.color.sunrise_yellow_04,
            ),
            PerformanceMeterRange(
                low = 80,
                high = 100,
                scaleColor = R.color.ever_green_06,
                pointerColor = R.color.ever_green_06,
            )
        )
    }
}
