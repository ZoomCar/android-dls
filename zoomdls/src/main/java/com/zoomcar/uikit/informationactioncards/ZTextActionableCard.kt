package com.zoomcar.uikit.informationactioncards

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.AttrRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.zoomcar.util.isValid
import com.zoomcar.zoomdls.R
import com.zoomcar.zoomdls.databinding.LayoutTextActionableCardBinding
import kotlinx.parcelize.Parcelize

/**
 * @created 13/09/2020 - 4:46 PM
 * @project Zoomcar
 * @author Shishir
 * Copyright (c) 2020 Zoomcar. All rights reserved.
 */
class ZTextActionableCard : ConstraintLayout, View.OnClickListener {
    private val binding: LayoutTextActionableCardBinding
    private var listener: IActionableCardClick? = null

    fun setZTextActionableCardListener(listener: IActionableCardClick) {
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
        binding = LayoutTextActionableCardBinding.inflate(inflater, this, true)
        binding.textAction.setOnClickListener(this)
    }

    fun setData(data: TextActionableCardUIModel) {
        binding.textTitle.apply {
            text = data.header
            isVisible = data.header.isValid()
        }

        binding.textDesc.text = data.desc
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

    @Parcelize
    data class TextActionableCardUIModel(
            var header: String? = null,
            var desc: String? = null,
            var actionText: String? = null
    ) : Parcelable
}
