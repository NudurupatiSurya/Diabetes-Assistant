package com.suryanudurupati.sugar.ui.mainscreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.suryanudurupati.sugar.viewmodel.MainViewModel

@Composable
fun AnswerBox(modifier: Modifier = Modifier, mainViewModel: MainViewModel = viewModel()) {
    //val mockText = "Yes, people with diabetes can eat Usirikaya, also known as Indian gooseberry or amla. It is considered beneficial for managing blood sugar levels due to its high vitamin C content and other antioxidants. Amla has been shown to improve insulin sensitivity and help regulate blood sugar. However, as with any food, it should be consumed in moderation and as part of a balanced diet. Always consult with a healthcare provider before making significant changes to your diet."
//    Row (horizontalArrangement = Arrangement.Absolute.Right) {
//        Speaker(Modifier.size(100.dp).padding(10.dp))
//    }
    val answer by mainViewModel.chatGPTResponse.observeAsState("")

    OutlinedCard(
        Modifier
            .fillMaxSize()
            .padding(10.dp)) {
        // put for loop in the answer size
        Text(answer!!, Modifier.padding(10.dp))
    }
}