package com.zoomcar.uikit.materialcardview

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.AttrRes
import com.google.android.material.card.MaterialCardView

class ZMaterialCardView : MaterialCardView {
    constructor(context: Context) : super(context)

    constructor(context: Context,
                attrs: AttributeSet?
    ) : super(context, attrs)

    constructor(
            context: Context,
            attrs: AttributeSet? = null,
            @AttrRes defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr)
}
