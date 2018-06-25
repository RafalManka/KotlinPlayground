package com.tigerspike.emirates.ui.route.flights

import android.content.Context
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.tigerspike.emirates.R
import com.tigerspike.emirates.ui.route.flights.oneway.createOneWayFragment
import com.tigerspike.emirates.ui.route.flights.placeholder.createPlaceholderFragment

private data class TabItem(@StringRes val title: Int, val creator: () -> Fragment)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val tabs: Array<TabItem> = arrayOf(
            TabItem(R.string.tab_title_return, createPlaceholderFragment(context.getString(R.string.feature_name_return_flight))),
            TabItem(R.string.tab_title_one_way, createOneWayFragment),
            TabItem(R.string.tab_title_multi_city, createPlaceholderFragment(context.getString(R.string.tab_title_multi_city)))
    )

    override fun getPageTitle(position: Int): CharSequence? {
        return context.getString(tabs[position].title)
    }

    override fun getItem(position: Int): Fragment {
        return tabs[position].creator()
    }

    override fun getCount(): Int {
        return tabs.size
    }
}