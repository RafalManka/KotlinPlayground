package com.tigerspike.emirates.search.selectairport

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.tigerspike.emirates.feature.airports.Airport

class AirportPickerAdapter(private val callback: (Airport) -> Unit) : RecyclerView.Adapter<AirportViewHolder>() {

    private var items: Array<Airport> = emptyArray()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var strong: String = ""

    fun refresh(items: Array<Airport>, strong: String) {
        this.items = items
        this.strong = strong
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirportViewHolder =
            AirportViewHolder(parent, callback)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: AirportViewHolder, position: Int) = holder.refresh(items[position], strong)

}