package com.suryanudurupati.sugar.repository

import android.util.Log
import com.suryanudurupati.sugar.model.ChatGPTRequest
import com.suryanudurupati.sugar.model.Message
import com.suryanudurupati.sugar.network.RetrofitObject
import retrofit2.HttpException

class ChatRepo {

    private val service = RetrofitObject.service

    suspend fun getChatGPTResponse(transcription: String): String? {
        val messages = listOf(
            Message(role = "system", content = "You are a diabetic health advisor. You should tell " +
                    "user whether they can eat a particular food or not concisely.Text will be sent"),
            Message(role = "user", content = "Can I eat $transcription")
        )

        val request = ChatGPTRequest(
            model = "gpt-3.5-turbo",
            messages = messages,
        )

        return try {
            val response = service.getChatResponse("Bearer $APIKEY", request)
            response.choices.firstOrNull()?.message?.content
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Log.e("ChatGPTRepository", "HTTP error: ${e.code()}, $errorBody")
            null
        } catch (e: Exception) {
            Log.e("ChatGPTRepository", "API call failed", e)
            null
        }
    }

    companion object {
        private const val APIKEY = ""
    }
}