package com.zoomcar.uikit.button

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.AttrRes
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.zoomcar.util.loadImage
import com.zoomcar.zoomdls.databinding.LayoutZButtonDarkBinding
import kotlinx.android.parcel.Parcelize

/**
 * @created 02/03/2021 - 10:30 AM
 * @project Zoomcar
 * @author Shishir
 * Copyright (c) 2021 Zoomcar. All rights reserved.
 */

class ZChipButtonDark : ConstraintLayout {
    private val binding: LayoutZButtonDarkBinding

    constructor(context: Context) : super(context)

    constructor(context: Context,
                attrs: AttributeSet?
    ) : super(context, attrs)

    constructor(
            context: Context,
            attrs: AttributeSet,
            @AttrRes defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr)

    init {
        val inflater = LayoutInflater.from(context)
        binding = LayoutZButtonDarkBinding.inflate(inflater, this, true)
    }

    fun setData(data: ZChipButtonDarkUiModel) {
        data.iconRes?.let {
            binding.imageIcon.setImageDrawable(ContextCompat.getDrawable(context, it))
        }
        data.iconUrl?.let {
            binding.imageIcon.loadImage(it)
        }
        binding.textName.text = data.text
        binding.viewDotHighlight.isVisible = data.isHighlighted
    }

    @Parcelize
    data class ZChipButtonDarkUiModel(
            @DrawableRes val iconRes: Int? = null,
            val iconUrl: String? = null,
            val text: String? = null,
            val isHighlighted: Boolean = false,
    ) : Parcelable
}