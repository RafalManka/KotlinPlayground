package com.tigerspike.emirates.tools.extensions

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment


fun <VM : ViewModel> Fragment.provideViewModel(clazz: Class<VM>): Lazy<VM> = lazy {
    ViewModelProviders.of(this).get(clazz)
}