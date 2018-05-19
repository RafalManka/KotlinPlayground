package com.tigerspike.emirates


data class User(val name: String) {

}

data class Repo(
        val id: Long,
        val name: String,
        val description: String,
        val user: User
)