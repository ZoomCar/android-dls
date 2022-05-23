package com.zoomcar.uikit.disclaimer

import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.AttrRes
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.TextViewCompat
import com.zoomcar.util.getNullCheck
import com.zoomcar.util.loadImage
import com.zoomcar.zoomdls.R
import com.zoomcar.zoomdls.databinding.LayoutZDisclaimerBinding
import kotlinx.parcelize.Parcelize

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

    constructor(
        context: Context,
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
            val a: TypedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.ZDisclaimerView, 0, 0
            )

            disclaimerType =
                DisclaimerType.fromValue(a.getInt(R.styleable.ZDisclaimerView_disclaimer_type, 0))
        }
    }

    fun setData(data: ZDisclaimerUiModel) {
        when (data.disclaimerType) {
            DisclaimerType.INFO -> {
                setDisclaimerUiStyle(
                    R.style.CaptionTintedMidnightBlue,
                    R.style.CaptionTintedMidnightBlue,
                    R.drawable.background_midnightblue01_corner_radius_4dp
                )
            }
            DisclaimerType.DEBUG -> {
                setDisclaimerUiStyle(
                    R.style.OverlineSecondary,
                    R.style.CaptionPrimary,
                    R.drawable.background_zoom_grey_corner_radius_4dp
                )
            }
            DisclaimerType.WARNING -> {
                setDisclaimerUiStyle(
                    R.style.CaptionTintedSunriseYellow,
                    R.style.CaptionTintedSunriseYellow,
                    R.drawable.background_sunrise_yellow_corner_radius_4dp
                )
            }
            DisclaimerType.ERROR -> {
                setDisclaimerUiStyle(
                    R.style.CaptionTintedFireRed,
                    R.style.CaptionTintedFireRed,
                    R.drawable.background_fire_red_corner_radius_4dp
                )
            }
            DisclaimerType.SUCCESS -> {
                setDisclaimerUiStyle(
                    R.style.CaptionTintedEvergreen,
                    R.style.CaptionTintedEvergreen,
                    R.drawable.background_evergreen_corner_radius_4dp
                )
            }
        }
        binding.textDisclaimierTitle.apply {
            isVisible = data.disclaimerTitle.getNullCheck()
            text = data.disclaimerTitle
        }
        binding.textDisclaimer.apply {
            isVisible = data.disclaimer.getNullCheck()
            text = data.disclaimer
        }
        binding.imageDisclaimer.apply {
            isVisible = data.disclaimerImage.getNullCheck()
            data.disclaimerImage?.let { image ->
                setImageResource(image)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    data.disclaimerType?.let { type -> getColorFromType(type) }?.let { color ->
                        ContextCompat.getColor(
                            context,
                            color
                        )
                    }?.let { binding.imageDisclaimer.drawable.setTint(it) }
                }
            }

        }
    }

    private fun getColorFromType(type: DisclaimerType): Int {
        return when (type) {
            DisclaimerType.INFO -> R.color.midnight_blue_06
            DisclaimerType.WARNING -> R.color.sunrise_yellow_06
            DisclaimerType.ERROR -> R.color.fire_red_06
            DisclaimerType.SUCCESS -> R.color.ever_green_06
            else -> R.color.phantom_grey_10
        }
    }

    private fun setDisclaimerUiStyle(titleStyle: Int, descriptionStyle: Int, background: Int) {
        TextViewCompat.setTextAppearance(
            binding.textDisclaimierTitle,
            titleStyle
        )
        TextViewCompat.setTextAppearance(
            binding.textDisclaimer,
            descriptionStyle
        )
        binding.disclaimerContainer.background = ContextCompat.getDrawable(
            context,
            background
        )
    }

    @Parcelize
    data class ZDisclaimerUiModel(
        var disclaimerTitle: String? = null,
        var disclaimer: String? = null,
        var disclaimerType: DisclaimerType? = DisclaimerType.INFO,
        var disclaimerImage: Int? = null,
    ) : Parcelable {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as ZDisclaimerUiModel

            if (disclaimerTitle != other.disclaimerTitle) return false
            if (disclaimer != other.disclaimer) return false
            if (disclaimerType != other.disclaimerType) return false

            return true
        }

        override fun hashCode(): Int {
            var result = disclaimerTitle?.hashCode() ?: 0
            result = 31 * result + (disclaimer?.hashCode() ?: 0)
            result = 31 * result + (disclaimerType?.hashCode() ?: 0)
            return result
        }
    }

    enum class DisclaimerType {
        INFO,
        WARNING,
        DEBUG,
        ERROR,
        SUCCESS;

        companion object {
            fun fromValue(type: Int) = values().first { it.ordinal == type }
            fun fromValue(type: String?) = values().firstOrNull { it.name == type }
        }
    }
}