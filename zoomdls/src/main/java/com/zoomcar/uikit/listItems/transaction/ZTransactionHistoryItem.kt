package com.zoomcar.uikit.listItems.transaction

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.AttrRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.widget.TextViewCompat
import androidx.databinding.DataBindingUtil
import com.zoomcar.util.getNullCheck
import com.zoomcar.zoomdls.R
import com.zoomcar.zoomdls.databinding.LayoutZTransactionHistoryItemBinding
import kotlinx.android.parcel.Parcelize

/**
 * @created 20/01/2021 - 4:12 PM
 * @project Zoomcar
 * @author Shishir
 * Copyright (c) 2021 Zoomcar. All rights reserved.
 */
class ZTransactionHistoryItem : ConstraintLayout {
    private var binding: LayoutZTransactionHistoryItemBinding

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
        binding = LayoutZTransactionHistoryItemBinding.inflate(inflater, this, true)
    }

    fun setData(model: ZtransactionHistoryItemUIModel) {
        binding.textDayOfMonth.apply {
            isVisible = model.date.getNullCheck()
            text = model.date
        }
        binding.textMonth.apply {
            isVisible = model.month.getNullCheck()
            text = model.month
        }
        binding.textLabel.apply {
            isVisible = model.label.getNullCheck()
            text = model.label
        }
        binding.textLabelDescription.apply {
            isVisible = model.labelDescription.getNullCheck()
            text = model.labelDescription
        }
        binding.textValue.apply {
            isVisible = model.value.getNullCheck()
            text = model.value
        }
        binding.textValueDescription.apply {
            isVisible = model.valueDescription.getNullCheck()
            text = model.valueDescription
        }

        model.type?.let { type ->
            when (type) {
                ValueType.HIGHLIGHTED_GREEN -> {
                    TextViewCompat.setTextAppearance(binding.textValue, R.style.Title1TintedEvergreen)
                }
                ValueType.HIGHLIGHTED_RED -> {
                    TextViewCompat.setTextAppearance(binding.textValue, R.style.Title1TintedFireRed)
                }
                else -> {
                    TextViewCompat.setTextAppearance(binding.textValue, R.style.Title1Primary)
                }
            }
        }
    }

    @Parcelize
    data class ZtransactionHistoryItemUIModel(
            var label: String? = null,
            var labelDescription: String? = null,
            var value: String? = null,
            var valueDescription: String? = null,
            var date: String? = null,
            var month: String? = null,
            var type: ValueType? = null
    ) : Parcelable

    enum class ValueType {
        HIGHLIGHTED_GREEN,
        HIGHLIGHTED_RED,
        NORMAL;
        companion object {
            fun fromType(type: String) = values().first { it.name == type }
        }
    }
}