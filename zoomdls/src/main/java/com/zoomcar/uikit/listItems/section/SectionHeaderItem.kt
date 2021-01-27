package com.zoomcar.uikit.listItems.section

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.AttrRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.zoomcar.zoomdls.R
import com.zoomcar.zoomdls.databinding.LayoutZSectionHeaderItemBinding

/**
 * @created 20/01/2021 - 4:36 PM
 * @project Zoomcar
 * @author Shishir
 * Copyright (c) 2021 Zoomcar. All rights reserved.
 */
class SectionHeaderItem : ConstraintLayout {
    private var binding: LayoutZSectionHeaderItemBinding

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
        binding = LayoutZSectionHeaderItemBinding.inflate(inflater,this, true)
    }

    fun setData(label: String) {
        binding.textLabel.text = label
    }
}