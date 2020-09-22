package com.zoomcar.uikit.informationactioncards

import android.content.Context
import android.graphics.Paint
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.AttrRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.zoomcar.util.isValid
import com.zoomcar.util.loadImage
import com.zoomcar.zoomdls.R
import com.zoomcar.zoomdls.databinding.LayoutTextIconActionableCardBinding
import kotlinx.android.parcel.Parcelize

/**
 * @created 13/09/2020 - 2:40 PM
 * @project Zoomcardls
 * @author Shishir
 * Copyright (c) 2020 Zoomcar. All rights reserved.
 */
class ZTextIconActionableCard : ConstraintLayout, View.OnClickListener {
    private val binding: LayoutTextIconActionableCardBinding
    private var listener: IActionableCardClick? = null

    fun setZTextIconActionableCardListener(listener: IActionableCardClick) {
        this.listener = listener
    }

    constructor(context: Context) : super(context)

    constructor(context: Context,
                attrs: AttributeSet?
    ) : super(context, attrs)

    constructor(
            context: Context,
            attrs: AttributeSet? = null,
            @AttrRes defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr)

    init {
        val inflater = LayoutInflater.from(context)
        binding = LayoutTextIconActionableCardBinding.inflate(inflater, this, true)
        binding.textAction.setOnClickListener(this)
    }

    fun setData(data: ActionableCardUIModel) {
        binding.textInfoTitle.text = data.header
        binding.textInfoDesc.text = data.desc
        binding.imageInfo.loadImage(data.img)
        binding.textAction.apply {
            text = data.actionText
            isVisible = data.actionText.isValid()
            paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG

        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.text_action -> {
                listener?.onActionClicked()
            }
        }
    }
}