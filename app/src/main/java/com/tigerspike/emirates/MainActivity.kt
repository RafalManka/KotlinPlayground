package com.tigerspike.emirates

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View

class MainActivity : AppCompatActivity() {

    val recyclerView: RecyclerView by bind(R.id.recyclerView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}


private fun <V : View> AppCompatActivity.bind(@IdRes id: Int): Lazy<V> {
    return lazy { findViewById<V>(id) }
}


