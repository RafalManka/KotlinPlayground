package com.tigerspike.emirates.tools.extensions

import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.view.View

fun <V : View> RecyclerView.ViewHolder.bind(@IdRes id: Int): Lazy<V> = lazy { itemView.findViewById<V>(id) }
