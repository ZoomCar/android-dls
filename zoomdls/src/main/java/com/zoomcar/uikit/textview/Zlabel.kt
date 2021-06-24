package com.zoomcar.uikit.textview

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.AttrRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.databinding.DataBindingUtil
import com.zoomcar.uikit.disclaimer.ZDisclaimerView
import com.zoomcar.zoomdls.R
import com.zoomcar.zoomdls.databinding.LayoutZLabelBinding
import kotlinx.android.parcel.Parcelize

/*
  * @created 23/06/2021 - 6:56 AM
  * @project Zoomcar
  * @author Shishir
  * Copyright (c) 2021 Zoomcar. All rights reserved.
*/
class Zlabel : ConstraintLayout {
    private var binding: LayoutZLabelBinding

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

    init {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_z_label, this, true)
    }

    fun setData(data: ZlabelUiModel) {
        setStyle(data.status)
        binding.textLabel.text = data.text
    }

    private fun setStyle(status: ZDisclaimerView.DisclaimerType?) {
        when (status) {
            ZDisclaimerView.DisclaimerType.INFO -> {
                TextViewCompat.setTextAppearance(
                    binding.textLabel,
                    R.style.CaptionTintedMidnightBlue
                )
                binding.textLabel.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.background_midnightblue01_corner_radius_4dp
                )
            }
            ZDisclaimerView.DisclaimerType.WARNING -> {
                TextViewCompat.setTextAppearance(
                    binding.textLabel,
                    R.style.CaptionTintedSunriseYellow
                )
                binding.textLabel.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.background_sunrise_yellow_corner_radius_4dp
                )
            }
            ZDisclaimerView.DisclaimerType.ERROR -> {
                TextViewCompat.setTextAppearance(binding.textLabel, R.style.CaptionTintedFireRed)
                binding.textLabel.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.background_fire_red_corner_radius_4dp
                )
            }
            else -> {
                TextViewCompat.setTextAppearance(binding.textLabel, R.style.OverlineSecondary)
                binding.textLabel.background = ContextCompat.getDrawable(
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