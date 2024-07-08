package com.suryanudurupati.sugar.network

import com.suryanudurupati.sugar.model.ChatGPTRequest
import com.suryanudurupati.sugar.model.ChatGPTResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface APIService {
    @POST("v1/chat/completions")
    suspend fun getChatResponse(
        @Header("Authorization") authorization: String,
        @Body request: ChatGPTRequest
    ): ChatGPTResponse
}