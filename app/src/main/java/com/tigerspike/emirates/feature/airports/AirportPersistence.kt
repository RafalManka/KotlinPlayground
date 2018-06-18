package com.tigerspike.emirates.feature.airports

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.content.Context

private const val dbName = "movies_db"

@Database(entities = [(Airport::class)], version = 1, exportSchema = false)
abstract class AirportDb : RoomDatabase() {
    abstract fun dao(): AirportDao
}

@Dao
interface AirportDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(airports: Array<Airport>)

    @Query("SELECT * FROM Airport")
    fun fetchAll(): LiveData<Array<Airport>>

}

fun provideAirportDb(context: Context): AirportDb =
        Room.databaseBuilder(context, AirportDb::class.java, dbName)
                .fallbackToDestructiveMigration()
                .build()