package com.zoomcar.uikit.driverscore.performancemeter

import androidx.annotation.ColorRes
import com.zoomcar.zoomdls.R

data class RankScale(
    var low: Int? = null,
    var high: Int? = null,
    @ColorRes var color: Int? = null,
    var category: String? = null,
    var text: String? = null,
) {
    companion object {
        val defaultRankScales = listOf(
            RankScale(
                low = 0,
                high = 20,
                color = R.color.fire_red_06,
                category = ScoreCategoryType.BAD.category
            ),
            RankScale(
                low = 20,
                high = 80,
                color = R.color.sunrise_yellow_04,
                category = ScoreCategoryType.AVERAGE.category
            ),
            RankScale(
                low = 80,
                high = 100,
                color = R.color.ever_green_06,
                category = ScoreCategoryType.GOOD.category
            )
        )
    }
}
