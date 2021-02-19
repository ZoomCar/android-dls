package com.zoomcar.uikit.optionsItem

import android.content.Context
import android.graphics.Paint
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.AttrRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.zoomcar.util.isValid
import com.zoomcar.zoomdls.R
import com.zoomcar.zoomdls.databinding.LayoutZRectangularSelectionItemBinding
import kotlinx.android.parcel.Parcelize

/**
 * @created 14/02/2021 - 10:44 PM
 * @project Zoomcar
 * @author Shishir
 * Copyright (c) 2021 Zoomcar. All rights reserved.
 */
class ZRectangularSelectionItem : ConstraintLayout {
    private var binding: LayoutZRectangularSelectionItemBinding

    constructor(context: Context) : super(context)

    constructor(context: Context,
                attrs: AttributeSet?
    ) : super(context, attrs)

    constructor(context: Context,
                attrs: AttributeSet? = null,
                @AttrRes defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr)

    init {
        val inflater = LayoutInflater.from(context)
        binding = LayoutZRectangularSelectionItemBinding.inflate(inflater, this, true)
        binding.textStrikedValue.setPaintFlags(binding.textStrikedValue.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)

    }

    fun setData(data: ZRectangularSelectionUiModel) {
        binding.textTag.apply {
            isVisible = data.tag.isValid()
            text = data.tag
        }
        binding.textDesc.text = data.desc
        binding.textValue.text = data.value
        binding.textStrikedValue.text = data.strikedValue
        if (data.selected) {
            binding.card.strokeColor = ContextCompat.getColor(context, R.color.ever_green_06)
        } else {
            binding.card.strokeColor = ContextCompat.getColor(context, R.color.phantom_grey_02)
        }
    }

    @Parcelize
    data class ZRectangularSelectionUiModel(
            val tag: String? = null,
            var selected: Boolean = false,
            val desc: String? = null,
            val value: String? = null,
            val strikedValue: String? = null
    ) : Parcelable
}