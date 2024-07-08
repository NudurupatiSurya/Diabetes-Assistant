package com.suryanudurupati.sugar.ui.mainscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.suryanudurupati.sugar.ui.components.LoadingIndicator
import com.suryanudurupati.sugar.viewmodel.MainViewModel

@Composable
fun AnswerBox(modifier: Modifier = Modifier, mainViewModel: MainViewModel = viewModel()) {
    val answer by mainViewModel.chatGPTResponse.observeAsState("")
    val isLoading by mainViewModel.isLoading.observeAsState(false)

    OutlinedCard(
        Modifier
            .fillMaxSize()
            .padding(10.dp),
    ) {
        if (isLoading) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoadingIndicator()
            }
        } else {
            Text(answer ?: "", Modifier.padding(10.dp))
        }
    }
}
