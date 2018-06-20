package com.tigerspike.emirates.feature.airports

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

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