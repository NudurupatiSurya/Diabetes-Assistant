package com.suryanudurupati.sugar.ui.components

import android.Manifest
import android.util.Log
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.suryanudurupati.sugar.R
import com.suryanudurupati.sugar.ui.theme.SugarTheme
import com.suryanudurupati.sugar.viewmodel.MainViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Mic(modifier: Modifier = Modifier, mainViewModel: MainViewModel = viewModel()) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.mic_animation))
    var isPlaying by remember { mutableStateOf(false) }

    val isRecording by mainViewModel.isRecording.observeAsState(false)

    val microphonePermissionState =
        rememberPermissionState(permission = Manifest.permission.RECORD_AUDIO)
    val storagePermissionState =
        rememberPermissionState(permission = Manifest.permission.WRITE_EXTERNAL_STORAGE)

    val progress by animateLottieCompositionAsState(
        composition,
        isPlaying = isPlaying,
        iterations = LottieConstants.IterateForever,
    )

    LaunchedEffect(isRecording) {
        isPlaying = isRecording
    }

    Log.i("storagePermission:", "" + storagePermissionState.status.isGranted)

    Surface(onClick = {
        if (microphonePermissionState.status.isGranted) {
            if (!isRecording) {
                mainViewModel.startListening()
            }
        } else {
            microphonePermissionState.launchPermissionRequest()
            storagePermissionState.launchPermissionRequest()
        }
    }, modifier = modifier) {
        LottieAnimation(
            composition = composition,
            progress = { if (isPlaying) progress else 0f }
        )
    }
}

@Preview
@Composable
fun MicAnimationPreview() {
    SugarTheme {
        Mic(Modifier.size(25.dp))
    }
}