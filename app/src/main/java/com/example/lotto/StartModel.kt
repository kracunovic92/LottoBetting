package com.example.lotto

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lotto.api_calls.RetrofitClient
import com.example.lotto.data.Offers
import kotlinx.coroutines.launch

class StartModel : ViewModel() {

    var data by mutableStateOf(Offers())

    fun fetchAllData(){

        viewModelScope.launch {

            Log.e("Fetch", "ovde")
            val response = RetrofitClient.getData()

            Log.e("Respones", data.toString())
            data = response
        }
    }
}