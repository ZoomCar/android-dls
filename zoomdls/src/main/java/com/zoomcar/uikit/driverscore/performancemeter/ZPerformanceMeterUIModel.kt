package com.zoomcar.uikit.driverscore.performancemeter

/**
 * UI model for performance meter.
 *
 * @property rankScales
 * @property score Score to be indicated on performance meter.
 */
data class ZPerformanceMeterUIModel(
    val rankScales: List<RankScale> = RankScale.defaultRankScales,
    val score: Int,
)
