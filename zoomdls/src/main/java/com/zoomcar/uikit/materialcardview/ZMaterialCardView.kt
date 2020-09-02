package com.zoomcar.uikit.materialcardview

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.AttrRes
import com.google.android.material.card.MaterialCardView

class ZMaterialCardView : MaterialCardView {
    constructor(
            context: Context,
            attrs: AttributeSet?
    ) : super(context, attrs)

    constructor(
            context: Context,
            attrs: AttributeSet?,
            @AttrRes defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)
}
