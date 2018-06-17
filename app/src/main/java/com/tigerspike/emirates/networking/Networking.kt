package com.tigerspike.emirates.networking

import com.tigerspike.emirates.example.RepoService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object AirportServiceProvider {
    val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/jbrooksuk/JSON-Airports/master/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}

object GithubServiceProvider {
    private val instance = retrofit2.Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val repoService: RepoService
        get() = instance.create(RepoService::class.java)

}