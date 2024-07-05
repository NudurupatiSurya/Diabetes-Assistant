package com.suryanudurupati.sugar.viewmodel

import android.app.Application
import android.media.MediaRecorder
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.arthenica.mobileffmpeg.FFmpeg
import com.suryanudurupati.sugar.repository.ChatRepo
import com.suryanudurupati.sugar.repository.TranscribeAudioRepo
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var mediaRecorder: MediaRecorder? = null
    private var audioFilePath: String? = null

    private val _isRecording = MutableLiveData<Boolean>()
    val isRecording: LiveData<Boolean> get() = _isRecording

    private val audioRepository = TranscribeAudioRepo()
    private val chatRepo = ChatRepo()

    private val _transcription = MutableLiveData<String?>()
    val transcription: MutableLiveData<String?> get() = _transcription

    private val _chatGPTResponse = MutableLiveData<String?>()
    val chatGPTResponse: MutableLiveData<String?> get() = _chatGPTResponse

    init {
        _isRecording.value = false
        audioFilePath = "${application.externalCacheDir?.absolutePath}/audio.3gp"
    }

    fun startRecording() {
        if (_isRecording.value == true) return

        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(audioFilePath)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            try {
                prepare()
                start()
                _isRecording.value = true
            } catch (e: IOException) {
                Log.e("MediaViewModel", "prepare() failed", e)
                _isRecording.value = false
            }
        }
    }

    fun stopRecording() {
        if (_isRecording.value == false) return

        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
        _isRecording.value = false
    }

    fun transcribeAudio() {
        viewModelScope.launch {
            audioFilePath?.let {
                val wavFilePath = "${getApplication<Application>().externalCacheDir?.absolutePath}/audio.wav"
                if (convert3gpToWav(it, wavFilePath)) {
                    val transcriptionText = audioRepository.transcribeAudio(wavFilePath, "te")
                    if (transcriptionText == null) {
                        Log.e("MainViewModel", "Transcription failed")
                    } else {
                        Log.i("Transcription", "Transcription Successful")
                        _transcription.value = transcriptionText
                        getChatGPTResponse(_transcription.value!!)
                    }
                } else {
                    Log.e("MainViewModel", "Audio conversion failed")
                }
            }
        }
    }

    private fun convert3gpToWav(inputPath: String, outputPath: String): Boolean {
        val command = arrayOf("-y", "-i", inputPath, outputPath)
        Log.d("FFmpegCommand", "Command: ${command.joinToString(" ")}")
        return try {
            val rc = FFmpeg.execute(command)
            rc == 0
        } catch (e: Exception) {
            Log.e("AudioConversion", "Conversion failed", e)
            false
        }
    }

    private fun getChatGPTResponse(transcription: String) {
        viewModelScope.launch {
            val response = chatRepo.getChatGPTResponse(transcription)
            if (response == null) {
                Log.e("MainViewModel", "ChatGPT API call failed")
            } else {
                _chatGPTResponse.value = response
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaRecorder?.release()
    }

    fun getAudioFilePath(): String? {
        return audioFilePath
    }
}
