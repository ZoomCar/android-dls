package com.zoomcar.uikit.selectionview

import android.content.Context
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.zoomcar.uikit.textview.ZSelectionTextView
import com.zoomcar.util.getNullCheck
import com.zoomcar.util.isValid
import com.zoomcar.zoomdls.R
import com.zoomcar.zoomdls.databinding.LayoutZFlowTextSelectionBinding
import kotlinx.android.parcel.Parcelize

class ZFlowTextSelectionLayout(context: Context,
                               val type: ZFlowSelectionType
) : ConstraintLayout(context), ZSelectionTextView.IZSelectionTextView {
    private var binding: LayoutZFlowTextSelectionBinding
    private val selectedKeys: MutableList<String> = mutableListOf()
    private var data: ZFlowTextSelectionLayoutUIModel? = null

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_z_flow_text_selection, this, true)
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

    @Parcelize
    data class ZFlowTextSelectionLayoutUIModel(
            var list: List<ZSelectionTextView.ZSelectionTVUIModel>? = null
    ) : Parcelable

    override fun onItemSelected(key: String) {
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
        MULTI
    }
}