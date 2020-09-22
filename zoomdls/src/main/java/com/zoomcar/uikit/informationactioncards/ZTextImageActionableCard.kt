package com.zoomcar.uikit.informationactioncards

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.zoomcar.util.isValid
import com.zoomcar.util.loadImage
import com.zoomcar.zoomdls.R
import com.zoomcar.zoomdls.databinding.LayoutTextImageActionableCardBinding

/**
 * @created 13/09/2020 - 4:26 PM
 * @project Zoomcar
 * @author Shishir
 * Copyright (c) 2020 Zoomcar. All rights reserved.
 */
class ZTextImageActionableCard : ConstraintLayout, View.OnClickListener {
    private val binding: LayoutTextImageActionableCardBinding
    private var listener: IActionableCardClick? = null

    fun setZTextImageActionableCardListener(listener: IActionableCardClick) {
        this.listener = listener
    }

    constructor(context: Context) : super(context)

    constructor(context: Context,
                attrs: AttributeSet?
    ) : super(context, attrs)


    init {
        val inflater = LayoutInflater.from(context)
        binding = LayoutTextImageActionableCardBinding.inflate(inflater, this, true)
        binding.textAction.setOnClickListener(this)
    }

    fun setData(data: ActionableCardUIModel) {
        binding.textTitle.text = data.header
        binding.textDesc.text = data.desc
        binding.imageInfo.loadImage(data.img)
        binding.textAction.apply {
            text = data.actionText
            isVisible = data.actionText.isValid()
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