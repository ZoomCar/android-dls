package com.zoomcar.uikit.optionsItem

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.AttrRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.widget.TextViewCompat
import androidx.databinding.DataBindingUtil
import com.zoomcar.util.isValid
import com.zoomcar.zoomdls.R
import com.zoomcar.zoomdls.databinding.LayoutSelectionItemBinding

class ZSelectionItemCell : ConstraintLayout, View.OnClickListener {
    private val binding: LayoutSelectionItemBinding
    private var listener: IZSelectionItemListener? = null

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
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_selection_item, this, true)
        binding.layoutSelectionItem.setOnClickListener(this)
    }

    fun setData(model: SelectionItemUIModel) {
        binding.textEdit.apply {
            isVisible = model.isEditable
            setOnClickListener(this@ZSelectionItemCell)
        }
        when (model.type) {
            SelectionItemType.DEFAULT -> {
                binding.imageType.setImageResource(R.drawable.ic_circle_grey)
                binding.textTitle.apply {
                    TextViewCompat.setTextAppearance(this, R.style.OverlineSecondary);
                }
                binding.textDesc.apply {
                    TextViewCompat.setTextAppearance(this, R.style.CaptionPrimary);
                }
            }
            SelectionItemType.HIGHLIGHTED -> {
                binding.imageType.setImageResource(R.drawable.ic_circle_green)
                binding.textTitle.apply {
                    TextViewCompat.setTextAppearance(this, R.style.Button2Primary);
                }
                binding.textDesc.apply {
                    TextViewCompat.setTextAppearance(this, R.style.CaptionInactive);
                }
            }
            SelectionItemType.DROP_OFF_LOCATION -> {
                binding.imageType.setImageResource(R.drawable.ic_circle_grey)
                binding.textTitle.apply {
                    TextViewCompat.setTextAppearance(this, R.style.Body2Primary);
                }
                binding.textDesc.apply {
                    TextViewCompat.setTextAppearance(this, R.style.CaptionInactive);
                }
            }
        }
        binding.textTitle.apply {
            text = model.title
            isVisible = model.title.isValid()
        }
        binding.textDesc.apply {
            text = model.desc
            isVisible = model.desc.isValid()
        }
    }

    fun setListener(listener: IZSelectionItemListener) {
        this.listener = listener
    }

    enum class SelectionItemType {
        DEFAULT,
        HIGHLIGHTED,
        DROP_OFF_LOCATION
    }

    data class SelectionItemUIModel(
            var title: String? = null,
            var desc: String? = null,
            var isEditable: Boolean = false,
            var type: SelectionItemType
    )

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.text_edit -> {
                listener?.onEditClick()
            }
            R.id.layout_selection_item -> {
                listener?.onItemClick()
            }
        }
    }

    interface IZSelectionItemListener {
        fun onItemClick()
        fun onEditClick()
    }
}