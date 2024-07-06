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
            Message(role = "system", content = "You are a diabetic health advisor. You should tell " +
                    "user whether they can eat a particular food or not concisely.Text will be sent " +
                    "to you in English, but try to find out the native language of it and answer in " +
                    "that language. For example if user sends you “Aarti pandu” then you should find " +
                    "out that this is Telugu language and answer in Telugu."),
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