package com.suryanudurupati.sugar.ui.components

import android.Manifest
import android.util.Log
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Mic(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.mic_animation))
    var isPlaying by remember { mutableStateOf(false) }
    val microphonePermissionState =
        rememberPermissionState(permission = Manifest.permission.RECORD_AUDIO)
    val progress by animateLottieCompositionAsState(
        composition,
        isPlaying = isPlaying,
        iterations = LottieConstants.IterateForever,
    )

    LaunchedEffect(key1 = progress) {
        if (progress == 1f) {
            isPlaying = true
        }
    }

    Log.i("isPlaying:", "" + isPlaying)

    Surface(onClick = {
        if (microphonePermissionState.status.isGranted) {
            // TODO: Record Audio
            isPlaying = !isPlaying
        } else {
            microphonePermissionState.launchPermissionRequest()
        }
    }, modifier = modifier) {
        LottieAnimation(composition = composition,
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