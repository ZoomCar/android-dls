package com.zoomcar.uikit.button

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.AttrRes
import com.google.android.material.button.MaterialButton

class ZMaterialButton : MaterialButton {
    constructor(context: Context) : super(context)

    constructor(context: Context,
                attrs: AttributeSet?
    ) : super(context, attrs)

    constructor(
            context: Context,
            attrs: AttributeSet,
            @AttrRes defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr)
}