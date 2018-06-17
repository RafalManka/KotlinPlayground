package com.tigerspike.emirates.feature.airports

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
data class Airport(
        @SerializedName("iata") val code: String?,
        @SerializedName("name") val name: String?,
        @SerializedName("iso") val country: String?,
        @SerializedName("type") val type: String?,
        @SerializedName("size") val size: String?,
        @SerializedName("status") val status: Int?
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(code)
        parcel.writeString(name)
        parcel.writeString(country)
        parcel.writeString(type)
        parcel.writeString(size)
        parcel.writeValue(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Airport> {
        override fun createFromParcel(parcel: Parcel): Airport {
            return Airport(parcel)
        }

        override fun newArray(size: Int): Array<Airport?> {
            return arrayOfNulls(size)
        }
    }

}