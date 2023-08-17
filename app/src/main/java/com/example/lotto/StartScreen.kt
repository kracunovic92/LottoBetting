package com.example.lotto

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import com.example.lotto.api_calls.RetrofitClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun StartScreen(
) {

    GlobalScope.launch {
        var data = RetrofitClient.getData()
        println(data)
        Log.e("Ovde puca", data.toString())

    }



    Column {
        Text(text = "Ovo je moja app")
        Text(
            modifier = Modifier.fillMaxSize(),
            text = "za malo"
            //text = viewModel.data.completeOffer.toString()
        )
    }

}