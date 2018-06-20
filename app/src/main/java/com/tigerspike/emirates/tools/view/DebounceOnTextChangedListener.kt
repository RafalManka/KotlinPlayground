package com.tigerspike.emirates.tools.view

import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import java.util.*

abstract class DebounceOnTextChangedListener(private val delay: Long = 1000) : TextWatcher {

    private var timer: Timer? = null

    final override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        timer?.cancel()
        timer = Timer()
        timer?.schedule(
                object : TimerTask() {
                    override fun run() {
                        Handler(Looper.getMainLooper()).post {
                            doOnTextChanged(s, start, before, count)
                        }
                    }
                },
                delay
        )
    }

    abstract fun doOnTextChanged(s: CharSequence, start: Int, before: Int, count: Int)

    final override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    final override fun afterTextChanged(p0: Editable?) {}

}