package com.zoomcar.uikit.textview

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.AttrRes
import androidx.appcompat.widget.AppCompatTextView

class ZTextView : AppCompatTextView {
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