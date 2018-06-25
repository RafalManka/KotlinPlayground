package com.tigerspike.emirates.tools.networking

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(@SerializedName("data") val payload: T)