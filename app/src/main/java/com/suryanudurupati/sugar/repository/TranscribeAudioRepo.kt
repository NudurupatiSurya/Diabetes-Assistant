package com.suryanudurupati.sugar.repository

import android.util.Log
import com.suryanudurupati.sugar.network.APIService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class TranscribeAudioRepo {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openai.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(APIService::class.java)

    suspend fun transcribeAudio(filePath: String, language: String): String? {
        val file = File(filePath)
        val requestFile = file.asRequestBody("audio/wav".toMediaTypeOrNull())
        val audioPart = MultipartBody.Part.createFormData("file", file.name, requestFile)
        val model = "whisper-1".toRequestBody("text/plain".toMediaTypeOrNull())
        val prompt = "తెలుగు".toRequestBody("text/plain".toMediaTypeOrNull())

        return try {
            val response = service.transcribeAudio("Bearer $APIKEY", audioPart, model, prompt)
            response.text
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Log.e("AudioRepository", "HTTP error: ${e.code()}, $errorBody")
            null
        } catch (e: Exception) {
            Log.e("AudioRepository", "Transcription failed", e)
            null
        }
    }

    companion object {
        private const val APIKEY = ""
    }
}