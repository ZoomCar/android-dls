package com.zoomcar.uikit.listItems.transaction

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.AttrRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.zoomcar.util.getNullCheck
import com.zoomcar.zoomdls.R
import com.zoomcar.zoomdls.databinding.LayoutZDisclaimerBinding
import com.zoomcar.zoomdls.databinding.LayoutZTransactionHeaderItemBinding
import kotlinx.parcelize.Parcelize

/**
 * @created 20/01/2021 - 4:12 PM
 * @project Zoomcar
 * @author Shishir
 * Copyright (c) 2021 Zoomcar. All rights reserved.
 */
class ZTransactionHeaderItem : ConstraintLayout {
    private var binding: LayoutZTransactionHeaderItemBinding

    constructor(context: Context) : super(context)

    constructor(context: Context,
                attrs: AttributeSet?
    ) : super(context, attrs)

    constructor(context: Context,
                attrs: AttributeSet? = null,
                @AttrRes defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr)

    init {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        binding = LayoutZTransactionHeaderItemBinding.inflate(inflater, this, true)
    }

    fun setData(model: ZtransactionHeaderItemUIModel) {
        binding.textLabel.apply {
            isVisible = model.title.getNullCheck()
            text = model.title
        }
        binding.textValue.apply {
            isVisible = model.value.getNullCheck()
            text = model.value
        }
    }

    @Parcelize
    data class ZtransactionHeaderItemUIModel(
            var title: String? = "",
            var value: String? = ""
    ) : Parcelable
}