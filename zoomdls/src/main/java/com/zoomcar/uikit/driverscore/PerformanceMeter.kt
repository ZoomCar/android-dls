package com.zoomcar.uikit.driverscore

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import kotlinx.android.parcel.Parcelize

/**
 * Originally authored by Shishir.
 * Modified by Gideon Paul.
 */
class PerformanceMeter @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


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
