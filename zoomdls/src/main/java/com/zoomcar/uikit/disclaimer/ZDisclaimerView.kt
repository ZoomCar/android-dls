package com.zoomcar.uikit.disclaimer

import android.content.Context
import android.content.res.TypedArray
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.AttrRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.zoomcar.util.getNullCheck
import com.zoomcar.zoomdls.R
import com.zoomcar.zoomdls.databinding.LayoutZDisclaimerBinding
import kotlinx.android.parcel.Parcelize

/**
 * @created 20/09/2020 - 7:24 PM
 * @project Zoomcar
 * @author Shishir
 * Copyright (c) 2020 Zoomcar. All rights reserved.
 */
class ZDisclaimerView : ConstraintLayout {
    private val binding: LayoutZDisclaimerBinding
    private var disclaimerType: DisclaimerType = DisclaimerType.INFO

    constructor(context: Context) : super(context)

    constructor(context: Context,
                attrs: AttributeSet?
    ) : super(context, attrs) {
        setZAttributes(attrs)
    }

    constructor(
            context: Context,
            attrs: AttributeSet,
            @AttrRes defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr) {
        setZAttributes(attrs)
    }

    init {
        val inflater = LayoutInflater.from(context)
        binding = LayoutZDisclaimerBinding.inflate(inflater, this, true)
    }

    private fun setZAttributes(attrs: AttributeSet?) {
        attrs?.let {
            val a: TypedArray = context.obtainStyledAttributes(attrs,
                    R.styleable.ZDisclaimerView, 0, 0)

            disclaimerType = DisclaimerType.fromValue(a.getInt(R.styleable.ZDisclaimerView_disclaimer_type, 0))
        }
    }

    fun setData(data: ZDisclaimerUiModel) {
        when (data.disclaimerType) {
            DisclaimerType.INFO -> binding.disclaimerContainer.background = ContextCompat.getDrawable(context, R.drawable.background_midnightblue01_corner_radius_4dp)
            DisclaimerType.DEBUG -> binding.disclaimerContainer.background = ContextCompat.getDrawable(context, R.drawable.background_zoom_grey_corner_radius_4dp)
            DisclaimerType.WARNING -> binding.disclaimerContainer.background = ContextCompat.getDrawable(context, R.drawable.background_sunrise_yellow_corner_radius_4dp)
        }
        binding.textDisclaimierTitle.apply {
            isVisible = data.disclaimerTitle.getNullCheck()
            text = data.disclaimerTitle
        }
        binding.textDisclaimer.apply {
            isVisible = data.disclaimer.getNullCheck()
            text = data.disclaimer
        }
    }

    @Parcelize
    data class ZDisclaimerUiModel(
            var disclaimerTitle: String? = null,
            var disclaimer: String? = null,
            var disclaimerType: DisclaimerType? = DisclaimerType.INFO
    ) : Parcelable

    enum class DisclaimerType {
        INFO,
        WARNING,
        DEBUG;

        companion object {
            fun fromValue(type: Int) = values().first { it.ordinal == type }
        }
    }
}