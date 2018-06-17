package com.tigerspike.emirates.feature.airports

import com.tigerspike.emirates.networking.AirportServiceProvider
import retrofit2.Call
import retrofit2.http.GET

fun newAirportService(): AirportService = AirportServiceProvider.retrofit.create(AirportService::class.java)

interface AirportService {
    @GET("airports.json")
    fun getAirports(): Call<Array<Airport>>
}