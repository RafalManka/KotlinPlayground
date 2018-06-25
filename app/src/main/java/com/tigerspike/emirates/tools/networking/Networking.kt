package com.tigerspike.emirates.tools.networking

import com.tigerspike.emirates.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AirportServiceProvider {

    val retrofit: Retrofit = Retrofit.Builder()
            .client(getHttpClient())
            .baseUrl("https://raw.githubusercontent.com/jbrooksuk/JSON-Airports/master/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private fun getHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient().newBuilder()
        val interceptor = Interceptor { chain ->
            chain.request()
                    ?.newBuilder()
                    ?.addHeader("apptype", BuildConfig.APP_TYPE)
                    ?.addHeader("Authorization", BuildConfig.AUTHORISATION)
                    ?.addHeader("X-Api-Version", BuildConfig.VERSION_NAME)
                    ?.addHeader("DTKN", BuildConfig.DEVICE_TOKEN)
                    ?.build()?.let {
                        chain.proceed(it)
                    }
        }
        httpClient.networkInterceptors().add(interceptor)
        return httpClient.build()
    }


}