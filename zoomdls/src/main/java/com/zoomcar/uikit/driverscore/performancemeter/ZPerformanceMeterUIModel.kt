package com.zoomcar.uikit.driverscore.performancemeter

import androidx.annotation.DrawableRes
import com.zoomcar.zoomdls.R

/**
 * UI model for performance meter.
 *
 * @property rankScales
 * @property score Score to be indicated on performance meter.
 * @property pointerDrawableRes Drawable for pointer to be displayed on meter.
 * @property tintPointer Indicates if the pointer should be tinted.
 */
data class ZPerformanceMeterUIModel(
    val rankScales: List<RankScale> = RankScale.defaultRankScales,
    val score: Int,
    @DrawableRes val pointerDrawableRes: Int? = R.drawable.ic_chevron_down,
    val tintPointer: Boolean = true,
)
