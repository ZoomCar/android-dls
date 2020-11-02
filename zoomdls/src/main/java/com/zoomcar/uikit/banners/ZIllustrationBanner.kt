package com.zoomcar.uikit.banners

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.AttrRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.zoomcar.util.getNullCheck
import com.zoomcar.util.isValid
import com.zoomcar.util.loadImage
import com.zoomcar.zoomdls.databinding.LayoutIllustrationBannerBinding
import kotlinx.android.parcel.Parcelize

/**
 * @created 21/10/2020 - 11:24 AM
 * @project Zoomcar
 * @author Shishir
 * Copyright (c) 2020 Zoomcar. All rights reserved.
 */
class ZIllustrationBanner : ConstraintLayout {
    private var binding: LayoutIllustrationBannerBinding

    constructor(context: Context) : super(context)

    constructor(context: Context,
                attrs: AttributeSet?
    ) : super(context, attrs)

    constructor(
            context: Context,
            attrs: AttributeSet,
            @AttrRes defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr)

    init {
        val inflater = LayoutInflater.from(context)
        binding = LayoutIllustrationBannerBinding.inflate(inflater, this, true)
    }

    fun setData(data: IllustrationBannerUIModel) {
        when {
            data.imageUrl.isValid() -> {
                binding.imageBanner.loadImage(data.imageUrl)
            }
            data.imageRes.getNullCheck() -> {
                binding.imageBanner.setImageResource(data.imageRes!!)
            }
            else -> {
                binding.imageBanner.setImageDrawable(null)
            }
        }
        binding.textTitle.apply {
            isVisible = data.title.isValid()
            text = data.title
        }

        binding.textDescription.apply {
            isVisible = data.description.isValid()
            text = data.description
        }

    }

    @Parcelize
    data class IllustrationBannerUIModel(
            val imageUrl: String? = null,
            val imageRes: Int? = null,
            val title: String? = null,
            val description: String? = null
    ) : Parcelable
}