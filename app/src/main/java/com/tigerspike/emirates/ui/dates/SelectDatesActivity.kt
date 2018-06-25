package com.tigerspike.emirates.ui.dates

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.tigerspike.emirates.R
import com.tigerspike.emirates.tools.extensions.inLayoutToolbar
import kotlinx.android.synthetic.main.fragment_placeholder.*

fun Context.newSelectFlightActivity(): Intent {
    return Intent(this, SelectDatesActivity::class.java)
}

class SelectDatesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_dates)
        setSupportActionBar(inLayoutToolbar())
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        section_label.text = getString(R.string.section_unavailable, "This")
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

}
