package com.tigerspike.emirates.tools.extensions

import android.content.Context
import android.support.annotation.StringRes
import android.widget.Toast

fun Context.toast(string: String) {
    Toast.makeText(this, string, Toast.LENGTH_LONG).show()
}

fun Context.toast(@StringRes int: Int) {
    Toast.makeText(this, int, Toast.LENGTH_LONG).show()
}