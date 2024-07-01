package com.suryanudurupati.sugar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.suryanudurupati.sugar.MainScreen.MainScreen
import com.suryanudurupati.sugar.ui.theme.SugarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SugarTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

/*TODO:: Take Mic Permission, Record Audio, Translate to English,
TODO:: send it to chatGPT, retrieve response, convert to telugu, speak it out loud*/

@Preview
@Composable
fun GreetingPreview() {
    SugarTheme {
        MainScreen()
    }
}

