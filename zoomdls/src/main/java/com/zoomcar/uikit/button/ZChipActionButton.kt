package com.zoomcar.uikit.button

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.AttrRes
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.ImageViewCompat
import androidx.core.widget.TextViewCompat
import com.zoomcar.util.loadImage
import com.zoomcar.zoomdls.R
import com.zoomcar.zoomdls.databinding.LayoutZButtonDarkBinding
import kotlinx.android.parcel.Parcelize

/**
 * @created 02/03/2021 - 10:30 AM
 * @project Zoomcar
 * @author Shishir
 * Copyright (c) 2021 Zoomcar. All rights reserved.
 */

class ZChipActionButton : ConstraintLayout {
    private val binding: LayoutZButtonDarkBinding
    private lateinit var type: ChipButtonType

    constructor(context: Context) : super(context)

    constructor(context: Context, type: ChipButtonType) : super(context) {
        this.type = type
    }

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
        binding = LayoutZButtonDarkBinding.inflate(inflater, this, true)
        refreshStyle()
    }

    private fun setZAttributes(attrs: AttributeSet?) {
        attrs?.let {
            val a: TypedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.ZChipActionButton, 0, 0
            )

            type = ChipButtonType.valueOf(a.getInt(R.styleable.ZChipActionButton_type, 0))
                ?: ChipButtonType.DARK
            a.recycle()
        }
    }

    fun setData(data: ZChipActionButtonUiModel) {
        data.iconRes?.let {
            binding.imageIcon.setImageDrawable(ContextCompat.getDrawable(context, it))
        }
        data.iconUrl?.let {
            binding.imageIcon.loadImage(it)
        }
        binding.textName.text = data.text
        binding.viewDotHighlight.isVisible = data.isHighlighted
    }

    private fun refreshStyle() {
        when (type) {
            ChipButtonType.DARK -> {
                binding.apply {
                    val bgColor = ContextCompat.getColor(context, R.color.phantom_grey_10)
                    val bgColorState = ColorStateList.valueOf(bgColor)
                    rootButton.setCardBackgroundColor(bgColorState)

                    textName.apply {
                        TextViewCompat.setTextAppearance(this, R.style.Button2Inverse)
                    }
                }
            }
            ChipButtonType.LIGHT -> {
                binding.apply {
                    val bgColor = ContextCompat.getColor(context, R.color.white)
                    val bgColorState = ColorStateList.valueOf(bgColor)
                    rootButton.setCardBackgroundColor(bgColorState)

                    textName.apply {
                        TextViewCompat.setTextAppearance(this, R.style.Button2Primary)
                    }
                }
            }
            ChipButtonType.TRANSIENT_DARK -> {
                binding.apply {
                    val bgColor = ContextCompat.getColor(context, R.color.black)
                    val bgColorState = ColorStateList.valueOf(bgColor)
                    rootButton.setCardBackgroundColor(bgColorState)

                    textName.apply {
                        TextViewCompat.setTextAppearance(this, R.style.Button2Inverse)
                    }
                    ImageViewCompat.setImageTintList(
                        imageIcon,
                        ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white))
                    )
                }
            }
            ChipButtonType.TRANSIENT_LIGHT -> {
                binding.apply {
                    val bgColor = ContextCompat.getColor(context, R.color.white)
                    val bgColorState = ColorStateList.valueOf(bgColor)
                    rootButton.setCardBackgroundColor(bgColorState)

                    textName.apply {
                        TextViewCompat.setTextAppearance(this, R.style.Button2Primary)
                    }
                    ImageViewCompat.setImageTintList(
                        imageIcon,
                        ColorStateList.valueOf(ContextCompat.getColor(context, R.color.black))
                    )
                }
            }
        }
    }

    fun setStyle(type: ChipButtonType) {
        this.type = type
        refreshStyle()
    }

    @Parcelize
    data class ZChipActionButtonUiModel(
        @DrawableRes val iconRes: Int? = null,
        val iconUrl: String? = null,
        val text: String? = null,
        val isHighlighted: Boolean = false,
    ) : Parcelable

    enum class ChipButtonType {
        LIGHT,
        DARK,
        TRANSIENT_DARK,
        TRANSIENT_LIGHT;

        companion object {
            fun valueOf(value: Int?) = values().firstOrNull {
                it.ordinal == value
            }
        }
    }
}