package com.tigerspike.emirates.search.searchflights.oneway

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tigerspike.emirates.R
import com.tigerspike.emirates.feature.airports.Airport
import com.tigerspike.emirates.search.selectairport.k_resultAirport
import com.tigerspike.emirates.search.selectairport.newAirportPickerActivityIntent
import com.tigerspike.emirates.tools.extensions.toast
import kotlinx.android.synthetic.main.fragment_one_way.*

internal val createOneWayFragment: () -> Fragment = {
    OneWayFragment()
}

private const val code_from = 1
private const val code_to = 2

class OneWayFragment : Fragment() {
    private var fromAirport: Airport? = null
    private var toAirport: Airport? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(
                R.layout.fragment_one_way, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fromInput.setOnClickListener {
            startActivityForResult(activity?.newAirportPickerActivityIntent(), code_from)
        }
        toInput.setOnClickListener {
            startActivityForResult(activity?.newAirportPickerActivityIntent(), code_to)
        }
        forInput.setOnClickListener {
            activity?.toast(getString(R.string.section_unavailable, "This"))
        }
        selectDates.setOnClickListener {
            activity?.toast(getString(R.string.section_unavailable, "This"))
        }
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
