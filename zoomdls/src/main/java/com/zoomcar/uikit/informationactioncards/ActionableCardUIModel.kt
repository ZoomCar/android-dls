package com.zoomcar.uikit.informationactioncards

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @created 13/09/2020 - 4:44 PM
 * @project Zoomcar
 * @author Shishir
 * Copyright (c) 2020 Zoomcar. All rights reserved.
 */

@Parcelize
data class ActionableCardUIModel(
        var header: String? = "",
        var desc: String? = "",
        var img: String? = "",
        var actionText: String? = ""
) : Parcelable