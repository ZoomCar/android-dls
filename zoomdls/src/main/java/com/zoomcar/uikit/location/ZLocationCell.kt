package com.zoomcar.uikit.location

import android.content.Context
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View.OnFocusChangeListener
import androidx.annotation.AttrRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.zoomcar.zoomdls.databinding.LayoutZLocationBarBinding
import kotlinx.parcelize.Parcelize

/*
  * @created 22/03/2021 - 2:12 PM
  * @project Zoomcar
  * @author Shishir
  * Copyright (c) 2021 Zoomcar. All rights reserved.
*/
class ZLocationCell : ConstraintLayout {

    private lateinit var binding: LayoutZLocationBarBinding
    private var listener: IZLocationBarListener? = null

    private val locationChangeListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (binding.textPickupLocation.hasFocus()) {
                listener?.onLocationSearchTextChanged(s.toString(), LocationSearchFlowType.PICKUP)
            } else if (binding.textDropOffLocation.hasFocus()) {
                listener?.onLocationSearchTextChanged(s.toString(), LocationSearchFlowType.DROP_OFF)
            }
        }

    }

    private val locationFocusListener = OnFocusChangeListener { v, hasFocus ->
        val tag = v?.tag as LocationSearchFlowType
        if (hasFocus) {
            if (tag == LocationSearchFlowType.PICKUP) {
                binding.textPickupLocation.addTextChangedListener(locationChangeListener)
                binding.textPickupLocation.setText("")
            } else {
                binding.textDropOffLocation.addTextChangedListener(locationChangeListener)
                binding.textDropOffLocation.setText("")
            }

        } else {
            if (tag == LocationSearchFlowType.PICKUP) {
                binding.textPickupLocation.removeTextChangedListener(locationChangeListener)
            } else {
                binding.textDropOffLocation.removeTextChangedListener(locationChangeListener)
            }
        }
    }

    constructor(context: Context) : super(context)

    constructor(context: Context,
                attrs: AttributeSet?
    ) : super(context, attrs)

    constructor(context: Context,
                attrs: AttributeSet? = null,
                @AttrRes defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr)

    init {
        val inflater = LayoutInflater.from(context)
        binding = LayoutZLocationBarBinding.inflate(inflater, this, true)
    }

    private fun setTextWatchers(setListener: Boolean) {
        if (setListener) {
            binding.textPickupLocation.addTextChangedListener(locationChangeListener)
            binding.textDropOffLocation.addTextChangedListener(locationChangeListener)
        } else {
            binding.textPickupLocation.removeTextChangedListener(locationChangeListener)
            binding.textDropOffLocation.removeTextChangedListener(locationChangeListener)
        }
    }

    private fun setFocusChangeListener(setListener: Boolean) {
        binding.textPickupLocation.onFocusChangeListener = if (setListener) locationFocusListener else null
        binding.textDropOffLocation.onFocusChangeListener = if (setListener) locationFocusListener else null
    }

    fun setListener(listener: IZLocationBarListener?) {
        this.listener = listener
    }

    fun setData(model: ZLocationBarUIModel) {
        setFocusChangeListener(false)
        setTextWatchers(false)
        binding.groupOneWay.isVisible = model.type == LocationBarType.ONE_WAY
        binding.textPickupLocation.apply {
            tag = LocationSearchFlowType.PICKUP
            setText(model.pickupPlace)
            hint = model.pickupHint
        }
        binding.textDropOffLocation.apply {
            tag = LocationSearchFlowType.DROP_OFF
            setText(model.dropOffPlace)
            hint = model.dropOffHint
        }
        setFocusChangeListener(true)
    }

    fun selectLocationFlow(flow: LocationSearchFlowType) {
        if(flow == LocationSearchFlowType.PICKUP)
            binding.textPickupLocation.performClick()
        else
            binding.textDropOffLocation.performClick()

    }

    @Parcelize
    data class ZLocationBarUIModel(
            val pickupPlace: String? = null,
            val pickupHint: String? = null,
            val dropOffPlace: String? = null,
            val dropOffHint: String? = null,
            val type: LocationBarType = LocationBarType.NORMAL
    ) : Parcelable

    //To be used to determine if Round trip flow or one way
    enum class LocationBarType {
        NORMAL,
        ONE_WAY
    }

    //To be used to determine if PICKUP address search or DROP_OFF
    enum class LocationSearchFlowType {
        PICKUP,
        DROP_OFF;

        companion object {
            fun valueOf(type: String?) = values().firstOrNull { it.name == type }
        }
    }

    interface IZLocationBarListener {
        fun onPickupLocationClicked()
        fun onDropOffLocationClicked()
        fun onLocationSearchTextChanged(text: String, type: LocationSearchFlowType)
    }
}