package com.zoomcar.uikit.performancemeter

/**
 * UI model for performance meter.
 *
 * @property performanceMeterRanges A list of ranges between 0 to 100.
 * @property score Score to be indicated on performance meter.
 */
data class ZPerformanceMeterUIModel(
    val performanceMeterRanges: List<ZPerformanceMeterRange> = ZPerformanceMeterRange.defaultRanges,
    val score: Int,
)
