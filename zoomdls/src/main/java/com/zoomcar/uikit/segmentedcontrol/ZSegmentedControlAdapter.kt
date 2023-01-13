package com.zoomcar.uikit.segmentedcontrol

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.zoomcar.uikit.interfaces.IRadioButton
import com.zoomcar.uikit.interfaces.IRadioSelectionBehaviour
import com.zoomcar.util.UiUtil
import com.zoomcar.util.getNullCheck
import com.zoomcar.zoomdls.R
import com.zoomcar.zoomdls.databinding.LayoutSegmentedButtonBinding
import kotlinx.parcelize.Parcelize

class ZSegmentedControlAdapter(
    private val segmentedClickListener: IZSegmentedButtonClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), IRadioSelectionBehaviour {
    private var data: List<ZSegmentedControlButtonModel>? = null
    private var selectedPosition = -1
    private var cellSize = 0
    private var segmentControlType = ZSegmentedControl.SegmentControlType.LABEL

    private val adapterlistener: ISegmentAdapterListener = object : ISegmentAdapterListener {
        override fun getCellSize(): Int {
            return cellSize
        }
    }

    fun setData(
        data: List<ZSegmentedControlButtonModel>,
        type: ZSegmentedControl.SegmentControlType
    ) {
        this.data = data
        this.segmentControlType = type
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
            false
        )
        return SegmentedButtonViewHolder(binding, adapterlistener, this, segmentControlType)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SegmentedButtonViewHolder).setData(data!![position])
    }

    class SegmentedButtonViewHolder(
        val binding: LayoutSegmentedButtonBinding,
        private val adapterListener: ISegmentAdapterListener,
        private val radioListener: IRadioSelectionBehaviour,
        private val segmentControlType: ZSegmentedControl.SegmentControlType
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, IRadioButton {
        init {
            binding.cardBg.setOnClickListener(this)
        }

        fun setData(model: ZSegmentedControlButtonModel) {
            val context = binding.root.context
            binding.root.layoutParams.width = adapterListener.getCellSize()
            binding.root.tag = model
            binding.textName.text = model.name

            when (segmentControlType) {
                ZSegmentedControl.SegmentControlType.LABEL -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        binding.textName.setTextAppearance(R.style.Button2Primary)
                    }
                    val param = binding.cardBg.layoutParams as ViewGroup.MarginLayoutParams
                    param.setMargins(4, 4, 4, 4)
                    binding.cardBg.apply {
                        layoutParams = param
                    }
                    binding.iconView.isVisible = false
                    if (model.isSelected) {
                        radioListener.onSelectPosition(bindingAdapterPosition)
                        binding.cardBg.apply {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                elevation = UiUtil.dpToPixels(
                                    context.resources.getDimensionPixelSize(R.dimen.segmented_control_selected_button_elevation),
                                    context
                                ).toFloat()
                            }
                            setCardBackgroundColor(
                                ContextCompat.getColor(
                                    context,
                                    R.color.ever_green_06
                                )
                            )
                        }
                        binding.textName.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.white
                            )
                        )
                    } else {
                        binding.cardBg.apply {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                elevation = UiUtil.dpToPixels(
                                    context.resources.getDimensionPixelSize(R.dimen.segmented_control_unselected_button_elevation),
                                    context
                                ).toFloat()
                            }
                            setCardBackgroundColor(
                                ContextCompat.getColor(
                                    context,
                                    R.color.transparent
                                )
                            )
                        }
                        binding.textName.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.phantom_grey_10
                            )
                        )
                    }
                }

                ZSegmentedControl.SegmentControlType.ICON_LABEL -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        binding.textName.setTextAppearance(R.style.CaptionPrimary)
                    }
                    //Remove layout margin
                    val param = binding.cardBg.layoutParams as ViewGroup.MarginLayoutParams
                    param.setMargins(0, 0, 0, 0)
                    binding.cardBg.apply {
                        layoutParams = param
                    }

                    binding.iconView.apply {
                        isVisible = model.icon.getNullCheck()
                        setImageDrawable(model.icon?.let {
                            ContextCompat.getDrawable(
                                context,
                                it
                            )
                        })
                    }
                    if (model.isSelected) {
                        radioListener.onSelectPosition(bindingAdapterPosition)
                        binding.cardBg.apply {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                elevation = UiUtil.dpToPixels(
                                    context.resources.getDimensionPixelSize(R.dimen.segmented_control_selected_button_elevation),
                                    context
                                ).toFloat()
                                binding.iconView.drawable.setTint(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.ever_green_06
                                    )
                                )
                            }
                            background = bindingAdapter?.itemCount?.let { count ->
                                getBackgroundStyleBasedOnPosition(
                                    context,
                                    bindingAdapterPosition,
                                    count
                                )
                            }
                            setBackgroundColor(
                                ContextCompat.getColor(
                                    context,
                                    R.color.white
                                )
                            )
                            strokeWidth = UiUtil.dpToPixels(
                                1,
                                context
                            )
                            strokeColor = ContextCompat.getColor(context, R.color.ever_green_06)
                        }
                        binding.textName.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.ever_green_06
                            )
                        )
                    } else {
                        binding.cardBg.apply {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                elevation = UiUtil.dpToPixels(
                                    context.resources.getDimensionPixelSize(R.dimen.segmented_control_unselected_button_elevation),
                                    context
                                ).toFloat()
                                binding.iconView.drawable.setTint(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.phantom_grey_08
                                    )
                                )
                            }
                            background = bindingAdapter?.itemCount?.let { count ->
                                getBackgroundStyleBasedOnPosition(
                                    context,
                                    bindingAdapterPosition,
                                    count
                                )
                            }
                            setBackgroundColor(
                                ContextCompat.getColor(
                                    context,
                                    R.color.transparent
                                )
                            )
                            strokeWidth = UiUtil.dpToPixels(
                                0,
                                context
                            )
                            strokeColor = ContextCompat.getColor(context, R.color.transparent)
                        }
                        binding.textName.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.phantom_grey_10
                            )
                        )
                    }
                }
            }
        }

        private fun getBackgroundStyleBasedOnPosition(
            context: Context,
            position: Int,
            size: Int
        ): Drawable? {
            return when (position) {
                0 -> ContextCompat.getDrawable(
                    context,
                    R.drawable.background_segmented_control_card_left_radius_2_7
                )
                size - 1 -> ContextCompat.getDrawable(
                    context,
                    R.drawable.background_segmented_control_card_right_radius_2_7
                )
                else -> null
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

    @Parcelize
    data class ZSegmentedControlButtonModel(
        val id: String,
        val name: String,
        var isSelected: Boolean = false,
        @DrawableRes val icon: Int? = null
    ) : Parcelable

    interface ISegmentAdapterListener {
        fun getCellSize(): Int
    }

    override fun onRadioButtonClicked(position: Int) {
        if (position != -1) {
            if (selectedPosition != position) {
                val prevPos = selectedPosition
                onSelectPosition(position)

                if (prevPos != -1) {
                    val prevSelectedButton = data?.get(prevPos)
                    prevSelectedButton?.let {
                        it.isSelected = false
                    }
                    notifyItemChanged(prevPos)

                }

                val currentSelectedButton = data?.get(selectedPosition)
                currentSelectedButton?.let {
                    it.isSelected = true
                    segmentedClickListener.onSegmentButtonClick(selectedPosition)
                }
                notifyItemChanged(selectedPosition)
            }
        }
    }

    override fun onSelectPosition(position: Int) {
        selectedPosition = position
    }

    interface IZSegmentedButtonClickListener {
        fun onSegmentButtonClick(position: Int)
    }
}