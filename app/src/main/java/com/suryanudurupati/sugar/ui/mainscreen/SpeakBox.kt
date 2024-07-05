package com.suryanudurupati.sugar.ui.mainscreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.suryanudurupati.sugar.ui.components.Mic
import com.suryanudurupati.sugar.viewmodel.MainViewModel

@Composable
fun SpeakBox(modifier: Modifier = Modifier, mainViewModel: MainViewModel = viewModel()) {
    val transcribedText by mainViewModel.transcription.observeAsState("")
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
            Text("$transcribedText", Modifier.padding(10.dp))
        }
    }
}