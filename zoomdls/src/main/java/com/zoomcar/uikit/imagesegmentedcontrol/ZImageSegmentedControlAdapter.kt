package com.zoomcar.uikit.imagesegmentedcontrol

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
import com.zoomcar.util.loadImage
import com.zoomcar.zoomdls.R
import com.zoomcar.zoomdls.databinding.LayoutImageSegmentedButtonBinding
import com.zoomcar.zoomdls.databinding.LayoutSegmentedButtonBinding
import kotlinx.android.parcel.Parcelize

class ZImageSegmentedControlAdapter(
        private val imageSegmentedClickListener: IZImageSegmentedButtonClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>()
        , IRadioSelectionBehaviour {
    private var data: List<ZImageSegmentedControlButtonModel>? = null
    private var selectedPosition = -1
    private var cellSize = 0

    private val adapterlistener: IImageSegmentAdapterListener = object :
        IImageSegmentAdapterListener {
        override fun getCellSize(): Int {
            return cellSize
        }
    }

    fun setData(data: List<ZImageSegmentedControlButtonModel>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun setCellSize(cellSize: Int) {
        this.cellSize = cellSize
        notifyDataSetChanged()
    }

    fun getSelectedData(): ZImageSegmentedControlButtonModel? {
        if (data.getNullCheck() && data!!.size > selectedPosition)
            return data!![selectedPosition]
        return null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: LayoutImageSegmentedButtonBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_image_segmented_button,
                parent,
                false)
        return ImageSegmentedButtonViewHolder(binding, adapterlistener, this)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ImageSegmentedButtonViewHolder).setData(data!![position])
    }

    class ImageSegmentedButtonViewHolder(val binding: LayoutImageSegmentedButtonBinding,
                                         private val adapterListenerImage: IImageSegmentAdapterListener,
                                         private val radioListener: IRadioSelectionBehaviour
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, IRadioButton {
        init {
            binding.cardBg.setOnClickListener(this)
        }

        fun setData(modelImage: ZImageSegmentedControlButtonModel) {
            val context = binding.root.context
            binding.root.layoutParams.width = adapterListenerImage.getCellSize()
            binding.root.tag = modelImage
            binding.iconView.apply {
                isVisible = modelImage.icon.getNullCheck()
                setImageDrawable(modelImage.icon?.let {
                    ContextCompat.getDrawable(context,
                        it
                    )
                })
            }
            binding.textName.text = modelImage.name
            if (modelImage.isSelected) {
                radioListener.onSelectPosition(bindingAdapterPosition)
                binding.cardBg.apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        elevation = UiUtil.dpToPixels(
                                context.resources.getDimensionPixelSize(R.dimen.segmented_control_selected_button_elevation),
                                context
                        ).toFloat()
                        binding.iconView.drawable.setTint(ContextCompat.getColor(context, R.color.ever_green_06))
                    }
                    setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))
                    strokeWidth = UiUtil.dpToPixels(
                        1,
                        context
                    )
                    strokeColor = ContextCompat.getColor(context, R.color.ever_green_06)
                }
                binding.textName.setTextColor(ContextCompat.getColor(context, R.color.ever_green_06))
            } else {
                binding.cardBg.apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        elevation = UiUtil.dpToPixels(
                                context.resources.getDimensionPixelSize(R.dimen.segmented_control_unselected_button_elevation),
                                context
                        ).toFloat()
                        binding.iconView.drawable.setTint(ContextCompat.getColor(context, R.color.phantom_grey_08))
                    }
                    setCardBackgroundColor(ContextCompat.getColor(context, R.color.transparent))
                    strokeWidth = UiUtil.dpToPixels(
                        0,
                        context
                    )
                    strokeColor = ContextCompat.getColor(context, R.color.transparent)
                }
                binding.textName.setTextColor(ContextCompat.getColor(context, R.color.phantom_grey_10))
            }
        }

        override fun isSelected(): Boolean {
            val data = binding.root.tag
            data?.let {
                return (it as ZImageSegmentedControlButtonModel).isSelected
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
    data class ZImageSegmentedControlButtonModel(
        val id: String,
        @DrawableRes val icon: Int? = null,
        val name: String,
        var isSelected: Boolean = false
    ) : Parcelable

    interface IImageSegmentAdapterListener {
        fun getCellSize(): Int
    }

    override fun onRadioButtonClicked(position: Int) {
        if (selectedPosition != position) {
            val prevPos = selectedPosition
            onSelectPosition(position)

            if(prevPos != -1) {
                val prevSelectedButton = data?.get(prevPos)
                prevSelectedButton?.let {
                    it.isSelected = false
                }
                notifyItemChanged(prevPos)

            }

            val currentSelectedButton = data?.get(selectedPosition)
            currentSelectedButton?.let {
                it.isSelected = true
                imageSegmentedClickListener.onSegmentButtonClick(selectedPosition)
            }
            notifyItemChanged(selectedPosition)
        }
    }

    override fun onSelectPosition(position: Int) {
        selectedPosition = position
    }

    interface IZImageSegmentedButtonClickListener {
        fun onSegmentButtonClick(position: Int)
    }
}