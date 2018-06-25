package com.tigerspike.emirates.tools.extensions


inline fun <reified T : Any> Array<T?>.byRemovingNulls(): Array<T> =
        filterNotNull().toTypedArray()
