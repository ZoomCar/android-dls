package com.zoomcar.uikit.segmentedcontrol

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.AttrRes
import androidx.annotation.DimenRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.zoomcar.util.isValid
import com.zoomcar.zoomdls.R
import com.zoomcar.zoomdls.databinding.LayoutSegmentedControlBinding

class ZSegmentedControl : ConstraintLayout, ZSegmentedControlAdapter.IZSegmentedButtonClickListener {

    constructor(context: Context) : super(context)

    constructor(context: Context,
                attrs: AttributeSet?
    ) : super(context, attrs)

    constructor(context: Context,
                attrs: AttributeSet? = null,
                @AttrRes defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr)

    var model: ZSegmentedControlUIModel? = null
    val binding: LayoutSegmentedControlBinding
    private val segmentAdapter: ZSegmentedControlAdapter

    var listener: IZSegmentedControlListener? = null
    private var parentWidth = 0

    init {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_segmented_control, this, true)
        segmentAdapter = ZSegmentedControlAdapter(this)
        initRecylerView()
    }

    private fun initRecylerView() {
        binding.segmentedControl.apply {
            layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = segmentAdapter
            addItemDecoration(SegmentedControlItemDecorator(context))
        }
    }

    fun setMargin(@DimenRes margin: Int) {
        val params = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        )
        val marginInPix = resources.getDimensionPixelSize(margin)
        params.setMargins(marginInPix, marginInPix, marginInPix, marginInPix)
        this.layoutParams = params
    }

    fun seTopMargin(@DimenRes margin: Int) {
        val params = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        )
        val marginInPix = resources.getDimensionPixelSize(margin)
        params.setMargins(0, marginInPix, 0, 0)
        this.layoutParams = params
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

    fun setZSegmentedControlClickListener(listener: IZSegmentedControlListener) {
        this.listener = listener
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        if (parentWidth != width) {
            parentWidth = width
            calculateCellSize()
        }
    }

    private fun calculateCellSize() {
        val width = if (binding.textHeader.isVisible) binding.textHeader.measuredWidth else 0
        val recyclerSize = parentWidth - width
        val itemCellSize = (recyclerSize / (model?.list?.size ?: 1).toFloat()).toInt()
        segmentAdapter.setCellSize(itemCellSize)
    }

    data class ZSegmentedControlUIModel(
            val id: String,
            val header: String,
            val list: List<ZSegmentedControlAdapter.ZSegmentedControlButtonModel>
    )

    interface IZSegmentedControlListener {
        fun onSegmentSelected(headerId: String, filterId: String)
    }

    override fun onSegmentButtonClick(position: Int) {
        model?.let { data ->
            listener?.onSegmentSelected(data.id, data.list[position].id)
        }
    }
}