package com.tigerspike.emirates.networking

import com.tigerspike.emirates.example.RepoService
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {
    private val instance = retrofit2.Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val repoService: RepoService
        get() = instance.create(RepoService::class.java)

}