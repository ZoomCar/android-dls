package com.zoomcar.uikit.performancemeter

import android.content.Context
import android.graphics.Canvas
import android.graphics.CornerPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.zoomcar.util.getNullCheck
import com.zoomcar.zoomdls.R

/**
 * Performance meter custom view.
 * Can display scores ranging from 0 to 100.
 *
 * @created 07/10/2021 - 1:00 PM
 * @project Zoomcar
 * @author Shishir
 * @author Gideon Paul
 *
 * Copyright (c) 2021 Zoomcar. All rights reserved.
 */
// Originally authored by Shishir.
//Moved to DLS by Gideon Paul.
class ZPerformanceMeter @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var data: ZPerformanceMeterUIModel? = null

    private var path: Path = Path()

    private val marginTopBottomForMeter =
        context.resources.getDimension(R.dimen.meter_top_bottom_margin)
    private val marginTopForPointer =
        context.resources.getDimension(R.dimen.meter_top_margin_for_pointer)
    private val pointerSize = context.resources.getDimensionPixelSize(R.dimen.meter_pointer_size)
    private val barMeterHeight = context.resources.getDimension(R.dimen.bar_meter_height)
    private val cornerRadius = context.resources.getDimension(R.dimen.meter_corner_radius)

    private val cornerEffect = CornerPathEffect(cornerRadius)

    // Paint for filling ranges inside colored bar.
    private val rankScalePaint =
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
        }

    // Paint for text inside the colored bar
    private val textPaint =
        Paint().apply {
            isAntiAlias = true
            color = ContextCompat.getColor(context, R.color.white)
            textAlign = Paint.Align.CENTER
            textSize = context.resources.getDimension(R.dimen.performance_meter_label_text_size)
        }

    // Paint for text for middle colored bars bottom label as text alignment is needed center
    private val labelPaint =
        Paint().apply {
            isAntiAlias = true
            color = ContextCompat.getColor(context, R.color.phantom_grey_04)
            textAlign = Paint.Align.CENTER
            textSize = context.resources.getDimension(R.dimen.performance_meter_label_text_size)
        }

    fun setData(data: ZPerformanceMeterUIModel?) {
        data?.let {
            this.data = data
            this.isVisible = data.performanceMeterRanges.getNullCheck()
            invalidate()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val lp = layoutParams.apply {
            height = context.resources.getDimensionPixelSize(R.dimen.performance_meter_height)
        }
        layoutParams = lp
        setWillNotDraw(false)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.let { canvas ->
            data?.performanceMeterRanges?.mapIndexed { index, item ->
                val top = marginTopBottomForMeter
                val bottom = barMeterHeight + marginTopBottomForMeter
                val left = (item.low ?: 0) * 1.0f / 100.0f * measuredWidth.toFloat()
                val right = (item.high ?: 0) * 1.0f / 100.0f * measuredWidth.toFloat()
                path.reset()

                when (index) {
                    0 -> {
                        // Path drawn considering curved edges.
                        path.moveTo(right, top)
                        path.lineTo(left, top)
                        path.lineTo(left, bottom)
                        path.lineTo(right, bottom)
                    }
                    (data?.performanceMeterRanges?.size ?: 0) - 1 -> {
                        // Path drawn considering curved edges.
                        path.moveTo(left, top)
                        path.lineTo(right, top)
                        path.lineTo(right, bottom)
                        path.lineTo(left, bottom)
                    }
                    else -> {
                        path.moveTo(left, top)
                        path.lineTo(right, top)
                        path.lineTo(right, bottom)
                        path.lineTo(left, bottom)
                    }
                }

                // Change the color of the rankScalePaint according to range's color.
                item.scaleColor?.let {
                    rankScalePaint.color = ContextCompat.getColor(context, it)
                }

                // Round the corners if either first or last elements.
                if (index == 0 || index == data?.performanceMeterRanges?.lastIndex) {
                    rankScalePaint.pathEffect = cornerEffect
                } else {
                    rankScalePaint.pathEffect = null
                }

                canvas.drawPath(path, rankScalePaint)

                // Add labels inside bars.
                var xPos = (left + right) / 2
                var yPos = ((top + bottom) / 2) - (textPaint.descent() + textPaint.ascent()) / 2
                canvas.drawText(item.label ?: "", xPos, yPos, textPaint)

                // Add labels below bar.
                yPos = bottom + barMeterHeight * 3 / 5
                if (index == 0) {
                    xPos = left + 2
                    canvas.drawText(item.low.toString(), xPos, yPos, labelPaint.apply {
                        textAlign = Paint.Align.LEFT
                    })
                }
                if (index == (data?.performanceMeterRanges?.size ?: 0) - 1) {
                    xPos = right - 2
                    canvas.drawText(item.high.toString(), xPos, yPos, labelPaint.apply {
                        textAlign = Paint.Align.RIGHT
                    })
                } else {
                    xPos = right
                    canvas.drawText(item.high.toString(), xPos, yPos, labelPaint.apply {
                        textAlign = Paint.Align.CENTER
                    })
                }
            }

            // Add pointer for score.
            val pointerX = width * ((data?.score ?: 0) * 1.0f / 100.0f) - pointerSize / 2
            val pointerY = marginTopForPointer
            canvas.translate(pointerX, pointerY)

            data?.performanceMeterRanges?.forEach { rankScale ->
                data?.score?.let { score ->
                    if (scoreFallsInRange(score, rankScale)) {
                        rankScale.pointerImage?.let {
                            drawPointer(canvas, it, rankScale.pointerColor)
                        }
                    }
                }
            }
            super.onDraw(canvas)
        }
    }

    private fun drawPointer(
        canvas: Canvas,
        @DrawableRes pointerDrawableRes: Int,
        @ColorRes tint: Int?
    ) {
        val pointerDrawable = ContextCompat.getDrawable(context, pointerDrawableRes)
        pointerDrawable?.apply {
            setBounds(0, 0, pointerSize, pointerSize)
            tint?.let {
                setTintToDrawable(this, tint)
            }
            draw(canvas)
        }
    }

    private fun setTintToDrawable(pointer: Drawable, pointerColor: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pointer.setTint(
                ContextCompat.getColor(
                    context,
                    pointerColor
                )
            )
        }
    }

    private fun scoreFallsInRange(score: Int, performanceMeterRange: ZPerformanceMeterRange): Boolean {
        return (score >= performanceMeterRange.low && score <= performanceMeterRange.high)
    }
}
