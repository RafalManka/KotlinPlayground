package com.tigerspike.emirates.search

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tigerspike.emirates.R
import com.tigerspike.emirates.tools.extensions.hideKeyboard
import com.tigerspike.emirates.tools.extensions.inLayoutToolbar
import com.tigerspike.emirates.tools.view.OnPageChangeListenerImpl
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : AppCompatActivity() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(inLayoutToolbar())
        mSectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        container.adapter = mSectionsPagerAdapter
        container.setCurrentItem(1, false)
        container.addOnPageChangeListener(object : OnPageChangeListenerImpl() {
            override fun onPageSelected(position: Int) {
                container.hideKeyboard()
            }
        })
        tabs.setupWithViewPager(container)

    }

}




