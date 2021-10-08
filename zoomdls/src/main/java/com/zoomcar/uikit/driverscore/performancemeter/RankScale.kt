package com.zoomcar.uikit.driverscore.performancemeter

import androidx.annotation.ColorRes
import com.zoomcar.zoomdls.R

data class RankScale(
    var low: Int,
    var high: Int,
    @ColorRes var scaleColor: Int? = null,
    @ColorRes var pointerColor: Int? = R.color.phantom_grey_08,
    var category: String? = null,
    var text: String? = null,
) {
    companion object {
        val defaultRankScales = listOf(
            RankScale(
                low = 0,
                high = 20,
                scaleColor = R.color.fire_red_06,
                pointerColor = R.color.fire_red_06,
                category = ScoreCategoryType.BAD.category
            ),
            RankScale(
                low = 20,
                high = 80,
                scaleColor = R.color.sunrise_yellow_04,
                pointerColor = R.color.sunrise_yellow_04,
                category = ScoreCategoryType.AVERAGE.category
            ),
            RankScale(
                low = 80,
                high = 100,
                scaleColor = R.color.ever_green_06,
                pointerColor = R.color.ever_green_06,
                category = ScoreCategoryType.GOOD.category
            )
        )
    }
}
