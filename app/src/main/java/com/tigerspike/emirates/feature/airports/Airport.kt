package com.tigerspike.emirates.feature.airports

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


@Database(entities = [(Airport::class)], version = 1, exportSchema = false)
abstract class AirportDb : RoomDatabase() {
    abstract fun dao(): AirportDao
}

@Dao
interface AirportDao {

    @Insert(onConflict = REPLACE)
    fun insert(airport: Airport)

    @Insert(onConflict = REPLACE)
    fun insert(airports: Array<Airport>)

    @Query("SELECT * FROM Airport WHERE iata = :code")
    fun fetchOneByCode(code: String): Airport

    @Update
    fun update(airport: Airport)

    @Delete
    fun delete(airport: Airport)

    @Query("SELECT * FROM Airport")
    fun fetchAll(): LiveData<Array<Airport>>
}

//{
//    "iata": "UTK",
//    "lon": "169.86667",
//    "iso": "MH",
//    "status": 1,
//    "name": "Utirik Airport",
//    "continent": "OC",
//    "type": "airport",
//    "lat": "11.233333",
//    "size": "small"
//}
@Entity
data class Airport(
        @PrimaryKey(autoGenerate = true) var id: Long?,
        @ColumnInfo(name = "iata") @SerializedName("iata") val code: String?,
        @ColumnInfo(name = "name") @SerializedName("name") val name: String?,
        @ColumnInfo(name = "iso") @SerializedName("iso") val country: String?,
        @ColumnInfo(name = "type") @SerializedName("type") val type: String?,
        @ColumnInfo(name = "size") @SerializedName("size") val size: String?,
        @ColumnInfo(name = "status") @SerializedName("status") val status: Int?
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readValue(Long::class.java.classLoader) as Long?,
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeString(code)
        writeString(name)
        writeString(country)
        writeString(type)
        writeString(size)
        writeValue(status)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Airport> = object : Parcelable.Creator<Airport> {
            override fun createFromParcel(source: Parcel): Airport = Airport(source)
            override fun newArray(size: Int): Array<Airport?> = arrayOfNulls(size)
        }
    }
}