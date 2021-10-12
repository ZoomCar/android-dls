package com.zoomcar.uikit.arcview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.zoomcar.uikit.textview.ZTextView
import com.zoomcar.util.UiUtil
import com.zoomcar.zoomdls.R

/*
  * @created 21/09/21 - 5:31 pm
  * @project Zoomcar
  * @author Rohil
  * Copyright (c) 2021 Zoomcar. All rights reserved.
*/
class ArcProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyle, defStyleRes) {

    private var arcView: ArcView
    private var progressValueTextView: ZTextView
    private var subTextView: ZTextView

    var strokeWidth: Int
    var progressValue: Int
    var startColor: String?
    var endColor: String?
    var animationDuration: Int
    private var autoAnimate: Boolean
    private var subText: String

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_arc_progress_view, this, true)
        arcView = findViewById(R.id.arc_view)
        progressValueTextView = findViewById(R.id.progress_value)
        subTextView = findViewById(R.id.sub_text)


        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcProgressView)
        strokeWidth = typedArray.getInt(
            R.styleable.ArcProgressView_apv_stroke_width,
            ArcView.DEFAULT_STROKE_WIDTH
        )
        progressValue = typedArray.getInt(
            R.styleable.ArcProgressView_apv_progress_value,
            0
        )
        startColor = typedArray.getString(R.styleable.ArcProgressView_apv_start_color)
            ?: ArcView.DEFAULT_START_COLOR
        endColor = typedArray.getString(R.styleable.ArcProgressView_apv_end_color)
            ?: ArcView.DEFAULT_END_COLOR
        animationDuration = typedArray.getInt(
            R.styleable.ArcProgressView_apv_animation_duration,
            ArcView.DEFAULT_ANIMATION_DURATION
        )
        subText = typedArray.getString(R.styleable.ArcProgressView_apv_sub_text) ?: "SAMPLE"
        autoAnimate = typedArray.getBoolean(
            R.styleable.ArcProgressView_apv_auto_animate,
            true
        )
        typedArray.recycle()
        populateValueAndSubText()
    }

    private fun populateValueAndSubText() {
        progressValueTextView.text = progressValue.toString()
        subTextView.text = subText
    }

    fun updateProgressValue(newValue: Int) {
        progressValue = newValue
        progressValueTextView.text = progressValue.toString()
    }

    fun updateSubText(newText: String) {
        subText = newText
        subTextView.text = subText
    }

    fun updateArcColor(startColorArc: String, endColorArc: String) {
        startColor = startColorArc
        endColor = endColorArc
    }

    fun initArc(): ArcProgressView {
        arcView.apply {
            strokeWidth = UiUtil.dpToPixels(this@ArcProgressView.strokeWidth, context).toFloat()
            startColor = this@ArcProgressView.startColor
            endColor = this@ArcProgressView.endColor
            updateProgressValue(this@ArcProgressView.progressValue)
            updateAnimationDuration(this@ArcProgressView.animationDuration)
            initCanvasObjects()
//            animateArc()
        }
        return this
    }

    fun animateArc() {
        arcView.animateArc()
    }
}