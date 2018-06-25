package com.tigerspike.emirates.tools.extensions

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.annotation.IdRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import com.tigerspike.emirates.R


fun AppCompatActivity.inLayoutToolbar(): Toolbar = findViewById(R.id.toolbar)

fun <V : View> AppCompatActivity.bind(@IdRes id: Int): Lazy<V> = lazy { findViewById<V>(id) }

fun <VM : ViewModel> AppCompatActivity.provideViewModel(clazz: Class<VM>): Lazy<VM> = lazy {
    ViewModelProviders.of(this).get(clazz)
}

fun <VM : ViewModel> AppCompatActivity.provideViewModel(clazz: Class<VM>, factory: ViewModelProvider.NewInstanceFactory): Lazy<VM> = lazy {
    ViewModelProviders.of(this, factory).get(clazz)
}

fun AppCompatActivity.getParcelableArrayExtra(key: String) = lazy {
    intent.getParcelableArrayExtra(key)
}
