package com.tigerspike.emirates.tools.extensions

import android.support.annotation.IdRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import com.tigerspike.emirates.R


fun AppCompatActivity.inLayoutToolbar(): Toolbar = findViewById(R.id.toolbar)

fun <V : View> AppCompatActivity.bind(@IdRes id: Int): Lazy<V> = lazy { findViewById<V>(id) }
