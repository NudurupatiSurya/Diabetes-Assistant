package com.suryanudurupati.sugar.ui.components

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.suryanudurupati.sugar.R

@Composable
fun Speaker(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.speaker_animation))
    var isPlaying by remember { mutableStateOf(false) }
    val progress by animateLottieCompositionAsState(
        composition,
        isPlaying = isPlaying
    )

    LaunchedEffect(key1 = progress) {
        if(progress == 0F) {
            isPlaying = true
        }
        if(progress == 1F) {
            isPlaying = false
        }
    }

    Surface(onClick = { isPlaying = !isPlaying /* TODO:: Speak Text out */ }, modifier = modifier) {
        LottieAnimation(composition = composition,
            progress = { progress }
        )
    }
}