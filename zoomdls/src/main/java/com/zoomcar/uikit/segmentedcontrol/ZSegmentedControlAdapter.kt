package com.zoomcar.uikit.segmentedcontrol

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.zoomcar.uikit.interfaces.IRadioButton
import com.zoomcar.zoomdls.R
import com.zoomcar.zoomdls.databinding.LayoutSegmentedButtonBinding
import com.zoomcar.uikit.interfaces.IRadioSelectionBehaviour
import com.zoomcar.util.UiUtil
import com.zoomcar.util.getNullCheck

class ZSegmentedControlAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()
        , IRadioSelectionBehaviour {
    private var data: List<ZSegmentedControlButtonModel>? = null
    private var selectedPosition = 0
    private var cellSize = 0
    private val adapterlistener: ISegmentAdapterListener = object : ISegmentAdapterListener {
        override fun getCellSize(): Int {
            return cellSize
        }
    }

    fun setData(data: List<ZSegmentedControlButtonModel>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun setCellSize(cellSize: Int) {
        this.cellSize = cellSize
        notifyDataSetChanged()
    }

    fun getSelectedData(): ZSegmentedControlButtonModel? {
        if (data.getNullCheck() && data!!.size > selectedPosition)
            return data!![selectedPosition]
        return null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: LayoutSegmentedButtonBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_segmented_button,
                parent,
                false)
        return SegmentedButtonViewHolder(binding, adapterlistener, this)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SegmentedButtonViewHolder).setData(data!![position])
    }

    class SegmentedButtonViewHolder(val binding: LayoutSegmentedButtonBinding,
                                    private val adapterListener: ISegmentAdapterListener,
                                    private val radioListener: IRadioSelectionBehaviour
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, IRadioButton {
        init {
            binding.cardBg.setOnClickListener(this)
        }

        fun setData(model: ZSegmentedControlButtonModel) {
            val context = binding.root.context
            binding.root.layoutParams.width = adapterListener.getCellSize()
            binding.root.tag = model
            binding.textName.text = model.name
            if (model.isSelected) {
                radioListener.onSelectPosition(bindingAdapterPosition)
                binding.cardBg.apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        elevation = UiUtil.dpToPixels(
                                context.resources.getDimensionPixelSize(R.dimen.segmented_control_selected_button_elevation),
                                context
                        ).toFloat()
                    }
                    setCardBackgroundColor(ContextCompat.getColor(context, R.color.ever_green_06))
                }
                binding.textName.setTextColor(ContextCompat.getColor(context, R.color.white))
            } else {
                binding.cardBg.apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        elevation = UiUtil.dpToPixels(
                                context.resources.getDimensionPixelSize(R.dimen.segmented_control_unselected_button_elevation),
                                context
                        ).toFloat()
                    }
                    setCardBackgroundColor(ContextCompat.getColor(context, R.color.transparent))
                }
                binding.textName.setTextColor(ContextCompat.getColor(context, R.color.phantom_grey_10))
            }
        }

        override fun isSelected(): Boolean {
            val data = binding.root.tag
            data?.let {
                return (it as ZSegmentedControlButtonModel).isSelected
            }
            return false
        }

        override fun onClick(v: View?) {
            when (v?.id) {
                R.id.card_bg -> {
                    radioListener.onRadioButtonClicked(bindingAdapterPosition)
                }
            }
        }
    }

    data class ZSegmentedControlButtonModel(
            val id: String,
            val name: String,
            var isSelected: Boolean = false
    )

    interface ISegmentAdapterListener {
        fun getCellSize(): Int
    }

    override fun onRadioButtonClicked(position: Int) {
        if (selectedPosition != position) {
            val prevPos = selectedPosition
            onSelectPosition(position)

            val prevSelectedButton = data?.get(prevPos)
            prevSelectedButton?.let {
                it.isSelected = false
            }
            notifyItemChanged(prevPos)

            val currentSelectedButton = data?.get(selectedPosition)
            currentSelectedButton?.let {
                it.isSelected = true
            }
            notifyItemChanged(selectedPosition)
        }
    }

    override fun onSelectPosition(position: Int) {
        selectedPosition = position
    }
}