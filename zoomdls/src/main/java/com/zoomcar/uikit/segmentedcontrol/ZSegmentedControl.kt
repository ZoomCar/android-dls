package com.zoomcar.uikit.segmentedcontrol

import android.content.Context
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.zoomcar.zoomdls.R
import com.zoomcar.zoomdls.databinding.LayoutSegmentedControlBinding
import com.zoomcar.util.UiUtil
import com.zoomcar.util.isValid

class ZSegmentedControl(context: Context) : ConstraintLayout(context) {
    var model: ZSegmentedControlUIModel? = null
    val binding: LayoutSegmentedControlBinding
    val segmentAdapter: ZSegmentedControlAdapter
    private var parentWidth = 0

    init {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_segmented_control, this, true)
        segmentAdapter = ZSegmentedControlAdapter()
        initRecylerView()
    }

    private fun initRecylerView() {
        binding.segmentedControl.apply {
            layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = segmentAdapter
            addItemDecoration(SegmentedControlItemDecorator(context))
        }
    }

    fun setData(item: ZSegmentedControlUIModel) {
        this.model = item
        binding.textHeader.apply {
            text = model?.header
            isVisible = model?.header.isValid()
            measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
        }
        model?.list?.let {
            segmentAdapter.setData(it)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        if (parentWidth != width) {
            parentWidth = width
            calculateCellSize()
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private fun calculateCellSize() {
        val width = if (binding.textHeader.isVisible) binding.textHeader.measuredWidth else 0
        val recyclerSize = parentWidth - UiUtil.dpToPixels(32, binding.root.context) - width
        val itemCellSize = (recyclerSize / (model?.list?.size ?: 1).toFloat()).toInt()
        segmentAdapter.setCellSize(itemCellSize)
    }

    data class ZSegmentedControlUIModel(
            val header: String,
            val list: List<ZSegmentedControlAdapter.ZSegmentedControlButtonModel>
    )
}