package com.tigerspike.emirates.example.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.tigerspike.emirates.example.Repo
import com.tigerspike.emirates.example.RepoService
import com.tigerspike.emirates.networking.GithubServiceProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListViewModel(
        val repos: MutableLiveData<List<Repo>> = MutableLiveData<List<Repo>>(),
        val isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>(),
        val isError: MutableLiveData<Boolean> = MutableLiveData<Boolean>(),
        private val repoService: RepoService = GithubServiceProvider.repoService
) : ViewModel() {

    private var call: Call<List<Repo>>? = null

    init {
        fetchRepos()
    }

    private fun fetchRepos() {
        isLoading.value = true
        call = repoService.repositories()
        call?.enqueue(object : Callback<List<Repo>> {
            override fun onFailure(call: Call<List<Repo>>?, t: Throwable?) {
                Log.d("ListViewModel", "error", t)
                isLoading.value = false
                isError.value = true
            }

            override fun onResponse(call: Call<List<Repo>>?, response: Response<List<Repo>>?) {
                Log.d("ListViewModel", "success ${response?.body()}")
                response?.body()?.let {
                    isError.value = false
                    isLoading.value = false
                    repos.value = it
                }
            }

        })
    }

    override fun onCleared() {
        super.onCleared()
        call?.cancel()
        call = null
    }

}