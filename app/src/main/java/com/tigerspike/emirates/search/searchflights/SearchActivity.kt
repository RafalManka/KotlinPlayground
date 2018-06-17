package com.tigerspike.emirates.search.searchflights

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.tigerspike.emirates.R
import com.tigerspike.emirates.tools.extensions.hideKeyboard
import com.tigerspike.emirates.tools.extensions.inLayoutToolbar
import com.tigerspike.emirates.tools.extensions.toast
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.action_options -> {
            toast(getString(R.string.section_unavailable, getString(R.string.section_name_options)))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}




