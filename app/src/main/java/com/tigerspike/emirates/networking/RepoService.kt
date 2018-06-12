package com.tigerspike.emirates.networking

import com.tigerspike.emirates.Repo
import retrofit2.Call
import retrofit2.http.GET

interface RepoService {
    @GET("orgs/Google/repos")
    fun repositories(): Call<List<Repo>>
}
