package com.zoomcar.uikit.driverscore.performancemeter

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.zoomcar.zoomdls.R

/**
 * A range can be something in between 0 to 100. For example, we can have 3 ranges to depict
 * a performance meter: 0-20, 20-80, 80-100.
 *
 * Each range can be given an individual label, color, pointerImage and tint to color
 * that pointerImage.
 *
 * @property low Starting of a range
 * @property high Ending of a range
 * @property scaleColor Shading color of a range
 * @property pointerImage Image of pointer that points to score at this range
 * @property pointerColor If provided, will tint the pointerImage.
 * @property label Label of a range. Will be displayed inside the shaded region.
 */
data class ZPerformanceMeterRange(
    var low: Int,
    var high: Int,
    @ColorRes var scaleColor: Int? = null,
    @DrawableRes var pointerImage: Int? = R.drawable.ic_chevron_down,
    @ColorRes var pointerColor: Int? = R.color.phantom_grey_08,
    var label: String? = null,
) {
    companion object {
        val defaultRanges = listOf(
            ZPerformanceMeterRange(
                low = 0,
                high = 20,
                scaleColor = R.color.fire_red_06,
                pointerColor = R.color.fire_red_06,
            ),
            ZPerformanceMeterRange(
                low = 20,
                high = 80,
                scaleColor = R.color.sunrise_yellow_04,
                pointerColor = R.color.sunrise_yellow_04,
            ),
            ZPerformanceMeterRange(
                low = 80,
                high = 100,
                scaleColor = R.color.ever_green_06,
                pointerColor = R.color.ever_green_06,
            )
        )
    }
}
