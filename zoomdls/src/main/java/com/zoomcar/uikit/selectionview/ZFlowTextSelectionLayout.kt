package com.zoomcar.uikit.selectionview

import android.content.Context
import android.content.res.TypedArray
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.AttrRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.zoomcar.uikit.textview.ZSelectionTextView
import com.zoomcar.util.getNullCheck
import com.zoomcar.util.isValid
import com.zoomcar.zoomdls.R
import com.zoomcar.zoomdls.databinding.LayoutZFlowTextSelectionBinding
import kotlinx.android.parcel.Parcelize

class ZFlowTextSelectionLayout : ConstraintLayout, ZSelectionTextView.IZSelectionTextView {
    private var binding: LayoutZFlowTextSelectionBinding
    private val selectedKeys: MutableList<String> = mutableListOf()
    private var data: ZFlowTextSelectionLayoutUIModel? = null
    private var listener: IZFlowClickListener? = null
    private var type: ZFlowSelectionType = ZFlowSelectionType.SINGLE

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_z_flow_text_selection, this, true)
    }
    constructor(context: Context) : super(context)

    constructor(context: Context,
                attrs: AttributeSet?
    ) : super(context, attrs) {
        setZAttributes(attrs)

    }

    constructor(
            context: Context,
            attrs: AttributeSet? = null,
            @AttrRes defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr) {
        setZAttributes(attrs)
    }

    private fun setZAttributes(attrs: AttributeSet?) {
        attrs?.let {
            val a: TypedArray = context.obtainStyledAttributes(attrs,
                    R.styleable.ZFlowTextSelectionLayout, 0, 0)

            type = ZFlowSelectionType.fromValue(a.getInt(R.styleable.ZFlowTextSelectionLayout_selection_type, 0))
        }
    }

    fun setType(type: ZFlowSelectionType) {
        this.type = type
    }

    fun setData(model: ZFlowTextSelectionLayoutUIModel) {
        data = model
        if (data?.list.getNullCheck()) {
            for ((position, item) in data?.list!!.withIndex()) {
                val cell = ZSelectionTextView(context).apply {
                    setData(item)
                    setListener(this@ZFlowTextSelectionLayout)
                }
                if (item.isSelected)
                    selectedKeys.add(item.id)
                cell.apply {
                    id = View.generateViewId()
                }
                binding.layoutContainer.addView(cell, position)
                binding.flowSelectionTextView.addView(cell)
            }
        }
    }

    fun setListener(listener: IZFlowClickListener) {
        this.listener = listener
    }

    @Parcelize
    data class ZFlowTextSelectionLayoutUIModel(
            var list: List<ZSelectionTextView.ZSelectionTVUIModel>? = null
    ) : Parcelable

    override fun onItemSelected(key: String) {
        listener?.onItemSelected(key)
        if (type == ZFlowSelectionType.SINGLE) {
            handleSingleSelection(key)
        } else if (type == ZFlowSelectionType.MULTI) {
            handleMultiSelection(key)
        }
    }

    private fun handleMultiSelection(selectedKey: String) {
        val isAlreadySelected = selectedKeys.contains(selectedKey)
        if (isAlreadySelected) {
            val item = binding.root.findViewWithTag<ZSelectionTextView?>(selectedKey)
            selectedKeys.remove(selectedKey)
            item?.setSelection(false)
            return
        } else {
            val item = binding.root.findViewWithTag<ZSelectionTextView?>(selectedKey)
            selectedKeys.add(selectedKey)
            item?.setSelection(true)
        }
    }

    private fun handleSingleSelection(selectedKey: String) {
        var prevCell: ZSelectionTextView? = null
        val prevSelectedKey = if (selectedKeys.size > 0) selectedKeys[0] else ""
        if (prevSelectedKey == selectedKey)
            return
        if (prevSelectedKey.isValid()) {
            prevCell = binding.root.findViewWithTag<ZSelectionTextView?>(prevSelectedKey)
            selectedKeys.remove(prevSelectedKey)
            prevCell?.setSelection(false)
        }
        val item = binding.root.findViewWithTag<ZSelectionTextView?>(selectedKey)
        selectedKeys.add(selectedKey)
        item?.setSelection(true)

    }

    enum class ZFlowSelectionType {
        SINGLE,
        MULTI;

        companion object {
            fun fromValue(type: Int) = values().first { it.ordinal == type }
        }
    }

    interface IZFlowClickListener {
        fun onItemSelected(id: String)
    }
}