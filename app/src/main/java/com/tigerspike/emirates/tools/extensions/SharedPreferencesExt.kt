package com.tigerspike.emirates.tools.extensions

import android.content.SharedPreferences

fun SharedPreferences.save(calls: SharedPreferences.Editor.() -> SharedPreferences.Editor) {
    edit().calls().apply()
}