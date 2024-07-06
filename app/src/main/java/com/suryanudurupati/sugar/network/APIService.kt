package com.suryanudurupati.sugar.network

import com.suryanudurupati.sugar.model.ChatGPTRequest
import com.suryanudurupati.sugar.model.ChatGPTResponse
import com.suryanudurupati.sugar.model.TranscriptionResponseModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface APIService {
    @Multipart
    @POST("v1/audio/transcriptions")
    suspend fun transcribeAudio(
        @Header("Authorization") authorization: String,
        @Part audio: MultipartBody.Part,
        @Part("model") model: RequestBody,
        @Part("prompt") prompt: RequestBody
    ): TranscriptionResponseModel

    @POST("v1/chat/completions")
    suspend fun getChatResponse(
        @Header("Authorization") authorization: String,
        @Body request: ChatGPTRequest
    ): ChatGPTResponse
}