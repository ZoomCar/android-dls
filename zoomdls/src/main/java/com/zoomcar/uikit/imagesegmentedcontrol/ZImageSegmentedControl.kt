package com.zoomcar.uikit.imagesegmentedcontrol

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.AttrRes
import androidx.annotation.DimenRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.zoomcar.uikit.segmentedcontrol.SegmentedControlItemDecorator
import com.zoomcar.zoomdls.R
import com.zoomcar.zoomdls.databinding.LayoutImageSegmentedControlBinding
import kotlinx.android.parcel.Parcelize

class ZImageSegmentedControl : ConstraintLayout, ZImageSegmentedControlAdapter.IZImageSegmentedButtonClickListener {

    constructor(context: Context) : super(context)

    constructor(context: Context,
                attrs: AttributeSet?
    ) : super(context, attrs)

    constructor(context: Context,
                attrs: AttributeSet? = null,
                @AttrRes defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr)

    var modelImage: ZImageSegmentedControlUIModel? = null
    val binding: LayoutImageSegmentedControlBinding
    private val segmentAdapter: ZImageSegmentedControlAdapter

    var listener: IZImageSegmentedControlListener? = null
    private var parentWidth = 0

    init {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_image_segmented_control, this, true)
        segmentAdapter = ZImageSegmentedControlAdapter(this)
        initRecylerView()
    }

    private fun initRecylerView() {
        binding.segmentedControl.apply {
            layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = segmentAdapter
            addItemDecoration(ImageSegmentedControlItemDecorator(context))
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

    fun setData(item: ZImageSegmentedControlUIModel) {
        this.modelImage = item
        modelImage?.list?.let {
            segmentAdapter.setData(it)
        }
    }

    fun setZImageSegmentedControlClickListener(listener: IZImageSegmentedControlListener) {
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
        val width = 0
        val recyclerSize = parentWidth - width
        val itemCellSize = (recyclerSize / (modelImage?.list?.size ?: 1).toFloat()).toInt()
        segmentAdapter.setCellSize(itemCellSize)
    }

    @Parcelize
    data class ZImageSegmentedControlUIModel(
        val id: String,
        val list: List<ZImageSegmentedControlAdapter.ZImageSegmentedControlButtonModel>
    ): Parcelable

    interface IZImageSegmentedControlListener {
        fun onSegmentSelected(headerId: String, filterId: String)
    }

    override fun onSegmentButtonClick(position: Int) {
        modelImage?.let { data ->
            listener?.onSegmentSelected(data.id, data.list[position].id)
        }
    }
}