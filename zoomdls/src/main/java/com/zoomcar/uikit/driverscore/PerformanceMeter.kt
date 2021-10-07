package com.zoomcar.uikit.driverscore

import android.content.Context
import android.graphics.CornerPathEffect
import android.graphics.Paint
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.zoomcar.util.getNullCheck
import com.zoomcar.zoomdls.R
import kotlinx.android.parcel.Parcelize

/**
 * Originally authored by Shishir.
 * Modified by Gideon Paul.
 */
class PerformanceMeter @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var data: ZMeterUIModel? = null

    private val marginTopBottomForMeter =
        context.resources.getDimension(R.dimen.meter_top_bottom_margin)
    private val marginTopForPointer =
        context.resources.getDimension(R.dimen.meter_top_margin_for_pointer)
    private val pointerSize = context.resources.getDimensionPixelSize(R.dimen.meter_pointer_size)
    private val barMeterHeight = context.resources.getDimension(R.dimen.bar_meter_height)
    private val cornerRadius = context.resources.getDimension(R.dimen.meter_corner_radius)

    private val cornerEffect = CornerPathEffect(cornerRadius)

    //red paint for bad category
    private val redPaint =
        Paint().apply {
            isAntiAlias = true
            color = ContextCompat.getColor(context, R.color.fire_red_06)
            style = Paint.Style.FILL
            //added for curved edges
            pathEffect = cornerEffect;
        }

    //orange paint for average category
    private val orangePaint =
        Paint().apply {
            isAntiAlias = true
            color = ContextCompat.getColor(context, R.color.sunrise_yellow_04)
            style = Paint.Style.FILL
        }

    //green paint for good category
    private val greenPaint =
        Paint().apply {
            isAntiAlias = true
            color = ContextCompat.getColor(context, R.color.ever_green_06)
            style = Paint.Style.FILL
            //added for curved edges
            pathEffect = cornerEffect;
        }

    //Paint for text inside the colored bar
    private val textPaint =
        Paint().apply {
            isAntiAlias = true
            color = ContextCompat.getColor(context, R.color.white)
            textAlign = Paint.Align.CENTER
            textSize = context.resources.getDimension(R.dimen.performance_meter_label_text_size)
        }

    //Paint for text for middle colored bars bottom label as text alignment is needed center
    private val labelPaint =
        Paint().apply {
            isAntiAlias = true
            color = ContextCompat.getColor(context, R.color.phantom_grey_04)
            textAlign = Paint.Align.CENTER
            textSize = context.resources.getDimension(R.dimen.performance_meter_label_text_size)
        }

    fun setData(data: ZMeterUIModel?) {
        data?.let {
            this.data = data
            this.isVisible = data.items.getNullCheck()
            invalidate()
        }
    }
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
