package com.tigerspike.emirates.search.oneway

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tigerspike.emirates.R
import com.tigerspike.emirates.search.newAirportPickerActivityIntent
import com.tigerspike.emirates.tools.extensions.toast
import kotlinx.android.synthetic.main.fragment_one_way.*

internal val createOneWayFragment: () -> Fragment = {
    OneWayFragment()
}

private const val code_from = 1
private const val code_to = 2

class OneWayFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(
                R.layout.fragment_one_way, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fromEditText.setOnClickListener {
            startActivityForResult(activity?.newAirportPickerActivityIntent(), code_from)
        }
        toEditText.setOnClickListener {
            startActivityForResult(activity?.newAirportPickerActivityIntent(), code_to)
        }
        forEditText.setOnClickListener {
            activity?.toast(getString(R.string.section_unavailable, "This"))
        }
    }


}
