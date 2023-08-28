package com.example.lotto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.lotto.models.EventModel
import com.example.lotto.models.StartModel
import com.example.lotto.ui.theme.LottoTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<StartModel>()
    private val eventModel by viewModels<EventModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LottoTheme {
                viewModel.fetchAllData()
                val navController  = rememberNavController()
                Navigation(navController = navController, viewModel= viewModel, eventModel =  eventModel)
            }
        }
    }
}





