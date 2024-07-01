package com.suryanudurupati.sugar.MainScreen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.suryanudurupati.sugar.ui.components.Speaker

@Composable
fun AnswerUI(modifier: Modifier = Modifier) {
    val mockText = "Yes, people with diabetes can eat Usirikaya, also known as Indian gooseberry or amla. It is considered beneficial for managing blood sugar levels due to its high vitamin C content and other antioxidants. Amla has been shown to improve insulin sensitivity and help regulate blood sugar. However, as with any food, it should be consumed in moderation and as part of a balanced diet. Always consult with a healthcare provider before making significant changes to your diet."
//    Row (horizontalArrangement = Arrangement.Absolute.Right) {
//        Speaker(Modifier.size(100.dp).padding(10.dp))
//    }
    OutlinedCard(
        Modifier
            .fillMaxSize()
            .padding(10.dp)) {
        // put for loop in the answer size
        Text(mockText, Modifier.padding(10.dp))
    }
}