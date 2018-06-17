/*
 * Copyright (C) 2014 Henrique Boregio.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author Henrique Boregio (hboregio@gmail.com)
 */
package com.tigerspike.emirates.tools.font

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.*
import android.util.AttributeSet
import java.util.*

// some default params
var DEFAULT_ABSOLUTE_TEXT_SIZE: Int = 0
const val DEFAULT_RELATIVE_TEXT_SIZE = 1f

/**
 * BabushkaText is a TextView which lets you customize the styling of parts of your text via
 * Spannables, but without the hassle of having to deal directly with Spannable themselves.
 *
 *
 * The idea behind a BabushkaText is that it is made up of `Piece`s. Each Piece represents a
 * section of the final text displayed by this TextView, and each Piece may be styled independently
 * from the other Pieces. When you put it all together, the final results is still a a single
 * TextView, but with a a very different graphic output.
 */
class BabushkaText : android.support.v7.widget.AppCompatTextView {

    private var mPieces: MutableList<Piece>? = null

    /**
     * Create a new instance of a this class
     *
     * @param context
     */
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        mPieces = ArrayList()
        DEFAULT_ABSOLUTE_TEXT_SIZE = textSize.toInt()
    }

    fun addPiece(aPiece: Piece) {
        mPieces?.add(aPiece)
    }

    /**
     * Adds a Piece at this specific location. The underlying data structure is a
     * [java.util.List], so expect the same type of behaviour.
     *
     * @param aPiece   the Piece to add.
     * @param location the index at which to add.
     */
    fun addPiece(aPiece: Piece, location: Int) {
        mPieces?.add(location, aPiece)
    }

    /**
     * Replaces the Piece at the specified location with this new Piece. The underlying data
     * structure is a [java.util.List], so expect the same type of behaviour.
     *
     * @param newPiece the Piece to insert.
     * @param location the index at which to insert.
     */
    fun replacePieceAt(location: Int, newPiece: Piece) {
        mPieces?.set(location, newPiece)
    }

    /**
     * Removes the Piece at this specified location. The underlying data structure is a
     * [java.util.List], so expect the same type of behaviour.
     *
     * @param location the index of the Piece to remove
     */
    fun removePiece(location: Int) {
        mPieces?.removeAt(location)
    }

    /**
     * Get a specific [Piece] in position index.
     *
     * @param location position of Piece (0 based)
     * @return Piece o null if invalid index
     */
    fun getPiece(location: Int): Piece? {
        return if (location >= 0 && location < mPieces?.size ?: 0) {
            mPieces?.get(location)
        } else null

    }

    /**
     * Call this method when you're done adding [Piece]s
     * and want this TextView to display the final, styled version of it's String contents.
     *
     *
     * You MUST also call this method whenever you make a modification to the text of a Piece that
     * has already been displayed.
     */
    fun display() {

        // generate the final string based on the pieces
        val builder = StringBuilder()
        for (aPiece in mPieces ?: ArrayList()) {
            builder.append(aPiece.text)
        }

        // apply spans
        var cursor = 0
        val finalString = SpannableString(builder.toString())
        for (aPiece in mPieces ?: ArrayList()) {
            applySpannablesTo(aPiece, finalString, cursor, cursor + (aPiece.text?.length ?: 0))
            cursor += aPiece.text?.length ?: 0
        }

        // set the styled text
        text = finalString
    }

    private fun applySpannablesTo(aPiece: Piece, finalString: SpannableString, start: Int, end: Int) {

        if (aPiece.subscript) {
            finalString.setSpan(SubscriptSpan(), start, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        if (aPiece.superscript) {
            finalString.setSpan(SuperscriptSpan(), start, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        if (aPiece.strike) {
            finalString.setSpan(StrikethroughSpan(), start, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        if (aPiece.underline) {
            finalString.setSpan(UnderlineSpan(), start, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        // style
        finalString.setSpan(StyleSpan(aPiece.style), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // absolute text size
        finalString.setSpan(AbsoluteSizeSpan(aPiece.textSize), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // relative text size
        finalString.setSpan(RelativeSizeSpan(aPiece.textSizeRelative), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // text color
        finalString.setSpan(ForegroundColorSpan(aPiece.textColor), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // background color
        if (aPiece.backgroundColor != -1) {
            finalString.setSpan(BackgroundColorSpan(aPiece.backgroundColor), start, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    /**
     * Resets the styling of this view and sets it's content to an empty String.
     */
    fun reset() {
        mPieces = ArrayList()
        text = ""
    }

    /**
     * Change text color of all pieces of textview.
     */
    fun changeTextColor(textColor: Int) {
        for (mPiece in mPieces ?: ArrayList()) {
            mPiece.setTextColor(textColor)
        }
        display()
    }

}


/**
 * A Piece represents a part of the text that you want to style. Say for example you want this
 * BabushkaText to display "Hello World" such that "Hello" is displayed in Bold and "World" is
 * displayed in Italics. Since these have different styles, they are both separate Pieces.
 *
 *
 * You create a Piece by using it's [Piece.Builder]
 */
class Piece(pieceBuilder: BabushkaPieceBuilder) {

    internal var text: String? = null
    internal var textColor: Int = 0
    internal val textSize: Int
    internal val backgroundColor: Int
    internal val textSizeRelative: Float
    internal val style: Int
    internal val underline: Boolean
    internal val superscript: Boolean
    internal val strike: Boolean
    internal val subscript: Boolean

    init {
        this.text = pieceBuilder.text
        this.textSize = pieceBuilder.textSize
        this.textColor = pieceBuilder.textColor
        this.backgroundColor = pieceBuilder.backgroundColor
        this.textSizeRelative = pieceBuilder.textSizeRelative
        this.style = pieceBuilder.style
        this.underline = pieceBuilder.underline
        this.superscript = pieceBuilder.superscript
        this.subscript = pieceBuilder.subscript
        this.strike = pieceBuilder.strike
    }

    /**
     * Sets the text of this Piece. If you're creating a new Piece, you should do so using it's
     * [Piece.Builder].
     *
     *
     * Use this method if you want to modify the text of an existing Piece that is already
     * displayed. After doing so, you MUST call `display()` for the changes to show up.
     *
     * @param text the text to display
     */
    fun setText(text: String) {
        this.text = text
    }


    /**
     * Sets the text color of this Piece. If you're creating a new Piece, you should do so using it's
     * [Piece.Builder].
     *
     *
     * Use this method if you want to change the text color of an existing Piece that is already
     * displayed. After doing so, you MUST call `display()` for the changes to show up.
     *
     * @param textColor of text (it is NOT android Color resources ID, use getResources().getColor(R.color.colorId) for it)
     */
    fun setTextColor(textColor: Int) {
        this.textColor = textColor
    }


}

/**
 * Builder of Pieces
 */
class BabushkaPieceBuilder
/**
 * Creates a new Builder for this Piece.
 *
 * @param text the text of this Piece
 */
(internal val text: String) {
    internal var textSize = DEFAULT_ABSOLUTE_TEXT_SIZE
    internal var textColor = Color.BLACK
    internal var backgroundColor = -1
    internal var textSizeRelative = DEFAULT_RELATIVE_TEXT_SIZE
    internal var style = Typeface.NORMAL
    internal var underline = false
    internal var strike = false
    internal var superscript = false
    internal var subscript = false

    /**
     * Sets the absolute text size.
     *
     * @param textSize text size in pixels
     * @return a Builder
     */
    fun textSize(textSize: Int): BabushkaPieceBuilder {
        this.textSize = textSize
        return this
    }

    /**
     * Sets the text color.
     *
     * @param textColor the color
     * @return a Builder
     */
    fun textColor(textColor: Int): BabushkaPieceBuilder {
        this.textColor = textColor
        return this
    }

    /**
     * Sets the background color.
     *
     * @param backgroundColor the color
     * @return a Builder
     */
    fun backgroundColor(backgroundColor: Int): BabushkaPieceBuilder {
        this.backgroundColor = backgroundColor
        return this
    }

    /**
     * Sets the relative text size.
     *
     * @param textSizeRelative relative text size
     * @return a Builder
     */
    fun textSizeRelative(textSizeRelative: Float): BabushkaPieceBuilder {
        this.textSizeRelative = textSizeRelative
        return this
    }

    /**
     * Sets a style to this Piece.
     *
     * @param style see [android.graphics.Typeface]
     * @return a Builder
     */
    fun style(style: Int): BabushkaPieceBuilder {
        this.style = style
        return this
    }

    /**
     * Underlines this Piece.
     *
     * @return a Builder
     */
    fun underline(): BabushkaPieceBuilder {
        this.underline = true
        return this
    }

    /**
     * Strikes this Piece.
     *
     * @return a Builder
     */
    fun strike(): BabushkaPieceBuilder {
        this.strike = true
        return this
    }

    /**
     * Sets this Piece as a superscript.
     *
     * @return a Builder
     */
    fun superscript(): BabushkaPieceBuilder {
        this.superscript = true
        return this
    }

    /**
     * Sets this Piece as a subscript.
     *
     * @return a Builder
     */
    fun subscript(): BabushkaPieceBuilder {
        this.subscript = true
        return this
    }

    /**
     * Creates a [Piece] with the customized
     * parameters.
     *
     * @return a Piece
     */
    fun build(): Piece {
        return Piece(this)
    }
}