package com.suryanudurupati.sugar.MainScreen.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.suryanudurupati.sugar.ui.components.Mic

@Composable
fun SpeakUI(modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Mic(
            Modifier
                .size(200.dp)
                .padding(10.dp))
        OutlinedCard(
            Modifier
                .fillMaxWidth()
                .padding(10.dp) // TODO:: Add animateContentSize later
        ) {
            Text("అరటి పండు", Modifier.padding(10.dp))
        }
    }
}