package com.tigerspike.emirates.feature.airports

import android.arch.persistence.room.Room
import android.content.Context

private const val dbName = "movies_db"

class AirportPersistence(context: Context) {
    val movieDatabase: AirportDb by lazy {
        Room.databaseBuilder(context.applicationContext, AirportDb::class.java, dbName)
                .fallbackToDestructiveMigration()
                .build()
    }
}