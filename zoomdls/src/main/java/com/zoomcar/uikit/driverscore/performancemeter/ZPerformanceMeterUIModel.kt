package com.zoomcar.uikit.driverscore.performancemeter

import androidx.annotation.DrawableRes
import com.zoomcar.zoomdls.R

data class ZPerformanceMeterUIModel(
    val rankScales: List<RankScale> = RankScale.defaultRankScales,
    val score: Int,
    @DrawableRes val pointerDrawableRes: Int? = R.drawable.ic_chevron_down,
    val tintPointer: Boolean = true,
    val category: ScoreCategoryType = ScoreCategoryType.UNKNOWN,
)
