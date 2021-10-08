package com.zoomcar.uikit.driverscore.performancemeter

/**
 * UI model for performance meter.
 *
 * @property performanceMeterRanges
 * @property score Score to be indicated on performance meter.
 */
data class ZPerformanceMeterUIModel(
    val performanceMeterRanges: List<PerformanceMeterRange> = PerformanceMeterRange.defaultRanges,
    val score: Int,
)
