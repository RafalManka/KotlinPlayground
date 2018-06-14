package com.tigerspike.emirates.search.placeholder

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tigerspike.emirates.R
import kotlinx.android.synthetic.main.fragment_placeholder.view.*

internal const val ARG_SECTION_NUMBER = "section_number"

val createPlaceholderFragment: (featureName: String) -> (() -> Fragment) = { featureName: String ->
    val args = Bundle()
    args.putString(ARG_SECTION_NUMBER, featureName)
    val creator: () -> Fragment = {
        val fragment = PlaceholderFragment()
        fragment.arguments = args
        fragment
    }
    creator
}

class PlaceholderFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_placeholder, container, false)
        rootView.section_label.text = getString(R.string.section_unavailable, arguments?.getString(ARG_SECTION_NUMBER))
        return rootView
    }

}