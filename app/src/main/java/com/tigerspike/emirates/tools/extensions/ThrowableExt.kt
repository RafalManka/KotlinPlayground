package com.tigerspike.emirates.tools.extensions

import timber.log.Timber

fun Throwable.logWarning() {
    Timber.w(this)
}