package com.zoomcar.uikit.driverscore.performancemeter

data class ZPerformanceMeterUIModel(
    val items: List<RankScaleVO>? = RankScaleVO.defaultRankScales,
    val score: Int,
    val category: DriverScoreCategoryType,
)