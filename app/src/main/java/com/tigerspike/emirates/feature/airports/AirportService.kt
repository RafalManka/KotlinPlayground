package com.tigerspike.emirates.feature.airports

import com.tigerspike.emirates.tools.networking.AirportServiceProvider
import com.tigerspike.emirates.tools.networking.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

fun newAirportService(): AirportService = AirportServiceProvider.retrofit.create(AirportService::class.java)

const val endpoint_debugAirports = "http://10.0.2.2:8080/v1.0.0/airports"

interface AirportService {
    @GET("airports.json")
    fun getAirports(@Query("language") language: String): Call<Array<Airport>>

    @GET
    fun getAirports(@Url url: String, @Query("language") language: String): Call<ApiResponse<Array<Airport>>>

}

