package com.zoomcar.uikit.optionsItem

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.AttrRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.widget.TextViewCompat
import androidx.databinding.DataBindingUtil
import com.zoomcar.util.getNullCheck
import com.zoomcar.util.isValid
import com.zoomcar.util.loadImage
import com.zoomcar.zoomdls.R
import com.zoomcar.zoomdls.databinding.LayoutSelectionItemBinding
import kotlinx.android.parcel.Parcelize

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
        binding.root.tag = model.id
        binding.textEdit.apply {
            isVisible = model.isEditable
            setOnClickListener(this@ZSelectionItemCell)
        }

        when {
            model.imageRes.getNullCheck() -> {
                binding.imageType.setImageResource(model.imageRes!!)
            }
            model.imageUrl.isValid() -> {
                binding.imageType.loadImage(model.imageUrl)
            }
            else -> {
                binding.imageType.setImageDrawable(null)
            }
        }

        when (model.type) {
            SelectionItemType.PRIMARY -> {
                binding.textTitle.apply {
                    TextViewCompat.setTextAppearance(this, R.style.OverlineSecondary)
                }
                binding.textDesc.apply {
                    TextViewCompat.setTextAppearance(this, R.style.CaptionPrimary)
                }
            }
            SelectionItemType.HIGHLIGHTED -> {
                binding.textTitle.apply {
                    TextViewCompat.setTextAppearance(this, R.style.Button2Primary)
                }
                binding.textDesc.apply {
                    TextViewCompat.setTextAppearance(this, R.style.CaptionInactive)
                }
            }
            SelectionItemType.DEFAULT -> {
                binding.textTitle.apply {
                    TextViewCompat.setTextAppearance(this, R.style.Body2Primary)
                }
                binding.textDesc.apply {
                    TextViewCompat.setTextAppearance(this, R.style.CaptionInactive)
                }
            }
        }
        binding.textTitle.apply {
            text = model.title
            isVisible = model.title.isValid()
            isSingleLine = model.isHeaderSingleLine
        }
        binding.textDesc.apply {
            text = model.desc
            isVisible = model.desc.isValid()
            isSingleLine = model.isDescSingleLine
        }
    }

    fun setListener(listener: IZSelectionItemListener) {
        this.listener = listener
    }

    enum class SelectionItemType {
        DEFAULT,
        PRIMARY,
        HIGHLIGHTED
    }

    @Parcelize
    data class SelectionItemUIModel(
            var id: String,
            var imageRes: Int?,
            var imageUrl: String? = null,
            var title: String? = null,
            var desc: String? = null,
            var isEditable: Boolean = false,
            var type: SelectionItemType,
            var isHeaderSingleLine: Boolean = false,
            var isDescSingleLine: Boolean = false
    ): Parcelable

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.text_edit -> {
                val id = binding.root.tag as String
                listener?.onEditClick(id)
            }
            R.id.layout_selection_item -> {
                val id = binding.root.tag as String
                listener?.onItemClick(id)
            }
        }
    }

    interface IZSelectionItemListener {
        fun onItemClick(id: String)
        fun onEditClick(id: String)
    }
}