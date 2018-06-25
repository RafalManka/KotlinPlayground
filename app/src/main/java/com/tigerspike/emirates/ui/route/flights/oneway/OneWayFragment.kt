package com.tigerspike.emirates.ui.route.flights.oneway

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tigerspike.emirates.R
import com.tigerspike.emirates.feature.airports.Airport
import com.tigerspike.emirates.feature.booking.BookingViewModel
import com.tigerspike.emirates.tools.extensions.byRemovingNulls
import com.tigerspike.emirates.tools.extensions.provideViewModel
import com.tigerspike.emirates.tools.extensions.toast
import com.tigerspike.emirates.tools.ifNotNull
import com.tigerspike.emirates.ui.route.airports.k_resultAirport
import com.tigerspike.emirates.ui.route.airports.newAirportPickerActivityIntent
import com.tigerspike.emirates.ui.dates.newSelectFlightActivity
import kotlinx.android.synthetic.main.fragment_one_way.*

internal val createOneWayFragment: () -> Fragment = {
    OneWayFragment()
}

private const val code_from = 1
private const val code_to = 2

private const val k_fromAirport = "fromAirport"
private const val k_toAirport = "toAirport"

class OneWayFragment : Fragment() {
    private var fromAirport: Airport? = null
    private var toAirport: Airport? = null

    private val bookingViewmodel: BookingViewModel by provideViewModel(BookingViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fromAirport = savedInstanceState?.getParcelable(k_fromAirport)
        toAirport = savedInstanceState?.getParcelable(k_toAirport)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(k_fromAirport, fromAirport)
        outState.putParcelable(k_toAirport, toAirport)
        super.onSaveInstanceState(outState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_one_way, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fromInput.setOnClickListener {
            val excludes = arrayOf(toAirport).byRemovingNulls()
            startActivityForResult(activity?.newAirportPickerActivityIntent(excludes), code_from)
        }
        toInput.setOnClickListener {
            val excludes = arrayOf(fromAirport).byRemovingNulls()
            startActivityForResult(activity?.newAirportPickerActivityIntent(excludes), code_to)
        }
        forInput.setOnClickListener {
            activity?.toast(getString(R.string.section_unavailable, "This"))
        }
        selectDates.setOnClickListener {
            ifNotNull(fromAirport, toAirport) { from, to ->
                bookingViewmodel.search(from, to)
            }
        }
        bookingViewmodel.booking.observe(this, Observer {
            startActivity(activity?.newSelectFlightActivity())
        })
        refreshButton()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) = when {
        resultCode == Activity.RESULT_OK && requestCode == code_from -> {
            fromAirport = data?.getParcelableExtra(k_resultAirport)
            fromInput.setText(fromAirport?.name ?: "")
            refreshButton()
        }
        resultCode == Activity.RESULT_OK && requestCode == code_to -> {
            toAirport = data?.getParcelableExtra(k_resultAirport)
            toInput.setText(toAirport?.name ?: "")
            refreshButton()
        }
        else -> super.onActivityResult(requestCode, resultCode, data)
    }

    private fun refreshButton() {
        selectDates.visibility = if (fromAirport != null && toAirport != null) View.VISIBLE else View.GONE
    }

}
