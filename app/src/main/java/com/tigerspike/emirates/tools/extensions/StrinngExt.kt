package com.tigerspike.emirates.tools.extensions

fun String?.containsIgnoreCase(string: String): Boolean? =
        this?.toUpperCase()?.contains(string.toUpperCase())