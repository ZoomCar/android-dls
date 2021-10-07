package com.zoomcar.uikit.driverscore

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import com.zoomcar.zoomdls.R
import kotlinx.android.parcel.Parcelize

/**
 * Originally authored by Shishir.
 * Modified by Gideon Paul.
 */
class PerformanceMeter @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val marginTopBottomForMeter = context.resources.getDimension(R.dimen.meter_top_bottom_margin)
    private val marginTopForPointer = context.resources.getDimension(R.dimen.meter_top_margin_for_pointer)
    private val pointerSize = context.resources.getDimensionPixelSize(R.dimen.meter_pointer_size)
    private val barMeterHeight = context.resources.getDimension(R.dimen.bar_meter_height)
    private val cornerRadius = context.resources.getDimension(R.dimen.meter_corner_radius)
}


// Value objects.

@Parcelize
data class RankScaleVO(
    var low: Int? = null,
    var high: Int? = null,
    var category: String? = null,
    var text: String? = null,
) : Parcelable

@Parcelize
data class ZMeterUIModel(
    val items: List<RankScaleVO>? = null,
    val score: Int,
    val category: DriverScoreCategoryType,
) : Parcelable

object DriverScoreCategoryKey {
    const val GOOD = "good"
    const val AVERAGE = "average"
    const val BAD = "bad"
    const val UNKNOWN = "unknown"
}

enum class DriverScoreCategoryType(val category: String) {
    GOOD(DriverScoreCategoryKey.GOOD),
    AVERAGE(DriverScoreCategoryKey.AVERAGE),
    BAD(DriverScoreCategoryKey.BAD),
    UNKNOWN(DriverScoreCategoryKey.UNKNOWN);

    companion object {
        fun fromType(category: String?) = try {
            values().first { it.category == category }
        } catch (e: IllegalArgumentException) {
            UNKNOWN
        } catch (e: NoSuchElementException) {
            UNKNOWN
        }
    }
}
