package com.tigerspike.emirates.example

import com.tigerspike.emirates.example.Repo
import retrofit2.Call
import retrofit2.http.GET

interface RepoService {
    @GET("orgs/Google/repos")
    fun repositories(): Call<List<Repo>>
}
