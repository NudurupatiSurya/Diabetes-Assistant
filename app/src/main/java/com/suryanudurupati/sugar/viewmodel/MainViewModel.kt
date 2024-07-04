package com.suryanudurupati.sugar.viewmodel

import android.app.Application
import android.media.MediaRecorder
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.io.IOException

class MainViewModel(application: Application): AndroidViewModel(application) {
    private var mediaRecorder: MediaRecorder? = null
    private var audioFilePath: String? = null

    private val _isRecording = MutableLiveData<Boolean>()
    val isRecording: LiveData<Boolean> get() = _isRecording

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
                Log.e("MediaViewModel", "prepare() failed")
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
    override fun onCleared() {
        super.onCleared()
        mediaRecorder?.release()
    }

    fun getAudioFilePath(): String? {
        return audioFilePath
    }

}