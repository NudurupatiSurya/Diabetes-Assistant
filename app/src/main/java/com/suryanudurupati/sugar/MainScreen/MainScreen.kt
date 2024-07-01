package com.suryanudurupati.sugar.MainScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.suryanudurupati.sugar.MainScreen.ui.AnswerUI
import com.suryanudurupati.sugar.MainScreen.ui.SpeakUI
import com.suryanudurupati.sugar.ui.theme.SugarTheme

@Composable
fun MainScreen() {
    Column {
        SpeakUI()
        AnswerUI()
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    SugarTheme {
        MainScreen()
    }
}