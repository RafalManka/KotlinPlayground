package com.tigerspike.emirates.search

import android.content.Context
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tigerspike.emirates.R
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*


private const val ARG_SECTION_NUMBER = "section_number"

class SearchActivity : AppCompatActivity() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbar)
        mSectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        container.adapter = mSectionsPagerAdapter
        tabs.setupWithViewPager(container)
    }

}

private data class TabItem(@StringRes val title: Int, val creator: () -> Fragment)

private val createPlaceholderFragment: (id: Int) -> (() -> Fragment) = { id: Int ->
    val args = Bundle()
    args.putInt(ARG_SECTION_NUMBER, id)
    val creator: () -> Fragment = {
        val fragment = PlaceholderFragment()
        fragment.arguments = args
        fragment
    }
    creator
}


class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val tabs: Array<TabItem> = arrayOf(
            TabItem(R.string.tab_title_return, createPlaceholderFragment(1)),
            TabItem(R.string.tab_title_one_way, createPlaceholderFragment(2)),
            TabItem(R.string.tab_title_multicity, createPlaceholderFragment(3))
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

class PlaceholderFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_search, container, false)
        rootView.section_label.text = getString(R.string.section_format, arguments?.getInt(ARG_SECTION_NUMBER))
        return rootView
    }

}
