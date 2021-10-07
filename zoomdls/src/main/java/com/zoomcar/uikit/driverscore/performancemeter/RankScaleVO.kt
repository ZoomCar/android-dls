package com.zoomcar.uikit.driverscore.performancemeter

data class RankScaleVO(
    var low: Int? = null,
    var high: Int? = null,
    var category: String? = null,
    var text: String? = null,
) {
    companion object {
        val defaultRankScales = listOf(
            RankScaleVO(
                low = 0,
                high = 20,
                category = DriverScoreCategoryType.BAD.category
            ),
            RankScaleVO(
                low = 20,
                high = 80,
                category = DriverScoreCategoryType.AVERAGE.category
            ),
            RankScaleVO(
                low = 80,
                high = 100,
                category = DriverScoreCategoryType.GOOD.category
            )
        )
    }
}