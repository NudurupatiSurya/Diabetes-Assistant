package com.suryanudurupati.sugar.viewmodel

import android.app.Application
import android.content.Intent
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.util.Base64
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.arthenica.mobileffmpeg.FFmpeg
import com.suryanudurupati.sugar.repository.ChatRepo
import com.suryanudurupati.sugar.repository.TranscribeAudioRepo
import kotlinx.coroutines.launch
import java.io.FileOutputStream
import java.io.IOException
import java.util.Locale


class MainViewModel(application: Application) : AndroidViewModel(application), TextToSpeech.OnInitListener {
    private var speechRecognizer: SpeechRecognizer? = null
    private var tts: TextToSpeech? = null

    private val chatRepo = ChatRepo()

    private val _isRecording = MutableLiveData<Boolean>()
    val isRecording: LiveData<Boolean> get() = _isRecording

    private val _transcription = MutableLiveData<String?>()
    val transcription: MutableLiveData<String?> get() = _transcription

    private val _chatGPTResponse = MutableLiveData<String?>()
    val chatGPTResponse: MutableLiveData<String?> get() = _chatGPTResponse

    init {
        _isRecording.value = false
        tts = TextToSpeech(application, this)
        initSpeechRecognizer()
    }

    private fun initSpeechRecognizer() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getApplication<Application>())
        speechRecognizer?.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                Log.d("MainViewModel", "onReadyForSpeech")
            }

            override fun onBeginningOfSpeech() {
                Log.d("MainViewModel", "onBeginningOfSpeech")
                _isRecording.value = true
            }

            override fun onRmsChanged(rmsdB: Float) {}

            override fun onBufferReceived(buffer: ByteArray?) {}

            override fun onEndOfSpeech() {
                Log.d("MainViewModel", "onEndOfSpeech")
                _isRecording.value = false
            }

            override fun onError(error: Int) {
                Log.e("MainViewModel", "Error: $error")
                _isRecording.value = false
            }

            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                matches?.let {
                    if (it.isNotEmpty()) {
                        _transcription.value = it[0]
                        getChatGPTResponse(it[0])
                    }
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {}

            override fun onEvent(eventType: Int, params: Bundle?) {}
        })
    }

    fun startListening() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH)  // Set to English
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now...")
        }
        speechRecognizer?.startListening(intent)
    }

    private fun getChatGPTResponse(transcription: String) {
        viewModelScope.launch {
            val response = chatRepo.getChatGPTResponse(transcription)
            if (response == null) {
                Log.e("MainViewModel", "ChatGPT API call failed")
            } else {
                _chatGPTResponse.value = response
                speakText(response)
            }
        }
    }

    private fun speakText(text: String) {
        tts?.language = Locale.ENGLISH  // Set to English
        val result = tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        if (result == TextToSpeech.ERROR) {
            Log.e("MainViewModel", "Error in converting Text to Speech")
        }
    }

    override fun onCleared() {
        super.onCleared()
        speechRecognizer?.destroy()
        tts?.shutdown()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.ENGLISH)  // Set to English
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("MainViewModel", "English language is not supported")
            }
        } else {
            Log.e("MainViewModel", "TTS Initialization failed")
        }
    }
}
