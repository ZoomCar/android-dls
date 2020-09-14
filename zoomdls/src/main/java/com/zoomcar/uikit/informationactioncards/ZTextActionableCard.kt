package com.zoomcar.uikit.informationactioncards

import android.content.Context
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.zoomcar.util.isValid
import com.zoomcar.zoomdls.R
import com.zoomcar.zoomdls.databinding.LayoutTextActionableCardBinding
import kotlinx.android.parcel.Parcelize

/**
 * @created 13/09/2020 - 4:46 PM
 * @project Zoomcar
 * @author Shishir
 * Copyright (c) 2020 Zoomcar. All rights reserved.
 */
class ZTextActionableCard(context: Context, val listener: IActionableCardClick) : ConstraintLayout(context), View.OnClickListener {
    private val binding: LayoutTextActionableCardBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = LayoutTextActionableCardBinding.inflate(inflater, this, true)
        binding.textAction.setOnClickListener(this)
    }

    fun setData(data: TextActionableCardUIModel) {
        binding.textTitle.text = data.header
        binding.textDesc.text = data.desc
        binding.textAction.apply {
            text = data.actionText
            isVisible = data.actionText.isValid()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.text_action -> {
                listener.onActionClicked()
            }
        }
    }

    @Parcelize
    data class TextActionableCardUIModel(
            var header: String? = "",
            var desc: String? = "",
            var actionText: String? = ""
    ) : Parcelable
}
