package com.example.lotto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.lotto.start_screen.StartScreen
import com.example.lotto.ui.theme.LottoTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<StartModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LottoTheme {
                viewModel.fetchAllData()
                val navController  = rememberNavController()
                StartScreen(viewModel, navController = navController)
            }
        }
    }
}





