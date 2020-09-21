package com.zoomcar.uikit.textview

import android.content.Context
import android.content.res.TypedArray
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import androidx.annotation.AttrRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.zoomcar.zoomdls.R
import kotlinx.android.parcel.Parcelize

class ZSelectionTextView : AppCompatTextView, View.OnClickListener {
    private var isZsTvSelected = false
    private var listener: IZSelectionTextView? = null

    constructor(context: Context) : super(context)

    constructor(context: Context,
                attrs: AttributeSet?
    ) : super(context, attrs) {
        initializeProperties(attrs)
    }

    constructor(
            context: Context,
            attrs: AttributeSet? = null,
            @AttrRes defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr) {
        initializeProperties(attrs)
    }

    fun initializeProperties(attrs: AttributeSet?) {
        attrs?.let {
            val a: TypedArray = context.obtainStyledAttributes(attrs,
                    R.styleable.ZSelectionTextView, 0, 0)
            isZsTvSelected = a.getBoolean(R.styleable.ZSelectionTextView_zstv_selected, false)
        }
        refreshSelection()
    }

    init {
        setOnClickListener(this)
    }

    fun setSelection(isSelected: Boolean) {
        isZsTvSelected = isSelected
        refreshSelection()
    }

    private fun refreshSelection() {
        if (isZsTvSelected) {
            setTextColor(ContextCompat.getColor(context, R.color.ever_green_06))
            background = ContextCompat.getDrawable(context, R.drawable.z_selection_text_view_selected_underline)
        } else {
            setTextColor(ContextCompat.getColor(context, R.color.phantom_grey_06))
            background = ContextCompat.getDrawable(context, R.drawable.z_selection_text_view_unselected_underline)
        }
    }

    fun setData(model: ZSelectionTVUIModel) {
        tag = model.id
        text = model.text
        setSelection(model.isSelected)
    }

    @Parcelize
    data class ZSelectionTVUIModel(
            val id: String,
            val text: String,
            val isSelected: Boolean = false
    ) : Parcelable

    override fun onClick(v: View?) {
        listener?.onItemSelected(tag as String)
    }

    fun setListener(listener: IZSelectionTextView) {
        this.listener = listener
    }

    interface IZSelectionTextView {
        fun onItemSelected(key: String)
    }
}