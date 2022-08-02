package com.zoomcar.util

import com.squareup.picasso.Picasso
import com.zoomcar.uikit.imageview.ZImageView

/**
 * @created 13/09/2020 - 3:38 PM
 * @project Zoomcar
 * @author Shishir
 * Copyright (c) 2020 Zoomcar. All rights reserved.
 */

fun ZImageView.loadImage(imageUrl: String?) {
    if (imageUrl.isValid()) {
        Picasso.get()
                .load(imageUrl)
                .into(this)
    }
}