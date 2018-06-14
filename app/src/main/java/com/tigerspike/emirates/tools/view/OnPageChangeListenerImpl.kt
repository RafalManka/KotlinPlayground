package com.tigerspike.emirates.tools.view

import android.support.v4.view.ViewPager

abstract class OnPageChangeListenerImpl : ViewPager.OnPageChangeListener {

    override fun onPageSelected(position: Int) {

    }

    override fun onPageScrolled(position: Int, offset: Float, offsetPixels: Int) {}

    override fun onPageScrollStateChanged(state: Int) {}

}