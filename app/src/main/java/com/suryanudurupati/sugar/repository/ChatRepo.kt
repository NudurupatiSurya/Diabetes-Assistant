package com.suryanudurupati.sugar.repository

import android.util.Log
import com.suryanudurupati.sugar.model.ChatGPTRequest
import com.suryanudurupati.sugar.model.Message
import com.suryanudurupati.sugar.network.APIService
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChatRepo {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openai.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(APIService::class.java)

    suspend fun getChatGPTResponse(transcription: String): String? {
        val messages = listOf(
            Message(role = "system", content = "You are a health advisor who gives answer concisely" +
                    " whether your patients can eat the food they specified or not" +
                    ". Also User gives their food item name in telugu language" +
                    " So while Answering please tell the fruit name in english and tell in telugu whether they can eat it or not"),
            Message(role = "user", content = "Can a person with diabetes eat the following food: $transcription")
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