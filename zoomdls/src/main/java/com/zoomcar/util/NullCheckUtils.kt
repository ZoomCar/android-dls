package com.zoomcar.util

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract


fun String?.isValid(): Boolean {
    return !this.isNullOrEmpty() && !this.equals("null", ignoreCase = true)
}

fun Map<*, *>?.isNotNullOrEmpty(): Boolean {
    return !this.isNullOrEmpty()
}

fun Collection<*>?.getNullCheck(): Boolean {
    return !this.isNullOrEmpty()
}

fun Any?.getNullCheck(): Boolean {
    return this != null
}
