package com.suryanudurupati.sugar.ui.mainscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.suryanudurupati.sugar.ui.theme.SugarTheme

@Composable
fun MainScreen() {
    Column {
        SpeakBox()
        AnswerBox()
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    SugarTheme {
        MainScreen()
    }
}