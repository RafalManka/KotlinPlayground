package com.tigerspike.emirates.tools.extensions

import android.content.Context
import android.support.annotation.IdRes
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.hideKeyboard() {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE)
    if (inputManager is InputMethodManager) {
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }
}
