package com.zoomcar.uikit.arcview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.core.content.ContextCompat
import com.zoomcar.util.UiUtil
import com.zoomcar.zoomdls.R

/*
  * @created 21/09/21 - 5:11 pm
  * @project Zoomcar
  * @author Rohil
  * Copyright (c) 2021 Zoomcar. All rights reserved.
*/
class ArcView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val foregroundPaint by lazy { Paint() }
    private val backgroundPaint by lazy { Paint() }

    var strokeWidth: Float = 0f

    var progressValue: Int

    var startColor: String?

    var endColor: String?

    var animationDuration: Int

    var autoAnimate: Boolean

    /* Right center = 0 / 360
    * Bottom center = 90
    * Left center = 180
    * Top Center = 270
    * Our Range varies from 135 to 405 Degree i.e 270 Degrees in total. So 1 progress
    * point is equal to 2.7 degrees */
    private var finalFillAngleFromStart = 0f

    /* Right center = 0.0 / 1.0
    * Bottom center = 0.25
    * Left center = 0.50
    * Top Center = 0.75 */
    private var gradientEndPosition = 0f

    private var viewWidth = -1

    private var viewHeight = -1

    private val widthWithoutStroke by lazy {
        viewWidth - (strokeWidth * 2)
    }

    private val heightWithoutStroke by lazy {
        viewHeight - (strokeWidth * 2)
    }

    private val rect by lazy {
        RectF(
            strokeWidth, strokeWidth, widthWithoutStroke + strokeWidth,
            heightWithoutStroke + strokeWidth
        )
    }

    private val fillAnimation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            super.applyTransformation(interpolatedTime, t)
            val angle = currentFillAngle +
                    ((finalFillAngleFromStart) - currentFillAngle) * interpolatedTime
            this@ArcView.currentFillAngle = angle
            this@ArcView.requestLayout()
        }
    }.apply {
        interpolator = AccelerateDecelerateInterpolator()
    }

    var currentFillAngle = 0f

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcView)
        strokeWidth = UiUtil.dpToPixels(
            typedArray.getInt(
                R.styleable.ArcView_av_stroke_width,
                DEFAULT_STROKE_WIDTH
            ), context
        ).toFloat()
        progressValue = typedArray.getInt(R.styleable.ArcView_av_progress_value, 0)
        startColor = typedArray.getString(R.styleable.ArcView_av_start_color)
            ?: DEFAULT_START_COLOR
        endColor = typedArray.getString(R.styleable.ArcView_av_end_color)
            ?: DEFAULT_END_COLOR
        animationDuration = typedArray.getInt(
            R.styleable.ArcView_av_animation_duration,
            DEFAULT_ANIMATION_DURATION
        )
        autoAnimate = typedArray.getBoolean(
            R.styleable.ArcView_av_auto_animate,
            DEFAULT_AUTO_ANIMATE
        )
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (areDimensionsInvalid()) {
            viewWidth = measuredWidth
            viewHeight = measuredHeight
            initCanvasObjects()
        }
    }

    fun updateProgressValue(newValue: Int) {
        progressValue = newValue
        finalFillAngleFromStart = (progressValue * 2.7).toFloat()
        gradientEndPosition = ((finalFillAngleFromStart) / 360)
        requestLayout()
    }

    fun updateAnimationDuration(newValue: Int) {
        animationDuration = newValue
        fillAnimation.duration = animationDuration.toLong()
    }

    fun initCanvasObjects() {
        if (areDimensionsInvalid()) {
            return
        }

        foregroundPaint.let {
            it.isAntiAlias = true
            it.style = Paint.Style.STROKE
            it.strokeWidth = strokeWidth
        }

        backgroundPaint.let {
            it.isAntiAlias = true
            it.style = Paint.Style.STROKE
            it.strokeWidth = strokeWidth
            it.color = ContextCompat.getColor(context, R.color.phantom_grey_02)
        }

        val colors = intArrayOf(Color.parseColor(startColor), Color.parseColor(endColor))
        val positions = floatArrayOf(0.0f, gradientEndPosition)
        val sweepGradient = SweepGradient(
            widthWithoutStroke / 2, heightWithoutStroke / 2,
            colors, positions
        )
        sweepGradient.apply {
            val rotate = START_ANGLE_POINT
            val gradientMatrix = Matrix()
            gradientMatrix.preRotate(rotate, (viewWidth / 2).toFloat(), (viewHeight / 2).toFloat())
            setLocalMatrix(gradientMatrix)
        }
        foregroundPaint.shader = sweepGradient

//        if (autoAnimate) {
//            animateArc()
//        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawArc(
            rect, START_ANGLE_POINT, END_ANGLE_POINT_FROM_START, false,
            backgroundPaint
        )
        canvas.drawArc(rect, START_ANGLE_POINT, finalFillAngleFromStart, false, foregroundPaint)
    }

    fun animateArc() {
        this.startAnimation(fillAnimation)
    }

    private fun areDimensionsInvalid() = viewWidth == -1 || viewHeight == -1

    companion object {
        private const val START_ANGLE_POINT = 135f
        private const val END_ANGLE_POINT_FROM_START = 270f
        const val DEFAULT_ANIMATION_DURATION = 500

        const val DEFAULT_START_COLOR = "#0b7a07" //R.color.ever_green_07
        const val DEFAULT_END_COLOR = "#10a310" //R.color.ever_green_06
        const val DEFAULT_STROKE_WIDTH = 15
        const val DEFAULT_AUTO_ANIMATE = true
    }
}
