package com.zoomcar.uikit.textview

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import androidx.annotation.AttrRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.TextViewCompat
import com.zoomcar.uikit.disclaimer.ZDisclaimerView
import com.zoomcar.util.UiUtil
import com.zoomcar.util.isValid
import com.zoomcar.zoomdls.R
import kotlinx.android.parcel.Parcelize

/*
  * @created 23/06/2021 - 6:56 AM
  * @project Zoomcar
  * @author Shishir
  * Copyright (c) 2021 Zoomcar. All rights reserved.
*/
class Zlabel : ZTextView {
    constructor(context: Context) : super(context)

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs)

    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        @AttrRes defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr)


    fun setData(data: ZlabelUiModel) {
        setStyle(data.status)
        text = data.text
    }

    init {
        setPadding(UiUtil.dpToPixels(8, context), UiUtil.dpToPixels(4, context), UiUtil.dpToPixels(8, context), UiUtil.dpToPixels(4, context))
    }

    private fun setStyle(status: ZDisclaimerView.DisclaimerType?) {
        when (status) {
            ZDisclaimerView.DisclaimerType.INFO -> {
                TextViewCompat.setTextAppearance(
                    this,
                    R.style.CaptionTintedMidnightBlue
                )
                background = ContextCompat.getDrawable(
                    context,
                    R.drawable.background_midnightblue01_corner_radius_4dp
                )
            }
            ZDisclaimerView.DisclaimerType.WARNING -> {
                TextViewCompat.setTextAppearance(
                    this,
                    R.style.CaptionTintedSunriseYellow
                )
                background = ContextCompat.getDrawable(
                    context,
                    R.drawable.background_sunrise_yellow_corner_radius_4dp
                )
            }
            ZDisclaimerView.DisclaimerType.ERROR -> {
                TextViewCompat.setTextAppearance(this, R.style.CaptionTintedFireRed)
                background = ContextCompat.getDrawable(
                    context,
                    R.drawable.background_fire_red_corner_radius_4dp
                )
            }
            ZDisclaimerView.DisclaimerType.SUCCESS -> {
                TextViewCompat.setTextAppearance(this, R.style.CaptionTintedEvergreen)
                background = ContextCompat.getDrawable(
                    context,
                    R.drawable.background_evergreen_corner_radius_4dp
                )
            }
            else -> {
                TextViewCompat.setTextAppearance(this, R.style.OverlineSecondary)
                background = ContextCompat.getDrawable(
                    context,
                    R.drawable.background_zoom_grey_corner_radius_4dp
                )
            }
        }
    }

    @Parcelize
    data class ZlabelUiModel(
        val text: String? = null,
        val status: ZDisclaimerView.DisclaimerType? = null
    ) : Parcelable
}