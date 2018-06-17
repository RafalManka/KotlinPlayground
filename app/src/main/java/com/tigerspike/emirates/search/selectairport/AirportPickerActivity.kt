package com.tigerspike.emirates.search.selectairport

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.tigerspike.emirates.R
import com.tigerspike.emirates.feature.airports.AirportsViewModel
import com.tigerspike.emirates.tools.extensions.inLayoutToolbar
import com.tigerspike.emirates.tools.extensions.provideViewModel
import com.tigerspike.emirates.tools.extensions.secondsToMilliseconds
import com.tigerspike.emirates.tools.view.DebounceOnTextChangedListener
import com.tigerspike.emirates.tools.view.TextWatcherImpl
import kotlinx.android.synthetic.main.activity_airport_picker.*

const val k_resultAirport = "RESULT_AIRPORT"

fun Context.newAirportPickerActivityIntent(): Intent {
    return Intent(this, AirportPickerActivity::class.java)
}

class AirportPickerActivity : AppCompatActivity() {

    private val viewModel by provideViewModel(AirportsViewModel::class.java)
    private val adapter = AirportPickerAdapter {
        val data = Intent()
        data.putExtra(k_resultAirport, it)
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_airport_picker)
        setSupportActionBar(inLayoutToolbar())
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        clear.setOnClickListener {
            input.setText("")
        }
        input.addTextChangedListener(object : TextWatcherImpl() {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                clear.visibility = if (p0?.isEmpty() == true) View.GONE else View.VISIBLE
                hintLayout.visibility = if (p0?.isNotBlank() == true) View.GONE else View.VISIBLE
            }
        })
        input.addTextChangedListener(object : DebounceOnTextChangedListener(0.5f.secondsToMilliseconds()) {
            override fun doOnTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                viewModel.getAirports(s.toString())
            }
        })
        viewModel.airports.observe(this, Observer {
            adapter.refresh(it ?: emptyArray(), input.text.toString())
        })
        viewModel.getAirports()
    }

}


