package com.tigerspike.emirates.ui.route.airports

import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.tigerspike.emirates.R
import com.tigerspike.emirates.feature.airports.Airport
import com.tigerspike.emirates.tools.extensions.bind
import com.tigerspike.emirates.tools.view.BabushkaPieceBuilder
import com.tigerspike.emirates.tools.view.BabushkaText

class AirportViewHolder(parent: ViewGroup, private val callback: (Airport) -> Unit) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.row_airport_picker, parent, false)
) {

    val code: TextView by bind(R.id.code)
    val location: BabushkaText by bind(R.id.location)
    val name: BabushkaText by bind(R.id.name)

    fun refresh(airport: Airport, strong: String) {
        setupCodeText(airport)
        setupTitleText(airport, strong)
        setupSubtitleText(airport, strong)
        itemView.setOnClickListener {
            callback(airport)
        }
    }

    private fun setupCodeText(airport: Airport) {
        code.text = airport.code
    }

    private fun setupSubtitleText(airport: Airport, strong: String) {
        val airportName = airport.name

        val parts = splitToParts(airportName ?: "", strong)
        name.reset()
        name.addPiece(BabushkaPieceBuilder(parts.start).build())
        name.addPiece(BabushkaPieceBuilder(parts.middle)
                .backgroundColor(ContextCompat.getColor(location.context, R.color.gray_light))
                .build())
        name.addPiece(BabushkaPieceBuilder(parts.end).build())
        name.display()
    }

    private fun setupTitleText(airport: Airport, strong: String) {
        location.reset()

        val parts = splitToParts(airport.city ?: "", strong)
        location.addPiece(BabushkaPieceBuilder(parts.start).style(Typeface.BOLD).build())
        location.addPiece(BabushkaPieceBuilder(parts.middle)
                .style(Typeface.BOLD)
                .backgroundColor(ContextCompat.getColor(location.context, R.color.gray_light))
                .build())
        location.addPiece(BabushkaPieceBuilder(parts.end).style(Typeface.BOLD).build())
        location.addPiece(BabushkaPieceBuilder(", ${airport.country}")
                .build())
        location.display()

    }

}

private data class Parts(val start: String = "", val middle: String = "", val end: String = "")

private fun splitToParts(text: String, emphasis: String): Parts {
    val indexOfMatch = text.toUpperCase().indexOf(emphasis.toUpperCase())
    return if (indexOfMatch < 0) {
        Parts(
                start = text
        )
    } else {
        Parts(
                start = text.substring(0, indexOfMatch),
                middle = text.substring(indexOfMatch, indexOfMatch + emphasis.length),
                end = text.substring(indexOfMatch + emphasis.length, text.length)
        )
    }
}