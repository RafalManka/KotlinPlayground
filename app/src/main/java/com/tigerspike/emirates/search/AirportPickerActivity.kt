package com.tigerspike.emirates.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ContextMenu
import android.view.Menu
import android.view.View
import com.tigerspike.emirates.R
import com.tigerspike.emirates.tools.extensions.inLayoutToolbar

fun Context.newAirportPickerActivityIntent(): Intent {
    return Intent(this, AirportPickerActivity::class.java)
}

class AirportPickerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_airport_picker)
        setSupportActionBar(inLayoutToolbar())
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
    }

}
