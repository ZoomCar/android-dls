package com.zoomcar.uikit.driverscore.performancemeter

data class ZPerformanceMeterUIModel(
    val items: List<RankScale>? = RankScale.defaultRankScales,
    val score: Int,
    val category: ScoreCategoryType,
)
