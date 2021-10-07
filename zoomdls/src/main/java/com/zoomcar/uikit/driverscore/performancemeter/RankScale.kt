package com.zoomcar.uikit.driverscore.performancemeter

data class RankScale(
    var low: Int? = null,
    var high: Int? = null,
    var category: String? = null,
    var text: String? = null,
) {
    companion object {
        val defaultRankScales = listOf(
            RankScale(
                low = 0,
                high = 20,
                category = ScoreCategoryType.BAD.category
            ),
            RankScale(
                low = 20,
                high = 80,
                category = ScoreCategoryType.AVERAGE.category
            ),
            RankScale(
                low = 80,
                high = 100,
                category = ScoreCategoryType.GOOD.category
            )
        )
    }
}
