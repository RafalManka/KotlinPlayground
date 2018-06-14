package com.tigerspike.emirates.example


data class User(val name: String) {

}

data class Repo(
        val id: Long,
        val name: String,
        val description: String,
        val user: User,
        val forks_count: Int,
        val stargazers_count: Int
)