package com.tigerspike.emirates.tools.extensions

import timber.log.Timber

fun String.logDebug() {
    Timber.d(this)
}